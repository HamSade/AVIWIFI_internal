package logger.logit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends ActionBarActivity {

	// specific activity variables
	private Button btnNext;
	private Button btnSelectPosition;
	private Button btnPrevious;
	private static final int ACTIVITY2_CODE = 2;
	private static final int ACTIVITY2n1_CODE = 3;
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE"}; 

	// status variables
	private TextView statusShow1;
	private TextView statusShow2;
	private String[] status;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2);
		
		btnNext = (Button) findViewById(R.id.activity2_btn_next);
		btnPrevious = (Button) findViewById(R.id.activity2_btn_previous);
		btnSelectPosition = (Button) findViewById(R.id.activity2_btn_selectposition);
		statusShow1 = (TextView) findViewById(R.id.activity2_textv_status1);
		statusShow2 = (TextView) findViewById(R.id.activity2_textv_status2);
		
		status = new String[statusSet.length];
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		
			for (int cntr1=0;cntr1<status.length-(status.length-1);cntr1++){
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
		
		
		// set listener for select position button
		btnSelectPosition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity2n1 = new Intent(getApplicationContext(),Activity2n1.class);
				startActivityForResult(toActivity2n1,ACTIVITY2_CODE);
			}
		});
		
		// set listener for next button
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity3 = new Intent(getApplicationContext(),Activity3.class);
				// add this activity data to the intent
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity3.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}			
				startActivity(toActivity3);
				finish();
			}
		});

		// set listener for previous button
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity1 = new Intent(getApplicationContext(),Activity1.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity1.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}	
				startActivity(toActivity1);
				finish();
			}
		});				  	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY2_CODE:
			if (resultCode == ACTIVITY2n1_CODE) {
				String position = data.getExtras().getString("position");
				status[1] = position;
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
				if (status[1]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[1]+" NOT SET, UNLABELED DATA DETECTED ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[1]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}
			}
		}
	}
	
    
}