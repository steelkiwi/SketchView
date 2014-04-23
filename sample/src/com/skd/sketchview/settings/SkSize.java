package com.skd.sketchview.settings;

import java.util.ArrayList;

import android.content.res.Resources;

import com.skd.sketchview.R;

public class SkSize {

	private String title;
	private int sizeResId;
	
	public SkSize(String title, int size) {
		this.title = title;
		this.sizeResId = size;
	}

	public String getTitle() {
		return title;
	}

	public int getSize() {
		return sizeResId;
	}

	public static ArrayList<SkSize> getSizes(Resources r) {
		ArrayList<SkSize> sizes = new ArrayList<>();
		sizes.add(new SkSize(r.getString(R.string.dlg_size_thin), 	R.dimen.size_brush_thin));
		sizes.add(new SkSize(r.getString(R.string.dlg_size_medium), R.dimen.size_brush_medium));
		sizes.add(new SkSize(r.getString(R.string.dlg_size_thick), 	R.dimen.size_brush_thick));
		return sizes;
	}
	
}
