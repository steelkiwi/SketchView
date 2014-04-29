/*
 * Copyright (C) 2014 Steelkiwi Development, Julia Zudikova
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skd.sketchview;

import java.io.File;
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

/*
 * A sample of sketch editor which supports basic actions:
 * - set brush color (from a range of available colors)
 * - set brush size (from a range of available sizes)
 * - set eraser size (from a range of available sizes)
 * - set image as background of the sketch
 * - undo option (all gestures can be undone)
 * - save sketch
 * - share sketch.
 */

public class MainActivity extends ActionBarActivity {

	private static final int IMAGE_PICK_INTENT = 1;
	
	private SkColor curColor;
	private SkSize curSize;
	private boolean needShare = false;
	
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
        
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareItem.setOnMenuItemClickListener(onMenuItemClickListener);
        
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
				case R.id.action_share: {
					saveAndShare();
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

	public void sketchSaved(String path) {
		if (needShare) {
			needShare = false;
			share(Uri.fromFile(new File(path)));
		}
	}
	
	private void saveAndShare() {
		needShare = true;
		save();
	}
	
	private void share(Uri uri) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("image/jpeg");
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(shareIntent, "Share Sketch"));
	}
	
	public SkColor getCurColor() {
		return curColor;
	}

	public SkSize getCurSize() {
		return curSize;
	}
	
}
