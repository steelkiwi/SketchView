package com.skd.sketchview;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SketchFragment extends Fragment {

	private GestureOverlayView gestureView;
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
		
		sketchView = (SketchView) getView().findViewById(R.id.paintCanvas);
		
		gestureView = (GestureOverlayView) getView().findViewById(R.id.signaturePad);
		gestureView.addOnGestureListener(sketchView);
	}

	public Bitmap getSketchBimap() {
		return sketchView.getBitmap();
	}
	
}
