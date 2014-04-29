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

package com.skd.sketchview.adapters;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skd.sketchview.R;
import com.skd.sketchview.settings.SkColor;

/*
 * Brush colors list adapter. Used for color picker dialog.
 */

public class ColorAdapter extends BaseAdapter {

	private ArrayList<SkColor> colors;
	private SkColor curColor;
	
	public ColorAdapter(ArrayList<SkColor> colors, SkColor curColor) {
		super();
		this.colors = colors;
		this.curColor = curColor;
	}

	@Override
	public int getCount() {
		return colors.size();
	}

	@Override
	public SkColor getItem(int position) {
		return colors.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ColorViewHolder holder;
	    if (convertView != null) {
	    	holder = (ColorViewHolder) convertView.getTag();
	    }
	    else {
	    	LayoutInflater li = LayoutInflater.from(parent.getContext());
	    	convertView = li.inflate(R.layout.dialog_list_item, parent, false);
	        holder = new ColorViewHolder(convertView);
	        convertView.setTag(holder);
	    }

	    SkColor color = getItem(position);
	    
	    if (color.getColorResourceId() == curColor.getColorResourceId()) {
	    	convertView.setBackgroundColor(parent.getContext().getResources().getColor(R.color.list_highlight_color));
	    }
	    else {
	    	convertView.setBackgroundResource(R.drawable.list_item_bg);
	    }
	    
	    holder.mTitle.setText(color.getTitle());
	    holder.mPreview.setImageResource(color.getColorResourceId());

	    return convertView;
	}

	static class ColorViewHolder {
		public TextView mTitle;
		public ImageView mPreview;

	    public ColorViewHolder(View view) {
	    	mTitle = (TextView) view.findViewById(R.id.title);
	    	mPreview = (ImageView) view.findViewById(R.id.preview);
	    }
	}
	
}
