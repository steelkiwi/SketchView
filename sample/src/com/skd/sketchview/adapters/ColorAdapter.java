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

public class ColorAdapter extends BaseAdapter {

	private ArrayList<SkColor> colors;
	
	public ColorAdapter(ArrayList<SkColor> colors) {
		super();
		this.colors = colors;
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
