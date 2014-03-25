package com.codepath.gridimagesearch;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	Button btSearch;
	GridView gvResults;
	List<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

	private static int REQUEST_GRID_SEARCH = 15;

	ImageRequest imageRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("Error", "Unhandled exception: " + ex.getMessage());
				GridExceptionHandler.getInstance().displayAlert(SearchActivity.this);
				System.exit(1);

			}
		});

		setContentView(R.layout.activity_search);

		setupViews();

		if (imageRequest == null) {
			imageRequest = new ImageRequest();
		}

		imageAdapter = new ImageResultArrayAdapter(this, imageResults);

		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (page < 9) {
					int currentIndex = imageRequest.getStartIndex();
					imageRequest.setStartIndex(currentIndex + 8);
					loadResults();
				}

			}
		});

		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				ImageResult imageResult = imageResults.get(position);
				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});

		gvResults.setAdapter(imageAdapter);

	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btSearch = (Button) findViewById(R.id.btSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void onsettingsclicked(MenuItem mi) {
		
		
		Intent i = new Intent(getApplicationContext(),
				SaveSettingsActivity.class);
		i.putExtra("imageRequest", imageRequest);
		startActivityForResult(i, REQUEST_GRID_SEARCH);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == REQUEST_GRID_SEARCH) {
			imageResults.clear();
			imageRequest = (ImageRequest) data
					.getSerializableExtra("imageRequest");

			etQuery.setText(imageRequest.getImageSearchString());
			loadResults();
		}
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		imageRequest.setImageSearchString(query);
		imageRequest.setStartIndex(0);
		imageResults.clear();
		if (query == null || query.length() == 0) {
			imageAdapter.notifyDataSetChanged();
		} else {
			loadResults();

		}
	}

	public void loadResults() {

		 if (ConnectionStatusInfo.getInstance(this).isOnline(this)) {
		
			AsyncHttpClient client = new AsyncHttpClient();
			// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android

			Log.d("DEBUG", imageRequest.generateSearchUrl());
			client.get(imageRequest.generateSearchUrl(),
					new JsonHttpResponseHandler() {

						public void onSuccess(JSONObject response) {

							JSONArray imageJSONResults = null;

							try {
								imageJSONResults = response.getJSONObject(
										"responseData").getJSONArray("results");
								imageResults.addAll(ImageResult
										.fromJSONArray(imageJSONResults));
								imageAdapter.notifyDataSetChanged();

							} catch (JSONException e) {
								e.printStackTrace();
								Log.e("ERROR", e.getMessage());
								GridExceptionHandler.getInstance().displayAlert(SearchActivity.this);
							}
						};
					});
		} else {
			ConnectionStatusInfo.getInstance(this).displayAlert(SearchActivity.this);
		}
	}

}
