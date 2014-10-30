package com.example.efficientappexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.efficientappexample.transfer.BundleField;
import com.example.efficientappexample.transfer.BundleMapper;
import com.example.efficientappexample.view.DeclareView;
import com.example.efficientappexample.view.ViewMapper;

// 간결하고 효율적인 안드로이드 앱 구조
// 1) Resource Id Mapping 최적화 : Annotation과 Mapping 을 이용하여 OnClickListener 최소화
// 2) BundleMapper를 이용한 간결화 : Annotation을 이용하여 키 맵핑 최소화 
public class MainActivity extends Activity implements View.OnClickListener {
	private static final String CLASS_NAME = MainActivity.class
			.getCanonicalName();

	@DeclareView(id = R.id.btnNext, click = "this")
	private Button nextButton;

	@DeclareView(id = R.id.txtStringValue)
	private TextView stringValue;
	@DeclareView(id = R.id.txtIntValue)
	private TextView intValue;

	// This variable should be declared 'public'. Because the getField method
	// will only find the field if it's public.
	@BundleField
	public String contents = null;
	@BundleField
	public int year = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// @DeclareView 사용하기 위한 map 함수 호출 
		ViewMapper.mapLayout(this, getWindow().getDecorView());

		setUiContents();
	}

	private void setUiContents() {
		stringValue.setText("Hello World!");
		intValue.setText("2014");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnNext :
				openTestActivity();
				break;
		}
	}

	private void openTestActivity() {
		contents = stringValue.getText().toString();
		year = Integer.parseInt(intValue.getText().toString());

		Log.d(CLASS_NAME, "contents: " + contents);
		Log.d(CLASS_NAME, "year: " + year);
		
		Intent intent = new Intent(MainActivity.this, TestActivity.class);
//		intent.putExtra("contents", contents);
//		intent.putExtra("year", year);
		BundleMapper.toBundle(this, intent.getExtras());
		startActivity(intent);
	}
}