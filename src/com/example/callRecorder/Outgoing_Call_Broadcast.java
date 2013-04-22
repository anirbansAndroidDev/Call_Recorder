package com.example.callRecorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Contacts;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Outgoing_Call_Broadcast extends BroadcastReceiver{
	int flag=1;

	String incoming_nr;
	int outgoing;
	static MediaRecorder recorder_out;
	File audiofile;
	static String pass_phone_no="no_number";
	static String pass_name="no_name";
	String date;
	public Context cont;

	SQLiteDatabase db;
	String original_state;
	static int count=0;
	static String state_out;
	int validate;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		cont=context;
		recorder_out = new MediaRecorder();

		if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL"))
		{


			Bundle bundle = intent.getExtras();

			String name="no name";
			if(null == bundle)
				return;
			String state = bundle.getString(TelephonyManager.EXTRA_STATE);
			String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

			Log.i("OutgoingCallReceiver",phonenumber);
			Log.i("OutgoingCallReceiver",bundle.toString());
			Log.i("OutgoingCallReceiver","State: "+ state);

			Toast.makeText(context,"Phone No" + phonenumber, 10000).show();

			//testing ends


			Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

			if(isSDPresent)
			{
				// yes SD-card is present

				Toast.makeText(context,"Recording started", 10000).show();	
				Intent intent2 = new Intent(context, Call_Recorder_Service.class);
				context.startService(intent2);

			}
			else
			{
				Toast.makeText(context, "SD Card NOT Mounted Recordings will not be saved", 10000).show();
			}
		}
	}


	private int getFile(File dirPath) 
	{
		File f = dirPath;// new File(dirPath);
		File[] files  =  f.listFiles();

		if(files != null)
			for(int i=0; i < files.length; i++)
			{
				File file = files[i];
				if(file.isDirectory())
				{
					count ++;
					//  getFile(file.getAbsolutePath()); 
					getFile(dirPath);
				}
				else
				{
					count ++;
				}
			}
		return count;
	}

}









