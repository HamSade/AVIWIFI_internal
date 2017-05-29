package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Activity1n1 extends ActionBarActivity {

	private Button btnOK;
	private RadioGroup radioGrpActions;
	private String action;
	private static final int ACTIVITY1n1_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity1n1);

		btnOK = (Button) findViewById(R.id.activity1n1_btn_ok);
		radioGrpActions = (RadioGroup) findViewById(R.id.activity1n1_radiogrp_1);
		
		radioGrpActions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.activity1n1_radiobtn_stand:
					action = "Standing";
					break;				
				case R.id.activity1n1_radiobtn_walk:
					action = "Walking";
					break;
				case R.id.activity1n1_radiobtn_stairsup:
					action = "Stairs (up)";
					break;
				case R.id.activity1n1_radiobtn_elevup:
					action = "Elevator (up)";
					break;
				case R.id.activity1n1_radiobtn_escup:
					action = "Escalator (up)";
					break;					
				case R.id.activity1n1_radiobtn_stairsdown:
					action = "Stairs (down)";
					break;
				case R.id.activity1n1_radiobtn_elevdown:
					action = "Elevator (down)";
					break;
				case R.id.activity1n1_radiobtn_escdown:
					action = "Escalator (down)";
					break;
				case R.id.activity1n1_radiobtn_sit:
					action = "Sitting";
					break;
					
				}
			}
		});			
		
		// set listener for next button
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity1 = new Intent(getApplicationContext(),Activity1.class);
				toActivity1.putExtra("action", action);
				setResult(ACTIVITY1n1_CODE,toActivity1);
				finish();
			}
		});
		
		
	}
	
	
	

}
