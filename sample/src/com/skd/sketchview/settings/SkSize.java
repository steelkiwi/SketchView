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
 * Entity class for brush size.
 */

public class SkSize {

	private String title;
	private int size;
	private int sizeResId;
	
	public SkSize(String title, int size, int sizeResId) {
		this.title = title;
		this.size = size;
		this.sizeResId = sizeResId;
	}

	public String getTitle() {
		return title;
	}

	public int getSize() {
		return size;
	}
	
	public int getSizeResourceId() {
		return sizeResId;
	}
	
	public static int getSize(Resources r, SkSize size) {
		return (int) r.getDimension(size.getSizeResourceId());
	}
	
	public static ArrayList<SkSize> getSizes(Resources r) {
		ArrayList<SkSize> sizes = new ArrayList<>();
		sizes.add(new SkSize(r.getString(R.string.dlg_size_thin), 	r.getInteger(R.integer.size_brush_thin), 	R.dimen.size_brush_thin));
		sizes.add(new SkSize(r.getString(R.string.dlg_size_medium), r.getInteger(R.integer.size_brush_medium),  R.dimen.size_brush_medium));
		sizes.add(new SkSize(r.getString(R.string.dlg_size_thick), 	r.getInteger(R.integer.size_brush_thick), 	R.dimen.size_brush_thick));
		return sizes;
	}
	
	public static SkSize getDefaultSize(Resources r) {
		return new SkSize(r.getString(R.string.dlg_size_medium), r.getInteger(R.integer.size_brush_medium), R.dimen.size_brush_medium);
	}
	
}
