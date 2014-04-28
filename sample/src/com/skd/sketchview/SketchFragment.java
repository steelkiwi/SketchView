package com.skd.sketchview;

import android.gesture.GestureOverlayView;
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

public class SketchFragment extends Fragment {

	public static final String COLOR = "argColor";
	public static final String SIZE = "argSize";
	
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
		
		sketchView = (SketchView) getView().findViewById(R.id.sketchCanvas);
		sketchView.setBrushes(SketchManager.createBrushes(getResources()));
		
		gestureView = (GestureOverlayView) getView().findViewById(R.id.gestureOverlay);
		gestureView.addOnGestureListener(sketchView);
		gestureView.addOnGesturePerformedListener(sketchView);
		
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
		gestureView.setGestureColor(color);
		gestureView.setGestureStrokeWidth(size);
	}
	
	public void undo() {
		boolean isLast = sketchView.removeLastPath();
		if (isLast) {
			Toast.makeText(getActivity(), getString(R.string.empty_sketch), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setSketchBackground(Bitmap bitmap) {
		sketchView.setAsBackground(bitmap);
	}

	public Bitmap getSketchBimap() {
		return sketchView.getBitmap();
	}
	
}
