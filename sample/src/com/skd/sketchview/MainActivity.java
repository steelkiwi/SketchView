package com.skd.sketchview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.skd.sketchview.dialogs.ColorPickerDialog;
import com.skd.sketchview.dialogs.EraserSizePickerDialog;
import com.skd.sketchview.dialogs.SizePickerDialog;
import com.skd.sketchview.settings.SkColor;
import com.skd.sketchview.settings.SkSize;

//TODO
/*
 * action bar:
 * --add picture bg (open gallery to choose image)
 * */

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			attachSketchFragment();
		}
	}

	private void attachSketchFragment() {
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container, new SketchFragment(), SketchFragment.class.getSimpleName())
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
		Toast.makeText(MainActivity.this, color.getTitle(), Toast.LENGTH_SHORT).show();
	}
	
	private void chooseSize() {
		SizePickerDialog dlg = new SizePickerDialog();
		dlg.show(getSupportFragmentManager(), SizePickerDialog.class.getSimpleName());
	}
	
	public void onSizeSet(SkSize size) {
		Toast.makeText(MainActivity.this, size.getTitle(), Toast.LENGTH_SHORT).show();
	}
	
	private void chooseEraserSize() {
		EraserSizePickerDialog dlg = new EraserSizePickerDialog();
		dlg.show(getSupportFragmentManager(), EraserSizePickerDialog.class.getSimpleName());
	}
	
	public void onEraserSizeSet(SkSize size) {
		Toast.makeText(MainActivity.this, size.getTitle(), Toast.LENGTH_SHORT).show();
	}
	
	private void chooseImage() {

	}
	
	private void save() {
		SaveSketchAsyncTask task = new SaveSketchAsyncTask(MainActivity.this);
		task.execute(getSketchFragment().getSketchBimap());
	}
	
}
