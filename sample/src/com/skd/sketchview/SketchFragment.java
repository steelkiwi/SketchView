package com.skd.sketchview;

import java.io.File;
import java.io.FileOutputStream;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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
		
		Button saveBtn = (Button) getView().findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSketch();
			}
		});
	}
	
	private void saveSketch() { //TODO rewrite
		try {
			Bitmap bm = sketchView.getBitmap();
			File f = new File(Environment.getExternalStorageDirectory() + File.separator + "sketch.png");
			f.createNewFile();
			FileOutputStream os = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.close();
		} catch (Exception e) {
			Log.v("Gestures", e.getMessage());
			e.printStackTrace();
		}
	}

}
