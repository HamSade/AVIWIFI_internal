package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Activity5n1 extends ActionBarActivity {

	private RadioGroup radioGrpMode;
	private Button btnOK;
	private String mode;
	private static final int ACTIVITY5n1_CODE = 9;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity5n1);
		
		radioGrpMode = (RadioGroup) findViewById(R.id.activity5n1_radiogrp_1);
		btnOK = (Button) findViewById(R.id.activity5n1_btn_ok);
		
		radioGrpMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.activity5n1_radiobtn_basic:
					mode = "Basic";
					break;				
				case R.id.activity5n1_radiobtn_normal:
					mode = "Normal";
					break;
				case R.id.activity5n1_radiobtn_advanced:
					mode = "Advanced";
					break;
				}
			}
		});			
		
		// set listener for OK button
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity5 = new Intent(getApplicationContext(),Activity5.class);
				toActivity5.putExtra("mode",mode);
				setResult(ACTIVITY5n1_CODE,toActivity5);
				finish();
			}
		});
		
		
		
	}

}
