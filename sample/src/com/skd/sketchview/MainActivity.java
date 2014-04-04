package com.skd.sketchview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

//TODO
/*
 * action bar:
 * -color -> drop down list (base colors up to 10)
 * -stroke width -> drop down list (3 sizes - thin, medium, thick)
 * -erase -> drop down list (3 sizes - thin, medium, thick)
 * -save (to sd card to external directory of the project)
 * -settings:
 * --add picture bg (open gallery to choose image)
 * */

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new SketchFragment()).commit();
		}
	}

}
