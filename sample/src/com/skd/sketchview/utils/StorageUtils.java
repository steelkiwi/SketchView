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

package com.skd.sketchview.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.skd.sketchview.R;

/*
 * Helper class to save sketch bitmap as PNG file on device's external storage.
 */

public class StorageUtils {

	public static String save(Context ctx, Bitmap bm) {
		if (!checkExternalStorageAvailable()) { return ""; }
		
		try {
			File homeDir = getHomeDir(ctx);
			File sketchFile = new File(homeDir, getFileName(ctx));
			sketchFile.createNewFile();
			FileOutputStream sketchFos = new FileOutputStream(sketchFile);
			bm.compress(Bitmap.CompressFormat.PNG, 100, sketchFos);
			sketchFos.close();
			return sketchFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static boolean checkExternalStorageAvailable() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		}
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		}
		else {
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		return (mExternalStorageAvailable && mExternalStorageWriteable);
	}
	
	private static File getHomeDir(Context ctx) {
		File dir = new File(Environment.getExternalStorageDirectory(), ctx.getString(R.string.app_name));
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir;
	}
	
	private static String getFileName(Context ctx) {
		String dt = getCurrentDateTime();
		return String.format("sketch_%s.png", dt);
	}
	
	private static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.ENGLISH);
		String dt = sdf.format(Calendar.getInstance().getTime());
		return dt;
	}
	
}
