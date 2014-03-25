package com.codepath.gridimagesearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionStatusInfo {
	private static ConnectionStatusInfo instance = new ConnectionStatusInfo();
	static Context context;
	ConnectivityManager connectivityManager;
	NetworkInfo wifiInfo, mobileInfo;
	boolean connected = false;

	public static ConnectionStatusInfo getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	public boolean isOnline(Context con) {
		try {
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager != null) {
				NetworkInfo[] networkInfo = connectivityManager
						.getAllNetworkInfo();
				if (networkInfo != null)
					for (int i = 0; i < networkInfo.length; i++)
						if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}

			}
			return false;
		} catch (Exception e) {
			System.out
					.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}

		return connected;
	}

	public void displayAlert(Context context) {
		AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
		builder2.setMessage("internet temporarily unavailable");
		builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		});

		builder2.show();
	}
}
