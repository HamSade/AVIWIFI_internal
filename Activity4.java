package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class Activity4 extends ActionBarActivity {

	// specific activity variables
	private Button btnNext;
	private Button btnPrevious;
	private Button btnSelectFrequency;
	private static final int ACTIVITY4_CODE = 6;
	private static final int ACTIVITY4n1_CODE = 7;
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE"}; 
	
	// status variables
	private TextView statusShow1;
	private TextView statusShow2;
	private String[] status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity4);

		btnPrevious = (Button) findViewById(R.id.activity4_btn_previous);
		btnNext = (Button) findViewById(R.id.activity4_btn_next);
		btnSelectFrequency = (Button) findViewById(R.id.activity4_btn_selectfrequency);	
		statusShow1 = (TextView) findViewById(R.id.activity4_textv_status1);
		statusShow2 = (TextView) findViewById(R.id.activity4_textv_status2);

		status = new String[statusSet.length];
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		
			for (int cntr1=0;cntr1<status.length-(status.length-5);cntr1++){
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
		
		// set listener for select sampling rate button
		btnSelectFrequency.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity4n1 = new Intent(getApplicationContext(),Activity4n1.class);
				startActivityForResult(toActivity4n1,ACTIVITY4_CODE);
			}
		});

		// set listener for next button
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity5 = new Intent(getApplicationContext(),Activity5.class);
				// add this activity data to the intent
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity5.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}			
				startActivity(toActivity5);
				finish();
			}
		});

		// set listener for previous button
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity3 = new Intent(getApplicationContext(),Activity3.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity3.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}	
				startActivity(toActivity3);
				finish();
			}
		});					
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY4_CODE:
			if (resultCode == ACTIVITY4n1_CODE) {
				String frequency = data.getExtras().getString("frequency");
				status[5] = frequency;
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
				if (status[5]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[5]+" NOT SET, GAME RATE WILL BE USED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[5]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}

			}
		}
	}
	   
	   


}
