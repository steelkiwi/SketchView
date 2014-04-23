package com.skd.sketchview;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.skd.sketchview.utils.StorageUtils;

public class SaveSketchAsyncTask extends AsyncTask<Bitmap, Void, String> {
	
	private WeakReference<MainActivity> activityRef;
	private ProgressDialog progress;

	public SaveSketchAsyncTask(MainActivity a) {
		super();
		
		activityRef = new WeakReference<MainActivity>(a);
		
		progress = new ProgressDialog(a);
		progress.setMessage(a.getString(R.string.progress_save));
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (progress != null) {
			progress.show();
		}
	}

	@Override
	protected String doInBackground(Bitmap... params) {
		return StorageUtils.save(activityRef.get(), params[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (progress != null) {
			progress.dismiss();
		}
		if (activityRef.get() == null) { return; }
		
		if (!TextUtils.isEmpty(result)) {
			Toast.makeText(activityRef.get(), String.format(activityRef.get().getString(R.string.success_save), result), Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(activityRef.get(), activityRef.get().getString(R.string.error_save), Toast.LENGTH_SHORT).show();
		}
	}
	
}