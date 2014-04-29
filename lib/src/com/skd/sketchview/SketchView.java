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

import java.util.HashMap;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/*
 * SketchView is a compound view holding custom canvas and GestureOverlayView.
 */

public class SketchView extends FrameLayout {

	private GestureOverlayView gestureView;
	private SketchCanvas sketchCanvas;
	
	public SketchView(Context context) {
		super(context);
		init(context);
	}

	public SketchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SketchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sketch_view, this);
		
		sketchCanvas = (SketchCanvas) findViewById(R.id.sketchCanvas);
		
		gestureView = (GestureOverlayView) findViewById(R.id.gestureOverlay);
		gestureView.addOnGestureListener(sketchCanvas);
		gestureView.addOnGesturePerformedListener(sketchCanvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i=0, cnt=getChildCount(); i<cnt; i++) {
			getChildAt(i).layout(l, t, r, b);
		}
	}
	
	public void setBrushes(HashMap<Pair<Integer, Integer>, Paint> brushes) {
		sketchCanvas.setBrushes(brushes);
	}
	
	public void setSketchBackground(Bitmap bitmap) {
		sketchCanvas.setAsBackground(bitmap);
	}
	
	public void setGestureColorAndSize(int color, int size) {
		gestureView.setGestureColor(color);
		gestureView.setGestureStrokeWidth(size);
	}
	
	public boolean undoLastGesture() {
		return sketchCanvas.removeLastPath();
	}
	
	public Bitmap getSketchBimap() {
		return sketchCanvas.getBitmap();
	}
	
}
