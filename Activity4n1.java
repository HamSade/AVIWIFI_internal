package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Activity4n1 extends ActionBarActivity {

	private RadioGroup radioGrpFrequency;
	private Button btnOK;
	private String frequency;
	private static final int ACTIVITY4n1_CODE = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity4n1);

		radioGrpFrequency = (RadioGroup) findViewById(R.id.activity4n1_radiogrp_1);
		btnOK = (Button) findViewById(R.id.activity4n1_btn_ok);
		
		radioGrpFrequency.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.activity4n1_radiobtn_fastest:
					frequency = "Fastest Rate Possible";
					break;				
				case R.id.activity4n1_radiobtn_game:
					frequency = "Gaming Rate";
					break;
				case R.id.activity4n1_radiobtn_normal:
					frequency = "Normal Rate";
					break;
				case R.id.activity4n1_radiobtn_slow:
					frequency = "Slow Rate";
					break;
				}
			}
		});			
		
		// set listener for OK button
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity4 = new Intent(getApplicationContext(),Activity4.class);
				toActivity4.putExtra("frequency",frequency);
				setResult(ACTIVITY4n1_CODE,toActivity4);
				finish();
			}
		});
		
		
		
	}



}
