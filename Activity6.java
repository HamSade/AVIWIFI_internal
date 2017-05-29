package logger.logit;

import java.io.File;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;

public class Activity6 extends ActionBarActivity implements AnimationListener {

	// specific activity variables
	private Button btnStartLogger;
	private Button btnStopLogger;
	private Button btnStartOver;
	private Button btnPrevious;
	private String[] statusSet = {"ACTIVITY","PHONE POSITION","USER NAME","USER AGE","USER GENDER","SAMPLING RATE","MODE"}; 
	private Boolean isLoggerOn = false;
	private TextView tv1;
	private Animation mAnimation1;

	
	// status variables
	private TextView statusShow1;
	private TextView statusShow2;
	private String[] status;
	private TextView loggerStatusShow;
	private boolean isLabeled = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity6);
		
		btnStartLogger = (Button) findViewById(R.id.activity6_btn_startlogger);
		btnStopLogger = (Button) findViewById(R.id.activity6_btn_stoplogger);
		btnStartOver = (Button) findViewById(R.id.activity6_btn_startover);
		btnPrevious = (Button) findViewById(R.id.activity6_btn_previous);

		// animation presets
		tv1 = (TextView) findViewById(R.id.activity6_textv_logger);
        mAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
        mAnimation1.setAnimationListener(this);

		statusShow1 = (TextView) findViewById(R.id.activity6_textv_status1);
		statusShow2 = (TextView) findViewById(R.id.activity6_textv_status2);
		
		loggerStatusShow = (TextView) findViewById(R.id.activity6_textv_loggingstatus2);
		
		// retrieve the status from the intent
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
		if (status[0]==null || status[1]==null){
			isLabeled = false;
		}else{
			isLabeled = true;
		}
		if (status[5] == null){
			status[5] = "Gaming Rate";
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
		statusShow1.append("LABELED ON?");
		if (isLabeled){
			statusShow2.append("Labeled Data");
		}else{
			statusShow2.append("Unlabeled Data");
		}
	
    	//Build the directory on the external memory 	
		File myFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"LOGit");		
		if(!myFolder.exists()){
			myFolder.mkdir();				
		}
		File myFolder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"LOGit"+"/"+"Labeled");		
		if(!myFolder1.exists()){
			myFolder1.mkdir();				
		}
		File myFolder11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"LOGit"+"/"+"Unlabeled");		
		if(!myFolder11.exists()){
			myFolder11.mkdir();				
		}
		
		// set the listener for the start over button
		btnStartOver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity1 = new Intent(getApplicationContext(),Activity1.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity1.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}
				if (isLoggerOn){
					Intent toStopLoggerService = new Intent(getApplicationContext(),Activity6LoggerService.class);
					isLoggerOn = false;					
					Toast toast= Toast.makeText(getApplicationContext(),"LOGGER SERVICE STOPPED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					stopService(toStopLoggerService);
				}
				startActivity(toActivity1);
				finish();
			}
		});		
			
		// set the listener for the back button
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toActivity5 = new Intent(getApplicationContext(),Activity5.class);
				for (int cntr1=0;cntr1<status.length;cntr1++){
					toActivity5.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
				}
				if (isLoggerOn){
					Intent toStopLoggerService = new Intent(getApplicationContext(),Activity6LoggerService.class);
					isLoggerOn = false;					
					Toast toast= Toast.makeText(getApplicationContext(),"LOGGER SERVICE STOPPED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					stopService(toStopLoggerService);
				}
				startActivity(toActivity5);
				finish();
			}
		});		
		
			
		// set the listener for the start logger button
		btnStartLogger.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
					Intent toStartLoggerService = new Intent(getApplicationContext(),Activity6LoggerService.class);
					for (int cntr1=0;cntr1<status.length;cntr1++){
						toStartLoggerService.putExtra("status_"+Integer.toString(cntr1),status[cntr1]);
					}
					toStartLoggerService.putExtra("isLabeled",isLabeled);
					isLoggerOn = true;
					loggerStatusShow.setText("Logger is running...");
					tv1.startAnimation(mAnimation1);
					Toast toast= Toast.makeText(getApplicationContext(),"LOGGER STARTED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					startService(toStartLoggerService);
			}
		});		
		
		// set the listener for the stop logger button
		btnStopLogger.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLoggerOn){
					Intent toStopLoggerService = new Intent(getApplicationContext(),Activity6LoggerService.class);
					isLoggerOn = false;					
					loggerStatusShow.setText("Logger has been stopped...");
					Toast toast= Toast.makeText(getApplicationContext(),"LOGGER STOPPED",Toast.LENGTH_SHORT);  
					//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					stopService(toStopLoggerService);
				}
			}
		});				
		
				
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == mAnimation1 && isLoggerOn){
			tv1.startAnimation(mAnimation1);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

		
	
}