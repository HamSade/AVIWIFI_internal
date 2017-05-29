package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Activity3n1 extends ActionBarActivity {

	private EditText etUserName;
	private EditText etUserAge; 
	private RadioGroup radioGrpGender;
	private Button btnOK;
	private String gender;
	private String name;
	private String age;
	
	private static final int ACTIVITY3n1_CODE = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity3n1);


		etUserName = (EditText) findViewById(R.id.activity3n1_texte_name);
		etUserAge = (EditText) findViewById(R.id.activity3n1_texte_age);
		radioGrpGender = (RadioGroup) findViewById(R.id.activity3n1_radiogrp_1);
		btnOK = (Button) findViewById(R.id.activity3n1_btn_ok);
		
		radioGrpGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.activity3n1_radiobtn_male:
					gender = "Male";
					break;				
				case R.id.activity3n1_radiobtn_female:
					gender = "Female";
					break;		
				}
			}
		});			
		
		// set listener for OK button
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity3 = new Intent(getApplicationContext(),Activity3.class);
				toActivity3.putExtra("gender", gender);
				if (etUserName.getText().length() > 0){
					name = etUserName.getText().toString();
				}else{
					name = null;
				}
				if (etUserAge.getText().length() > 0){
					age = etUserAge.getText().toString();
				}else{
					age = null;
				}
				toActivity3.putExtra("name", name);
				toActivity3.putExtra("age", age);
				setResult(ACTIVITY3n1_CODE,toActivity3);
				finish();
			}
		});
		
		
		
	}



}
