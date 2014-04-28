package com.skd.sketchview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.skd.sketchview.dialogs.ColorPickerDialog;
import com.skd.sketchview.dialogs.EraserSizePickerDialog;
import com.skd.sketchview.dialogs.SizePickerDialog;
import com.skd.sketchview.settings.SkColor;
import com.skd.sketchview.settings.SkSize;

//TODO
/*
 * save image on orientation change
 * 
 * action bar:
 * --add picture bg (open gallery to choose image)
 * */

public class MainActivity extends ActionBarActivity {

	private SkColor curColor;
	private SkSize curSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initGestureColorAndSize();
		
		if (savedInstanceState == null) {
			attachSketchFragment();
		}
	}

	private void initGestureColorAndSize() {
		curColor = SkColor.getDefaultColor(getResources());
		curSize = SkSize.getDefaultSize(getResources());
	}
	
	private void attachSketchFragment() {
		Bundle args = new Bundle();
		args.putInt(SketchFragment.COLOR, SkColor.getColor(getResources(), curColor));
		args.putInt(SketchFragment.SIZE, curSize.getSize());
		
		SketchFragment frag = new SketchFragment();
		frag.setArguments(args);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container, frag, SketchFragment.class.getSimpleName())
			.commit();
	}
	
	private SketchFragment getSketchFragment() {
		return (SketchFragment) getSupportFragmentManager().findFragmentByTag(SketchFragment.class.getSimpleName());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
        
        MenuItem colorItem = menu.findItem(R.id.action_color);
        colorItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        MenuItem sizeItem = menu.findItem(R.id.action_size);
        sizeItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        MenuItem eraserItem = menu.findItem(R.id.action_eraser);
        eraserItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        MenuItem bgItem = menu.findItem(R.id.action_bg);
        bgItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        MenuItem undoItem = menu.findItem(R.id.action_undo);
        undoItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        MenuItem saveItem = menu.findItem(R.id.action_save);
        saveItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
        return true;
	}
	
	private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
				case R.id.action_color: {
					chooseColor();
					return true;
				}
				case R.id.action_size: {
					chooseSize();
					return true;
				}
				case R.id.action_eraser: {
					chooseEraserSize();
					return true;
				}
				case R.id.action_bg: {
					chooseImage();
					return true;
				}
				case R.id.action_undo: {
					undo();
					return true;
				}
				case R.id.action_save: {
					save();
					return true;
				}
				default: return false;
			}
		}
	};
	
	private void chooseColor() {
		ColorPickerDialog dlg = new ColorPickerDialog();
		dlg.show(getSupportFragmentManager(), ColorPickerDialog.class.getSimpleName());
	}
	
	public void onColorSet(SkColor color) {
		curColor = color;
		getSketchFragment().setGestureColorAndSize(curColor, curSize);
	}
	
	private void chooseSize() {
		SizePickerDialog dlg = new SizePickerDialog();
		dlg.show(getSupportFragmentManager(), SizePickerDialog.class.getSimpleName());
	}
	
	public void onSizeSet(SkSize size) {
		curSize = size;
		getSketchFragment().setGestureColorAndSize(curColor, curSize);
	}
	
	private void chooseEraserSize() {
		EraserSizePickerDialog dlg = new EraserSizePickerDialog();
		dlg.show(getSupportFragmentManager(), EraserSizePickerDialog.class.getSimpleName());
	}
	
	public void onEraserSizeSet(SkSize size) {
		curColor = SkColor.getEraserColor(getResources());
		curSize = size;
		getSketchFragment().setGestureColorAndSize(SkColor.getEraserColor(getResources()), curSize);
	}
	
	private void chooseImage() {

	}
	
	private void undo() {
		getSketchFragment().undo();
	}
	
	private void save() {
		SaveSketchAsyncTask task = new SaveSketchAsyncTask(MainActivity.this);
		task.execute(getSketchFragment().getSketchBimap());
	}

	public SkColor getCurColor() {
		return curColor;
	}

	public SkSize getCurSize() {
		return curSize;
	}
	
}
