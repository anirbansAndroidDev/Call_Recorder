package com.example.callRecorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class Call_Recorder_Service extends Service {
	MediaRecorder recorder;
	File audiofile;
	String name,phonenumber;
	String audio_format;
	public String Audio_Type;
	int audioSource;
	Context context;
	private Handler handler;
	Timer timer;
	Boolean offHook=false,ringing=false;
	Toast toast; 
	Boolean isOffHook=false;
	public class CustomPhoneStateListener extends PhoneStateListener {

		private static final String TAG = "CustomPhoneStateListener";
		
		Calendar c = Calendar.getInstance(); 
		int seconds = c.get(Calendar.SECOND);
				
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy_HHS mm ss");
		String currentDateandTime = sdf.format(new Date());

		Audio_Recorder_Class ar = new Audio_Recorder_Class(currentDateandTime);


		@Override
		public void onCallStateChanged(int state, String phonenumber){


			switch(state){
			case TelephonyManager.CALL_STATE_RINGING:
				Log.d(TAG, "CALL_STATE_RINGING");

				break;


			case TelephonyManager.CALL_STATE_OFFHOOK:
				
				if(!isOffHook)
				{
					Log.d(TAG, "Call connected");
					
					isOffHook=true;
					try 
					{
						startRecord();
					} 
					catch (Throwable e) 
					{
						Log.d("Starting Error", e+"");
					} 
				}
				
				break;

			case TelephonyManager.CALL_STATE_IDLE:

				if(isOffHook)
				{
					isOffHook =false;
					Log.d(TAG, "Call disconected");
					
					try 
					{
						stopRecord();
					} 
					catch (Throwable e) 
					{
						Log.d("Stopping Error",e+"");
					}
				}
				break;
				
			default:
				Log.d(TAG, "DEFAULT");
				break;

			} //switch close
		}

		// Start function of media recorder
		public void startRecord() throws IllegalStateException, IOException{
			ar.start();
		}
		// Stop function of media recorder
		public void stopRecord()throws IOException{
			ar.stop();
		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		Log.d("service", "destroy");

		super.onDestroy();
	}
}

