package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class Activity1 extends ActionBarActivity {

	// specific activity variables
	private Button btnNext;
	private Button btnSkip;
	private Button btnSelectActivity;
	private static final int ACTIVITY1_CODE = 0;
	private static final int ACTIVITY1n1_CODE = 1;
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE"}; 
	
	// status variables
	private TextView statusShow1;
	private TextView statusShow2;
	private String[] status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity1);
		
		btnNext = (Button) findViewById(R.id.activity1_btn_next);
		btnSkip = (Button) findViewById(R.id.activity1_btn_previous);
		btnSelectActivity = (Button) findViewById(R.id.activity1_btn_selectactivity);
		statusShow1 = (TextView) findViewById(R.id.activity1_textv_status1);
		statusShow2 = (TextView) findViewById(R.id.activity1_textv_status2);

		// retrieve the intent data for thus-this-activity data (get rid of data
		// after this activity)		
		status = new String[statusSet.length];
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		
			for (int cntr1=0;cntr1<status.length-(status.length);cntr1++){
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
		

		// set listener for select activity button
		btnSelectActivity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity1n1 = new Intent(getApplicationContext(),
						Activity1n1.class);
				startActivityForResult(toActivity1n1, ACTIVITY1_CODE);
			}
		});

		// set listener for next button
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity2 = new Intent(getApplicationContext(),
						Activity2.class);
				// add this activity data to the intent
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity2.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}	
				startActivity(toActivity2);
				finish();
			}
		});
		
		// set listener for skip button
		btnSkip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity5 = new Intent(getApplicationContext(),
						Activity5.class);
				// add this activity data to the intent
				status[0] = null;
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity5.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}			
				startActivity(toActivity5);
				finish();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY1_CODE:
			if (resultCode == ACTIVITY1n1_CODE) {
				String action = data.getExtras().getString("action");
				status[0] = action;
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
				if (status[0]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[0]+" NOT SET, UNLABELED DATA DETECTED ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[0]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}
			}
		}
	}

}
