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

package com.skd.sketchview.settings;

import java.util.ArrayList;

import android.content.res.Resources;

import com.skd.sketchview.R;

/*
 * Entity class for brush color.
 */

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
	
	public static int getColor(Resources r, SkColor color) {
		return r.getColor(color.getColorResourceId());
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
	
	public static SkColor getDefaultColor(Resources r) {
		return new SkColor(r.getString(R.string.dlg_color_black), R.color.color_black);
	}
	
	public static SkColor getEraserColor(Resources r) {
		return new SkColor(r.getString(R.string.dlg_color_white), R.color.color_white);
	}
	
}
