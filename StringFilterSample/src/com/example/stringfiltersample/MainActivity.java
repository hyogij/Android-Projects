package com.example.stringfiltersample;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

/**
 * @description : Sample project shows the use of StringFilter class
 * @author hyogij@gmail.com
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setStringFilter();
	}

	private void setStringFilter() {
		StringFilter stringFilter = new StringFilter(this);

		InputFilter[] allowAlphanumeric = new InputFilter[1];
		allowAlphanumeric[0] = stringFilter.allowAlphanumeric;

		// Allows only alphanumeric characters. Filters special and hangul
		// characters.
		EditText editTextAlphanumeric = (EditText) findViewById(R.id.editText01);
		editTextAlphanumeric.setFilters(allowAlphanumeric);

		InputFilter[] allowAlphanumericHangul = new InputFilter[1];
		allowAlphanumericHangul[0] = stringFilter.allowAlphanumericHangul;

		// Allows only alphanumeric and hangul characters. Filters special
		// characters.
		EditText editTextAlphanumericHangul = (EditText) findViewById(R.id.editText02);
		editTextAlphanumericHangul.setFilters(allowAlphanumericHangul);
	}
}