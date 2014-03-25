package com.codepath.gridimagesearch;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;

public class SaveSettingsActivity extends Activity{

	ImageRequest imageRequest;
	EditText etImageSize;
	EditText etImageType;
	EditText etColorFilter;
	EditText etSiteFilter;

	private String NONE = "none";
	private String BLANK = "";
	ImageSearchOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("Error", "Unhandled exception: " + ex.getMessage());
				GridExceptionHandler.getInstance().displayAlert(SaveSettingsActivity.this);
				System.exit(1);
			}
		});
		
		setContentView(R.layout.activity_save_settings);
		
		options = new ImageSearchOptions();
		fetchImageSearchOptions();
		setViewMappings();
		
		imageRequest = (ImageRequest)getIntent().getSerializableExtra("imageRequest");
		imageRequest.setStartIndex(0);
		parseImageRequest();
	}

	public void setViewMappings(){
		etImageSize = (EditText) findViewById(R.id.etImageSize);
		etImageType = (EditText) findViewById(R.id.etImageType);
		etColorFilter = (EditText) findViewById(R.id.etColorFilter);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_settings, menu);
		return true;
	}

	public void fetchImageSearchOptions() {

		options.getImageSizeList().add("none");
		options.getImageSizeList().add("icon");
		options.getImageSizeList().add("small");
		options.getImageSizeList().add("medium");
		options.getImageSizeList().add("large");
		options.getImageSizeList().add("xlarge");
		options.getImageSizeList().add("xxlarge");
		options.getImageSizeList().add("huge");

		
		options.getColorFilterList().add("none");
		options.getColorFilterList().add("yellow");
		options.getColorFilterList().add("green");
		options.getColorFilterList().add("teal");
		options.getColorFilterList().add("blue");
		options.getColorFilterList().add("purple");
		options.getColorFilterList().add("pink");
		options.getColorFilterList().add("white");
		options.getColorFilterList().add("grey");
		options.getColorFilterList().add("black");
		options.getColorFilterList().add("brown");
		
		options.getImageTypeList().add("none");
		options.getImageTypeList().add("clipart");
		options.getImageTypeList().add("face");
		options.getImageTypeList().add("lineart");
		options.getImageTypeList().add("news");
		options.getImageTypeList().add("photo");

		options.getImageTypeIconList().add(R.drawable.ic_img_none);
		options.getImageTypeIconList().add(R.drawable.ic_img_c);
		options.getImageTypeIconList().add(R.drawable.ic_img_f);
		options.getImageTypeIconList().add(R.drawable.ic_img_l);
		options.getImageTypeIconList().add(R.drawable.ic_img_n);
		options.getImageTypeIconList().add(R.drawable.ic_img_p);

	}

	public void openImageSizeDialog(View v) {

		List<String> sizeList = options.getImageSizeList();

		final CharSequence[] cs = sizeList.toArray(new CharSequence[sizeList
				.size()]);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select image size");

		builder.setItems(cs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Do something with the selection
				
				if(NONE.equalsIgnoreCase(cs[item].toString())){
					etImageSize.setText(BLANK);
				} else {
					etImageSize.setText(cs[item]);
				}
				
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}
	
	public void openColorFilterDialog(View v) {

		List<String> colorList = options.getColorFilterList();

		final CharSequence[] cs = colorList.toArray(new CharSequence[colorList
				.size()]);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select color filter");

		builder.setItems(cs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Do something with the selection
				
				if(NONE.equalsIgnoreCase(cs[item].toString())){
					etColorFilter.setText(BLANK);
				} else {
					etColorFilter.setText(cs[item]);
				}
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	public void openImageTyeDialog(View v) {

		List<String> typeList = options.getImageTypeList();
		List<Integer> typeIconList = options.getImageTypeIconList();

		ListAdapter adapter = new SearchImageTypeAdapter(getApplicationContext(), typeList, typeIconList);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(SaveSettingsActivity.this);

			builder.setTitle("title");

			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
			    	
					if(NONE.equalsIgnoreCase(options.getImageTypeList().get(which))){
						etImageType.setText(BLANK);
					} else {
						etImageType.setText(options.getImageTypeList().get(which));
					}

			    }

			});

			builder.create();
			if (! ((Activity) SaveSettingsActivity.this).isFinishing()) {
			    builder.show();
			}

	}

	public void saveSettingChanges(View v){
		
		updateImageRequest();
		sendImageRequest();
	}
	
	public void cancelSettingChanges(View v){
		sendImageRequest();
	}
	
	public void resetSettingChanges(View v){
		etImageSize.setText(BLANK);
		etImageType.setText(BLANK);
		etColorFilter.setText(BLANK);
		etSiteFilter.setText(BLANK);
	}
	
	public void updateImageRequest(){
		imageRequest.setImageSize(etImageSize.getText().toString());
		imageRequest.setImageType(etImageType.getText().toString());
		imageRequest.setColorFilter(etColorFilter.getText().toString());
		imageRequest.setSiteFilter(etSiteFilter.getText().toString());
		
	}
	
	public void parseImageRequest(){
		
		etImageSize.setText(imageRequest.getImageSize());
		etImageType.setText(imageRequest.getImageType());
		etColorFilter.setText(imageRequest.getColorFilter());
		etSiteFilter.setText(imageRequest.getSiteFilter());
	}
	
	public void sendImageRequest() {

		Intent i = new Intent();
		i.putExtra("imageRequest", imageRequest);
		setResult(RESULT_OK, i);
		finish();
	}
	
	
}
