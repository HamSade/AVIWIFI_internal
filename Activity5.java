package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class Activity5 extends ActionBarActivity {

	// specific activity variables
	private Button btnNext;
	private Button btnPrevious;
	private Button btnSelectSensors;
	private String mode = "Basic";
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE","MODE"}; 
	private TextView sensorList;
	private static final int ACTIVITY5_CODE = 8;
	private static final int ACTIVITY5n1_CODE = 9;

	// status variables
	private String[] status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity5);

		btnSelectSensors = (Button) findViewById(R.id.activity5_btn_selectmode);
		btnPrevious = (Button) findViewById(R.id.activity5_btn_previous);
		btnNext = (Button) findViewById(R.id.activity5_btn_next);
		sensorList = (TextView) findViewById(R.id.activity5_textv_availablesensors);
		
		status = new String[statusSet.length];
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		
			for (int cntr1=0;cntr1<status.length;cntr1++){
				status[cntr1] = extras.getString("status_"+Integer.toString(cntr1));
			}	
		}else{
			for (int cntr1=0;cntr1<status.length;cntr1++){
				status[cntr1] = null;
			}
		}
		
		sensorList.setText("");
		sensorList.append("Beta version does not allow selection of to-be-logged sensors. Accelerometer, Gyroscope and Barometer are logged if available.");
		sensorList.append("\n\n");
		/*sensorList.append("ON THIS DEVICE\n\n");			
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> deviceSensors = mSensorManager
				.getSensorList(Sensor.TYPE_ALL);
		for (int cntr1 = 1; cntr1 < deviceSensors.size(); cntr1++) {
			String sensorName = deviceSensors.get(cntr1).getName();
			sensorList
			.append("("+Integer.toString(cntr1)+") " + sensorName+"\n");
		}*/
	
		// set listener for select mode button
		btnSelectSensors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity5n1 = new Intent(getApplicationContext(),Activity5n1.class);
				startActivityForResult(toActivity5n1,ACTIVITY5_CODE);
			}
		});
		
		
		// set listener for next button
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity6 = new Intent(getApplicationContext(),Activity6.class);
				// add this activity data to the intent
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity6.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}
				toActivity6.putExtra("sensorMode",mode);
				startActivity(toActivity6);
				finish();
			}
		});

		// set listener for previous button
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity4 = new Intent(getApplicationContext(),Activity4.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity4.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}	
				startActivity(toActivity4);
				finish();
			}
		});	

	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY5_CODE:
			if (resultCode == ACTIVITY5n1_CODE) {
				String mode = data.getExtras().getString("mode");
				status[6] = mode;
				if (status[6]==null){
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[6]+" NOT SET, NORMAL WILL BE USED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
				}else{
					Toast toast= Toast.makeText(getApplicationContext(),statusSet[6]+" SET SUCCESSFULLY ",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					}

			}
		}
	}
	
	
}
