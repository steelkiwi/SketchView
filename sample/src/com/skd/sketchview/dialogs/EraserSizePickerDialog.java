package com.skd.sketchview.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.skd.sketchview.R;

public class EraserSizePickerDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dlg_eraser_size_title);
//		builder.setView(view);
		builder.setNeutralButton(R.string.dlg_close, null);
		
        return builder.create();
	}
	
}
