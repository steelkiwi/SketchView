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
	private sizeType type;
	
	public SizeAdapter(ArrayList<SkSize> sizes, sizeType type) {
		super();
		this.sizes = sizes;
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
	    
	    holder.mTitle.setText(size.getTitle());
	    int sz = (int) parent.getContext().getResources().getDimension(R.dimen.size_brush_thick) - (int) parent.getContext().getResources().getDimension(size.getSize());
	    switch (type) {
		    case BRUSH: {
		    	holder.mPreview.setBackgroundColor(parent.getResources().getColor(android.R.color.transparent));
		    	holder.mPreview.setImageResource(android.R.color.black); 
		    	break;
		    }
		    case ERASER: {
		    	holder.mPreview.setBackgroundColor(parent.getResources().getColor(android.R.color.black));
		    	holder.mPreview.setImageResource(android.R.color.white);
		    	sz = (sz == 0) ? 1 : sz;
		    	break;
		    }
	    }
	    holder.mPreview.setPadding(sz, sz, sz, sz);

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
