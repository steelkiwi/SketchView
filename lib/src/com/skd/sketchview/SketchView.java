package com.skd.sketchview;

import java.util.ArrayList;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class SketchView extends View implements OnGestureListener {
	
	private ArrayList<Pair<Path, Paint>> pathes;
	
	public SketchView(Context context) {
		super(context);
	}

	public SketchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SketchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
		float width = overlay.getGestureStrokeWidth();
		Paint paint = createPaint(color, width);
		
		Path path = overlay.getGesture().toPath();

		addPath(path, paint);
		
		this.invalidate();
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {}

	private Paint createPaint(int color, float width) {
		Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setDither(true);
	    paint.setColor(color);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeJoin(Paint.Join.ROUND);
	    paint.setStrokeCap(Paint.Cap.ROUND);
	    paint.setStrokeWidth(width);
	    return paint;
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
