package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class Activity3 extends ActionBarActivity {

	// specific activity variables
	private Button btnNext;
	private Button btnPrevious;
	private Button btnEnterInfo;
	private static final int ACTIVITY3_CODE = 4;
	private static final int ACTIVITY3n1_CODE = 5;
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE"}; 
	
	// status variables
	private TextView statusShow1;
	private TextView statusShow2;
	private String[] status;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity3);
		
		btnPrevious = (Button) findViewById(R.id.activity3_btn_previous);
		btnNext = (Button) findViewById(R.id.activity3_btn_next);
		btnEnterInfo = (Button) findViewById(R.id.activity3_btn_enteruserinfo);		
		statusShow1 = (TextView) findViewById(R.id.activity3_textv_status1);
		statusShow2 = (TextView) findViewById(R.id.activity3_textv_status2);

		
		status = new String[statusSet.length];
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		
			for (int cntr1=0;cntr1<status.length-(status.length-2);cntr1++){
				status[cntr1] = extras.getString("status_"+Integer.toString(cntr1));
			}	
		}else{
			for (int cntr1=0;cntr1<status.length;cntr1++){
				status[cntr1] = null;
			}
		}
		
		// show the status on statusShow
		statusShow1.setText("");
		statusShow2.setText("");
		for (int cntr1 = 0; cntr1 < status.length; cntr1++) {
			statusShow1.append(statusSet[cntr1]);
			if (status[cntr1] != null) {
				statusShow2.append(status[cntr1]);
			} else {
				statusShow2.append("?");
			}
			statusShow1.append("\n");
			statusShow2.append("\n");
		}
		
	
		// set listener for user info button
		btnEnterInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity3n1 = new Intent(getApplicationContext(),Activity3n1.class);
				startActivityForResult(toActivity3n1,ACTIVITY3_CODE);
			}
		});
		
		// set listener for next button
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity4 = new Intent(getApplicationContext(),Activity4.class);
				// add this activity data to the intent
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity4.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}			
				startActivity(toActivity4);
				finish();
			}
		});

		// set listener for previous button
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity2 = new Intent(getApplicationContext(),Activity2.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity2.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}	
				startActivity(toActivity2);
				finish();
			}
		});			
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY3_CODE:
			if (resultCode == ACTIVITY3n1_CODE) {
				String name = data.getExtras().getString("name");
				String age = data.getExtras().getString("age");
				String gender = data.getExtras().getString("gender");
				status[2] = name;
				status[3] = age;
				status[4] = gender;
				// show the status on statusShow
				statusShow1.setText("");
				statusShow2.setText("");
				for (int cntr1 = 0; cntr1 < status.length; cntr1++) {
					statusShow1.append(statusSet[cntr1]);
					if (status[cntr1] != null) {
						statusShow2.append(status[cntr1]);
					} else {
						statusShow2.append("?");
					}
					statusShow1.append("\n");
					statusShow2.append("\n");
				}
				if (status[2]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[2]+" NOT SET ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[2]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}
				if (status[3]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[3]+" NOT SET ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[3]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}
				if (status[4]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[4]+" NOT SET ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[4]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}

			}
		}
	}
	    
	   
	
}