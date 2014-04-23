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
