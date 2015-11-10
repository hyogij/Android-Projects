package com.example.efficientappexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.efficientappexample.transfer.BundleField;
import com.example.efficientappexample.transfer.BundleMapper;
import com.example.efficientappexample.view.DeclareView;
import com.example.efficientappexample.view.ViewMapper;

public class TestActivity extends Activity implements View.OnClickListener {
	private static final String CLASS_NAME = TestActivity.class
			.getCanonicalName();

	@DeclareView(id = R.id.btnClose, click = "this")
	Button closeButton;

	@DeclareView(id = R.id.txtStringValue)
	TextView stringValue;
	@DeclareView(id = R.id.txtIntValue)
	TextView intValue;

	@BundleField
	public String contents = null;
	@BundleField
	public int year = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		ViewMapper.mapLayout(this, getWindow().getDecorView());

		BundleMapper.fromIntent(this, getIntent());
		setUiContents();
	}

	private void setUiContents() {
		Log.d(CLASS_NAME, "contents: " + contents);
		Log.d(CLASS_NAME, "year: " + year);
		
		stringValue.setText(contents);
		intValue.setText(Integer.toString(year));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnClose :
				finish();
				break;
		}
	}
}