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
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class SketchView extends View implements OnGestureListener, OnGesturePerformedListener {
	
	private int canvasW, canvasH;
	private Rect bitmapRect, canvasRect;
	
	private Paint defaultPaint, bgPaint;
	private HashMap<Pair<Integer, Integer>, Paint> brushes;
	private ArrayList<Pair<Path, Paint>> pathes;
	private Bitmap bgBitmap;
	
	public SketchView(Context context) {
		super(context);
		createDefaultPaint();
		createBgPaint();
	}

	public SketchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SketchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		createDefaultPaint();
		createBgPaint();
	}

	private void createDefaultPaint() {
		defaultPaint = new Paint();
		defaultPaint.setAntiAlias(true);
	    defaultPaint.setDither(false);
	    defaultPaint.setColor(Color.BLACK);
	    defaultPaint.setStyle(Paint.Style.STROKE);
	    defaultPaint.setStrokeJoin(Paint.Join.ROUND);
	    defaultPaint.setStrokeCap(Paint.Cap.ROUND);
	    defaultPaint.setStrokeWidth(8);
	}
	
	private void createBgPaint() {
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setDither(false);
	}
	
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.canvasW = w;
        this.canvasH = h;
        this.canvasRect = new Rect(0, 0, canvasW, canvasH);
        this.bitmapRect = new Rect();
        super.onSizeChanged(w, h, oldw, oldh);
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		setBackgroundColor(Color.WHITE);
		if (bgBitmap != null) {
			canvas.drawBitmap(bgBitmap, bitmapRect, canvasRect, bgPaint);
		}
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
	
	public boolean removeLastPath() {
		if (pathes == null || pathes.size() <= 0) {
			return true;
		}
		pathes.remove(pathes.size()-1);
		this.invalidate();
		return false;
	}
	
	public void setAsBackground(Bitmap bitmap) {
		if (bgBitmap != null) {
			bgBitmap.recycle();
		}
		bgBitmap = bitmap;
		
		int minW = Math.min(canvasW, bitmap.getWidth());
		int maxW = Math.max(canvasW, bitmap.getWidth());
		
		int minH = Math.min(canvasH, bitmap.getHeight());
		int maxH = Math.max(canvasH, bitmap.getHeight());
		
		int bmX = (maxW == bitmap.getWidth())  ? (maxW - minW) / 2 : 0;
		int bmY = (maxH == bitmap.getHeight()) ? (maxH - minH) / 2 : 0;
		
		bitmapRect.set(bmX, bmY, bmX + minW, bmY + minH);
		
		this.invalidate();
	}
	
	public Bitmap getBitmap() {
		this.setDrawingCacheEnabled(true);  
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());   
        this.setDrawingCacheEnabled(false);
        return bmp;
	}
	
}
