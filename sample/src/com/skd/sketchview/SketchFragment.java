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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skd.sketchview.settings.SkColor;
import com.skd.sketchview.settings.SkSize;
import com.skd.sketchview.sketch.SketchManager;

/*
 * Fragment class holding SketchView. 
 */

public class SketchFragment extends Fragment {

	public static final String COLOR = "argColor";
	public static final String SIZE = "argSize";
	
	private SketchView sketchView;
	
	public SketchFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		sketchView = (SketchView) getView().findViewById(R.id.sketchView);
		sketchView.setBrushes(SketchManager.createBrushes(getResources()));
		
		if (getArguments() != null) {
			int color = getArguments().getInt(COLOR, getResources().getColor(R.color.color_black));
			int size = getArguments().getInt(SIZE, getResources().getInteger(R.integer.size_brush_medium));
			setGestureColorAndSize(color, size);
		}
	}

	public void setGestureColorAndSize(SkColor color, SkSize size) {
		setGestureColorAndSize(SkColor.getColor(getResources(), color), size.getSize());
	}
	
	private void setGestureColorAndSize(int color, int size) {
		sketchView.setGestureColorAndSize(color, size);
	}
	
	public void undo() {
		boolean isLast = sketchView.undoLastGesture();
		if (isLast) {
			Toast.makeText(getActivity(), getString(R.string.empty_sketch), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setSketchBackground(Bitmap bitmap) {
		sketchView.setSketchBackground(bitmap);
	}

	public Bitmap getSketchBimap() {
		return sketchView.getSketchBimap();
	}
	
}
