package com.skd.sketchview.sketch;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Paint;
import android.util.Pair;

import com.skd.sketchview.settings.SkColor;
import com.skd.sketchview.settings.SkSize;

public class SketchManager {

	public static HashMap<Pair<Integer, Integer>, Paint> createBrushes(Resources r) {
		HashMap<Pair<Integer, Integer>, Paint> brushes = new HashMap<Pair<Integer, Integer>, Paint>();
		
		ArrayList<SkColor> colors = SkColor.getColors(r);
		ArrayList<SkSize> sizes = SkSize.getSizes(r);
		for (int i=0, len=sizes.size(); i<len; i++) {
			for (int j=0, max=colors.size(); j<max; j++) {
				int color = r.getColor(colors.get(j).getColorResourceId());
				int size = sizes.get(i).getSize();
				Paint paint = createBrush(color, size);
				brushes.put(new Pair<Integer, Integer>(color, size), paint);
			}
		}
		
		return brushes;
	}
	
	private static Paint createBrush(int color, float width) {
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
	
}
