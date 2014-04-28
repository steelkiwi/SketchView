package com.skd.sketchview.adapters;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skd.sketchview.R;
import com.skd.sketchview.settings.SkSize;

public class SizeAdapter extends BaseAdapter {

	public enum sizeType { BRUSH, ERASER };
	
	private ArrayList<SkSize> sizes;
	private SkSize curSize;
	private sizeType type;
	
	public SizeAdapter(ArrayList<SkSize> sizes, SkSize curSize, sizeType type) {
		super();
		this.sizes = sizes;
		this.curSize = curSize;
		this.type = type;
	}

	@Override
	public int getCount() {
		return sizes.size();
	}

	@Override
	public SkSize getItem(int position) {
		return sizes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SizeViewHolder holder;
	    if (convertView != null) {
	    	holder = (SizeViewHolder) convertView.getTag();
	    }
	    else {
	    	LayoutInflater li = LayoutInflater.from(parent.getContext());
	    	convertView = li.inflate(R.layout.dialog_list_item, parent, false);
	        holder = new SizeViewHolder(convertView);
	        convertView.setTag(holder);
	    }

	    SkSize size = getItem(position);
	    
	    if (size.getSizeResourceId() == curSize.getSizeResourceId()) {
	    	convertView.setBackgroundColor(parent.getContext().getResources().getColor(R.color.list_highlight_color));
	    }
	    else {
	    	convertView.setBackgroundResource(R.drawable.list_item_bg);
	    }
	    
	    holder.mTitle.setText(size.getTitle());
	    switch (type) {
		    case BRUSH: {
		    	holder.mPreview.setBackgroundColor(parent.getResources().getColor(android.R.color.transparent));
		    	holder.mPreview.setImageResource(android.R.color.black); 
		    	break;
		    }
		    case ERASER: {
		    	holder.mPreview.setBackgroundColor(parent.getResources().getColor(android.R.color.black));
		    	holder.mPreview.setImageResource(android.R.color.white);
		    	break;
		    }
	    }
	    int pad = 0;
	    switch (size.getSizeResourceId()) {
		    case R.dimen.size_brush_thin: pad = (int) parent.getContext().getResources().getDimension(R.dimen.preview_brush_thin_padding); break;
		    case R.dimen.size_brush_medium: pad = (int) parent.getContext().getResources().getDimension(R.dimen.preview_brush_medium_padding); break;
		    case R.dimen.size_brush_thick: pad = (int) parent.getContext().getResources().getDimension(R.dimen.preview_brush_thick_padding); break;
	    }
	    holder.mPreview.setPadding(pad, pad, pad, pad);

	    return convertView;
	}

	static class SizeViewHolder {
		public TextView mTitle;
		public ImageView mPreview;

	    public SizeViewHolder(View view) {
	    	mTitle = (TextView) view.findViewById(R.id.title);
	    	mPreview = (ImageView) view.findViewById(R.id.preview);
	    }
	}
	
}
