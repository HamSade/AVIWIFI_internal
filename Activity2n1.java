package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Activity2n1 extends ActionBarActivity {

	private Button btnOK;
	private RadioGroup radioGrpPositions;
	private String position;
	private static final int ACTIVITY2n1_CODE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2n1);

		btnOK = (Button) findViewById(R.id.activity2n1_btn_ok);
		radioGrpPositions = (RadioGroup) findViewById(R.id.activity2n1_radiogrp_1);
		
		radioGrpPositions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.activity2n1_radiobtn_pantsfrontpocket:
					position = "Pants Front Pocket";
					break;				
				case R.id.activity2n1_radiobtn_pantsbackpocket:
					position = "Pants Back Pocket";
					break;
				case R.id.activity2n1_radiobtn_inhandsuspended:
					position = "In Hand";
					break;
				case R.id.activity2n1_radiobtn_inhandtexting:
					position = "In Hand (Texting)";
					break;
				case R.id.activity2n1_radiobtn_inhandtalking:
					position = "In Hand (Talking)";
					break;
				case R.id.activity2n1_radiobtn_shirtpockettop:
					position = "Shirt (Top)";
					break;
				case R.id.activity2n1_radiobtn_shirtpocketbottom:
					position = "Shirt (Bottom)";
					break;
				}
			}
		});			
		
		// set listener for OK button
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity2 = new Intent(getApplicationContext(),Activity2.class);
				toActivity2.putExtra("position", position);
				setResult(ACTIVITY2n1_CODE,toActivity2);
				finish();
			}
		});
		
		

	}



}
