package com.codepath.gridimagesearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class GridExceptionHandler {


	private static GridExceptionHandler instance = new GridExceptionHandler();

	public static GridExceptionHandler getInstance() {
		return instance;
	}

	public void displayAlert(Context context) {
		
		AlertDialog.Builder builderExp = new AlertDialog.Builder(context);
		builderExp.setMessage("Oops...System unavailable, Please try again later");
		builderExp.setPositiveButton("Force Close",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.e("ERROR", "Force close action : Error occurred in application");
					}

				});

		builderExp.setNegativeButton("Report",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Log.e("ERROR", "Report Action : Error occurred in application");
					}

				});

		builderExp.show();
	}

}
