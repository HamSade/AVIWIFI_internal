package logger.logit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class Activity6LoggerService extends Service implements
		SensorEventListener {

	// variables of Activity6Loggerservice
	private SensorManager sensormanager;
	private Sensor accSensor;
	private Sensor gyroSensor;
	private Sensor baroSensor;
	private Sensor linAccSensor;

	private CSVWriter accSensorWriter = null;
	private CSVWriter gyroSensorWriter = null;
	private CSVWriter baroSensorWriter = null;
	private CSVWriter linAccSensorWriter = null;
	private CSVWriter statusWriter;

	// add/remove sensor for advanced mode
	private Sensor temperatureSensor;
	private Sensor gravitySensor;
	private Sensor lightSensor;
	private Sensor magneticSensor;
	private Sensor orientationSensor;
	private Sensor proximitySensor;
	private Sensor rotationVectorSensor;
	
	float[] mGravs = new float[3];
	float[] mGeoMags = new float[3];
	float[] mRotationM = new float[9];
    float[] mInclinationM = new float[9];
	
	private CSVWriter rotationMatrixWriter = null;
	private CSVWriter temperatureSensorWriter = null;
	private CSVWriter gravitySensorWriter = null;
	private CSVWriter lightSensorWriter = null;
	private CSVWriter magneticSensorWriter = null;
	private CSVWriter orientationSensorWriter = null;
	private CSVWriter proximitySensorWriter = null;
	private CSVWriter rotationVectorSensorWriter = null;
	private CSVWriter rssWriter = null;
	private CSVWriter macsWriter;

	private String[] statusSet = { "ACTIVITY", "PHONE POSITION", "USER NAME",
			"USER AGE", "USER GENDER", "SAMPLING RATE", "MODE" };
	private int sampleRate = SensorManager.SENSOR_DELAY_GAME;

	// status variables
	private String[] status;
	private boolean isLabeled = true;

	// WiFi variables
	private WifiManager mWiFiManager;
	private WiFiReceiver mWiFiReceiver;
	private int rssScanIndex = 0;
	private int[][] rssTable;
	private ArrayList<String> accessPointList = new ArrayList<String>();
	private ArrayList<String> rssScanTime = new ArrayList<String>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		
		
		// getting header information
		status = new String[statusSet.length];
		Bundle extras = intent.getExtras();
		if (extras != null) {
			for (int cntr1 = 0; cntr1 < status.length; cntr1++) {
				status[cntr1] = extras.getString("status_"
						+ Integer.toString(cntr1));
			}
		} else {
			for (int cntr1 = 0; cntr1 < status.length; cntr1++) {
				status[cntr1] = null;
			}
		}
		isLabeled = extras.getBoolean("isLabeled");
		
		if (status[5] != null) {
			switch (status[5]) {
			case "Fastest Rate Possible":
				sampleRate = SensorManager.SENSOR_DELAY_FASTEST;
				break;
			case "Gaming Rate":
				sampleRate = SensorManager.SENSOR_DELAY_GAME;
				break;
			case "Normal Rate":
				sampleRate = SensorManager.SENSOR_DELAY_UI;
				break;
			case "Slow Rate":
				sampleRate = SensorManager.SENSOR_DELAY_NORMAL;
				break;
			}
		}

		sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accSensor = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		switch (status[status.length - 1]) {
		case "Normal":
			gyroSensor = sensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			baroSensor = sensormanager.getDefaultSensor(Sensor.TYPE_PRESSURE);
			linAccSensor = sensormanagerƒ
					.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			break;
		case "Advanced":
			gyroSensor = sensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			baroSensor = sensormanager.getDefaultSensor(Sensor.TYPE_PRESSURE);
			linAccSensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			temperatureSensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
			gravitySensor = sensormanager.getDefaultSensor(Sensor.TYPE_GRAVITY);
			lightSensor = sensormanager.getDefaultSensor(Sensor.TYPE_LIGHT);
			magneticSensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			orientationSensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			proximitySensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_PROXIMITY);
			rotationVectorSensor = sensormanager
					.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

			// Initiate WiFi service manager and register for WiFi scan results
			// if case is selected as Advanced:
			mWiFiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			if (mWiFiManager.isWifiEnabled() == false) {
				Toast.makeText(getApplicationContext(),
						"Enabling WiFi...",
						Toast.LENGTH_LONG).show();
				mWiFiManager.setWifiEnabled(true);
			}
			mWiFiReceiver = new WiFiReceiver();
			registerReceiver(mWiFiReceiver, new IntentFilter(
					WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			mWiFiManager.startScan();
			break;
		}

		File dirFolder;
		if (isLabeled) {
			dirFolder = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + "LOGit" + "/" + "Labeled");
		} else {
			dirFolder = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + "LOGit" + "/" + "Unlabeled");
		}
		
/*		int dirFileCount = dirFolder.listFiles().length;
		File myFolder = new File(dirFolder.getAbsolutePath() + "/"
				+ Integer.toString(++dirFileCount));*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		File myFolder = new File(dirFolder.getAbsolutePath() + "/"
				+ currentDateandTime);
		
		myFolder.mkdir();
		try {
			accSensorWriter = new CSVWriter(new FileWriter(
					myFolder.getAbsolutePath() + "/acceleration.csv"), ',');
			statusWriter = new CSVWriter(new FileWriter(
					myFolder.getAbsolutePath() + "/labels.csv"), ',');
			switch (status[status.length - 1]) {
			case "Normal":
				gyroSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/gyroscope.csv"), ',');
				baroSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/barometer.csv"), ',');
				linAccSensorWriter = new CSVWriter(
						new FileWriter(myFolder.getAbsolutePath()
								+ "/linear_acceleration.csv"), ',');
				statusWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/labels.csv"), ',');
				break;
			case "Advanced":
				gyroSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/gyroscope.csv"), ',');
				baroSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/barometer.csv"), ',');
				linAccSensorWriter = new CSVWriter(
						new FileWriter(myFolder.getAbsolutePath()
								+ "/linear_acceleration.csv"), ',');
				temperatureSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/temperature.csv"), ',');
				gravitySensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/gravity.csv"), ',');
				lightSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/light.csv"), ',');
				magneticSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/magnetic.csv"), ',');
				orientationSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/orientation.csv"), ',');
				proximitySensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/proximity.csv"), ',');
				rotationVectorSensorWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/rotation_vector.csv"),
						',');
				rssWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/wifi.csv"), ',');
				statusWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/labels.csv"), ',');
				macsWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/macs.csv"), ',');
				rotationMatrixWriter = new CSVWriter(new FileWriter(
						myFolder.getAbsolutePath() + "/rotation_matrix.csv"),
						',');
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* accSensorWriter.writeNext(status); */
		statusWriter.writeNext(status);

		sensormanager.registerListener(this, accSensor, sampleRate);
		switch (status[status.length - 1]) {
		case "Normal":
			/*
			 * gyroSensorWriter.writeNext(status);
			 * baroSensorWriter.writeNext(status);
			 * linAccSensorWriter.writeNext(status);
			 */
			statusWriter.writeNext(status);

			sensormanager.registerListener(this, gyroSensor, sampleRate);
			sensormanager.registerListener(this, baroSensor, sampleRate);
			sensormanager.registerListener(this, linAccSensor, sampleRate);
			break;
		case "Advanced":
			
			statusWriter.writeNext(status);

			sensormanager.registerListener(this, gyroSensor, sampleRate);
			sensormanager.registerListener(this, baroSensor, sampleRate);
			sensormanager.registerListener(this, linAccSensor, sampleRate);
			sensormanager.registerListener(this, temperatureSensor, sampleRate);
			sensormanager.registerListener(this, gravitySensor, sampleRate);
			sensormanager.registerListener(this, lightSensor, sampleRate);
			sensormanager.registerListener(this, magneticSensor, sampleRate);
			sensormanager.registerListener(this, orientationSensor, sampleRate);
			sensormanager.registerListener(this, proximitySensor, sampleRate);
			sensormanager.registerListener(this, rotationVectorSensor,
					sampleRate);
			break;
		}

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(activity6Receiver, filter);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		sensormanager.unregisterListener(this);
		unregisterReceiver(activity6Receiver);
		if (mWiFiManager.isWifiEnabled() == true) {
			Toast.makeText(getApplicationContext(),
					"Disabling WiFi...",
					Toast.LENGTH_LONG).show();
			mWiFiManager.setWifiEnabled(false);
		}
		try {
			accSensorWriter.close();
			statusWriter.close();
			switch (status[status.length - 1]) {
			case "Normal":
				gyroSensorWriter.close();
				baroSensorWriter.close();
				linAccSensorWriter.close();
				statusWriter.close();
				break;
			case "Advanced":
				gyroSensorWriter.close();
				baroSensorWriter.close();
				linAccSensorWriter.close();
				temperatureSensorWriter.close();
				gravitySensorWriter.close();
				lightSensorWriter.close();
				magneticSensorWriter.close();
				orientationSensorWriter.close();
				proximitySensorWriter.close();
				rotationVectorSensorWriter.close();
				unregisterReceiver(mWiFiReceiver);
				// save rssTable in wifi.csv:
				for (int i = 0; i < accessPointList.size(); i++) {
					String[] temp1 = new String[1];
					temp1[0] = accessPointList.get(i).toString();
					macsWriter.writeNext(temp1);
				}
				for (int i = 0; i < rssTable[0].length; i++) {
					String[] temp = new String[rssTable.length + 1];
					temp[0] = rssScanTime.get(i);
					for (int j = 0; j < rssTable.length; j++) {
						temp[j + 1] = Integer.toString(rssTable[j][i]);
					}
					rssWriter.writeNext(temp);
				}
				rssWriter.close();
				statusWriter.close();
				macsWriter.close();
				rotationMatrixWriter.close();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}

	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	public BroadcastReceiver activity6Receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				// Unregisters the listener and registers it again.
				sensormanager.unregisterListener(Activity6LoggerService.this);
				sensormanager.registerListener(Activity6LoggerService.this,
						accSensor, sampleRate);

				switch (status[status.length - 1]) {
				case "Normal":
					sensormanager
							.unregisterListener(Activity6LoggerService.this);
					sensormanager.registerListener(Activity6LoggerService.this,
							gyroSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							baroSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							linAccSensor, sampleRate);
					break;
				case "Advanced":
					sensormanager
							.unregisterListener(Activity6LoggerService.this);
					sensormanager.registerListener(Activity6LoggerService.this,
							gyroSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							baroSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							linAccSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							temperatureSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							gravitySensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							lightSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							magneticSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							orientationSensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							proximitySensor, sampleRate);
					sensormanager.registerListener(Activity6LoggerService.this,
							rotationVectorSensor, sampleRate);
					break;
				}

			}
		}
	};

	class WiFiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
/*			Toast.makeText(getApplicationContext(),
					"start of scanning for WiFi...",
					Toast.LENGTH_SHORT).show();*/
			List<ScanResult> wifiResults = mWiFiManager.getScanResults();
			rssScanTime.add(Long.toString(wifiResults.get(0).timestamp));
			// update the accessPointList:
			for (int i = 0; i < wifiResults.size(); i++) {
				String bssid = wifiResults.get(i).BSSID;
				int index = accessPointList.indexOf(bssid);
				if (index == -1) {
					accessPointList.add(bssid);
				}
			}
			// preparing the new tempRssTable in size:
			int[][] tempRssTable = new int[accessPointList.size()][rssScanIndex + 1];
			for (int i = 0; i < tempRssTable.length; i++) {
				for (int j = 0; j < tempRssTable[0].length; j++) {
					tempRssTable[i][j] = -110;
				}
			}
			if (rssScanIndex > 0) {
				for (int i = 0; i < rssTable.length; i++) {
					for (int j = 0; j < rssTable[0].length; j++) {
						tempRssTable[i][j] = rssTable[i][j];
					}
				}
			}
			// copying new values for current time into tempRssTable
			for (int i = 0; i < wifiResults.size(); i++) {
				String bssid = wifiResults.get(i).BSSID;
				int rss = wifiResults.get(i).level;
				int index = accessPointList.indexOf(bssid);
				tempRssTable[index][rssScanIndex] = rss;
			}
			// prepare for the next onReceive:
			rssScanIndex = rssScanIndex + 1;
			rssTable = null;
			rssTable = tempRssTable;
/*			Toast.makeText(getApplicationContext(),
					"End of scanning for WiFi...",
					Toast.LENGTH_SHORT).show();*/
			registerReceiver(mWiFiReceiver, new IntentFilter(
					WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			mWiFiManager.startScan();
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		long timeInMillis = (event.timestamp) / 1000000L;
		String[] entriesStr = new String[event.values.length + 1];
		entriesStr[0] = Long.toString(timeInMillis);
		for (int cntr1 = 0; cntr1 < event.values.length; cntr1++) {
			entriesStr[cntr1 + 1] = Float.toString(event.values[cntr1]);
		}
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			accSensorWriter.writeNext(entriesStr);
			System.arraycopy(event.values, 0, mGravs, 0, 3);
			break;
		case Sensor.TYPE_GYROSCOPE:
			gyroSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_PRESSURE:
			baroSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_LINEAR_ACCELERATION:
			linAccSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_AMBIENT_TEMPERATURE:
			temperatureSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_GRAVITY:
			gravitySensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_LIGHT:
			lightSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			magneticSensorWriter.writeNext(entriesStr);
			System.arraycopy(event.values, 0, mGeoMags, 0, 3);
			break;
		case Sensor.TYPE_ORIENTATION:
			orientationSensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_PROXIMITY:
			proximitySensorWriter.writeNext(entriesStr);
			break;
		case Sensor.TYPE_ROTATION_VECTOR:
			rotationVectorSensorWriter.writeNext(entriesStr);
			break;
		}

		if (mGravs != null && mGeoMags != null) {
			// checks that the rotation matrix is found
			boolean success = SensorManager.getRotationMatrix(mRotationM,
					mInclinationM, mGravs, mGeoMags);
			if (success) {	
				String[] temp2 = new String[mRotationM.length+1];
				temp2[0] = entriesStr[0];
				for (int i = 0; i < mRotationM.length; i++){
					temp2[i+1] = Float.toString(mRotationM[i]);
				}			
				rotationMatrixWriter.writeNext(temp2);
			}
		}

		
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
