package com.codepath.gridimagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("Error", "Unhandled exception: " + ex.getMessage());
				GridExceptionHandler.getInstance().displayAlert(
						ImageDisplayActivity.this);
				System.exit(1);
			}
		});

		setContentView(R.layout.activity_image_display);

		ImageResult result = (ImageResult) getIntent().getSerializableExtra(
				"result");
		SmartImageView ivResult = (SmartImageView) findViewById(R.id.ivResult);
		Log.d("DEBUG", "DISPLAY URL : " + result.getFullUrl());

		if (ConnectionStatusInfo.getInstance(this).isOnline(this)) {

			ivResult.setImageUrl(result.getFullUrl(),
					R.drawable.ic_not_found_hd, R.drawable.ic_loading);
		} else {
			ConnectionStatusInfo.getInstance(this).displayAlert(
					ImageDisplayActivity.this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);

		return true;
	}

	public void onShareclicked(MenuItem mi) {
		// Get access to bitmap image from view
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		Uri bmpUri = getLocalBitmapUri(ivImage);
		if (bmpUri != null) {
			// Construct a ShareIntent with link to image
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
			shareIntent.setType("image/*");
			// Launch sharing dialog for image
			startActivity(Intent.createChooser(shareIntent, "Share Content"));
		} else {
			Log.e("ERROR", "Image sending failed");
		}
		
	}

	public Uri getLocalBitmapUri(ImageView imageView) {
		Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		// Write image to default external storage directory
		Uri bmpUri = null;
		try {
			File filesDir = getFilesDir();
			File file = new File(filesDir, "share_image.png");

			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bmpUri;
	}
}
