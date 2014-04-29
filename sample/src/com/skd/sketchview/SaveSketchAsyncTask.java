/*
 * Copyright (C) 2014 Steelkiwi Development, Julia Zudikova
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skd.sketchview;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.skd.sketchview.utils.StorageUtils;

/*
 * A background task to save sketch bitmap on device's external storage.
 * Displays ProgressDialog while saving.
 * At the end calls activity's method passing a string path of saved sketch. 
 */

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
		
		activityRef.get().sketchSaved(result);
	}
	
}