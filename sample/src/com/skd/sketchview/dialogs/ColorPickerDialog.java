package com.skd.sketchview.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.skd.sketchview.MainActivity;
import com.skd.sketchview.R;
import com.skd.sketchview.adapters.ColorAdapter;
import com.skd.sketchview.settings.SkColor;

public class ColorPickerDialog extends DialogFragment implements OnItemClickListener {

	private ColorAdapter listAdapter;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(getActivity());
		View view = li.inflate(R.layout.dialog_main, null);
		
		ListView list = (ListView) view.findViewById(android.R.id.list);
		listAdapter = new ColorAdapter(SkColor.getColors(getResources()));
		list.setAdapter(listAdapter);
		list.setOnItemClickListener(this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dlg_color_title);
		builder.setView(view);
		builder.setNeutralButton(R.string.dlg_close, null);
		
        return builder.create();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		getDialog().dismiss();
		((MainActivity) getActivity()).onColorSet(listAdapter.getItem(position));
	}
	
}
