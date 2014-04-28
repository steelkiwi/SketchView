package com.skd.sketchview;

import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.skd.sketchview.dialogs.ColorPickerDialog;
import com.skd.sketchview.dialogs.EraserSizePickerDialog;
import com.skd.sketchview.dialogs.SizePickerDialog;
import com.skd.sketchview.settings.SkColor;
import com.skd.sketchview.settings.SkSize;

public class MainActivity extends ActionBarActivity {

	private static final int IMAGE_PICK_INTENT = 1;
	
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg0 == IMAGE_PICK_INTENT && arg1 == RESULT_OK) {
			Uri uri = arg2.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
				getSketchFragment().setSketchBackground(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("image/*");
		startActivityForResult(i, IMAGE_PICK_INTENT);
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
