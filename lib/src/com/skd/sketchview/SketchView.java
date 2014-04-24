package com.skd.sketchview;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class SketchView extends View implements OnGestureListener, OnGesturePerformedListener {
	
	private Paint defaultPaint;
	private HashMap<Pair<Integer, Integer>, Paint> brushes;
	private ArrayList<Pair<Path, Paint>> pathes;
	
	public SketchView(Context context) {
		super(context);
		createDefaultPaint();
	}

	public SketchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SketchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		createDefaultPaint();
	}

	private void createDefaultPaint() {
		defaultPaint = new Paint();
		defaultPaint.setAntiAlias(true);
	    defaultPaint.setDither(true);
	    defaultPaint.setColor(Color.BLACK);
	    defaultPaint.setStyle(Paint.Style.STROKE);
	    defaultPaint.setStrokeJoin(Paint.Join.ROUND);
	    defaultPaint.setStrokeCap(Paint.Cap.ROUND);
	    defaultPaint.setStrokeWidth(8);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		setBackgroundColor(Color.WHITE);
		if (pathes == null) { return; }
		for (int i=0, len=pathes.size(); i<len; i++) {
            canvas.drawPath(pathes.get(i).first, pathes.get(i).second);
        }
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		int color = overlay.getGestureColor();
		int size = (int) overlay.getGestureStrokeWidth();
		Paint paint = getBrushByColorAndSize(color, size);
		Path path = overlay.getGesture().toPath();
		addPath(path, paint);
		this.invalidate();
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {}

	public void setBrushes(HashMap<Pair<Integer, Integer>, Paint> brushes) {
		this.brushes = brushes;
	}
	
	private Paint getBrushByColorAndSize(int color, int size) {
		Paint paint = brushes.get(new Pair<Integer, Integer>(color, size));
		if (paint != null) {
			return paint;
		}
		return defaultPaint;
	}
	
	private void addPath(Path path, Paint paint) {
		if (pathes == null) {
			pathes = new ArrayList<Pair<Path, Paint>>();
		}
		pathes.add(new Pair<Path, Paint>(path, paint));
	}
	
	public Bitmap getBitmap() {
		this.setDrawingCacheEnabled(true);  
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());   
        this.setDrawingCacheEnabled(false);
        return bmp;
	}
	
}
