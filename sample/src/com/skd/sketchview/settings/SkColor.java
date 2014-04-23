package com.skd.sketchview.settings;

import java.util.ArrayList;

import android.content.res.Resources;

import com.skd.sketchview.R;

public class SkColor {
	
	private String title;
	private int colorResId;
	
	public SkColor(String title, int colorResId) {
		this.title = title;
		this.colorResId = colorResId;
	}

	public String getTitle() {
		return title;
	}

	public int getColorResourceId() {
		return colorResId;
	}
	
	public static ArrayList<SkColor> getColors(Resources r) {
		ArrayList<SkColor> colors = new ArrayList<SkColor>();
		colors.add(new SkColor(r.getString(R.string.dlg_color_red), 	R.color.color_red));
		colors.add(new SkColor(r.getString(R.string.dlg_color_orange), 	R.color.color_orange));
		colors.add(new SkColor(r.getString(R.string.dlg_color_yellow), 	R.color.color_yellow));
		colors.add(new SkColor(r.getString(R.string.dlg_color_green), 	R.color.color_green));
		colors.add(new SkColor(r.getString(R.string.dlg_color_cyan), 	R.color.color_cyan));
		colors.add(new SkColor(r.getString(R.string.dlg_color_blue), 	R.color.color_blue));
		colors.add(new SkColor(r.getString(R.string.dlg_color_magenta), R.color.color_magenta));
		colors.add(new SkColor(r.getString(R.string.dlg_color_gray), 	R.color.color_gray));
		colors.add(new SkColor(r.getString(R.string.dlg_color_black), 	R.color.color_black));
		colors.add(new SkColor(r.getString(R.string.dlg_color_white), 	R.color.color_white));
		return colors;
	}
	
}
