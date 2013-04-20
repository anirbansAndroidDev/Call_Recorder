package com.example.callRecorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.voicerecorder1.R;



import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_Activity extends Activity {
	Button start,stop;
	MediaRecorder recorder;
	File audiofile;
	 int audioSource;
	 Intent intent2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start=(Button) findViewById(R.id.button1);
		stop=(Button) findViewById(R.id.button2);
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Start Service", 10000).show();
				intent2 = new Intent(getApplicationContext(), Call_Recorder_Service.class);
                getApplicationContext().startService(intent2);
			}
		});
		
		stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Stop Service", 10000).show();
				intent2 = new Intent(getApplicationContext(), Call_Recorder_Service.class);
                getApplicationContext().stopService(intent2);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void startRecord() throws IllegalStateException, IOException{
		
		
		//String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
		String out = new SimpleDateFormat("hh-mm-ss").format(new Date());
			  //Toast.makeText(getApplicationContext(),"Recording....",Toast.LENGTH_SHORT).show();
			  File sampleDir = new File(Environment.getExternalStorageDirectory(), "/Recording");
			  if(!sampleDir.exists()){
			  sampleDir.mkdirs();
			  }
			 String file_name= "Record";
		    audiofile = File.createTempFile(file_name, ".3gp", sampleDir);
	String path=Environment.getExternalStorageDirectory().getAbsolutePath();
	//recorder = null;
	
	audioSource = MediaRecorder.AudioSource.MIC;
	
	recorder=new MediaRecorder();
	recorder.setAudioSource(audioSource);
	//recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); 
	recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
	recorder.setOutputFile(audiofile.getAbsolutePath()); 

	recorder.prepare(); 
	recorder.start();

	Log.d("TAG", "recording started");
	 
	  
	}
	/// Stop function of media recorder
	public void stopRecord()throws IOException{
		 if(recorder!=null)
		 {
			 Log.d("TAG", "record not null in stop");   
			 recorder.stop();
			 Log.d("TAG", "recording stopped");  
			 recorder.release();
			 recorder.reset();
	  Toast.makeText(getApplicationContext(),"Recording Stopped", 10000).show();
	  
	  Log.d("TAG", "recording stopped");
		 }
		 else
		 {
			 Log.d("TAG", "recording stopped error");
		 }
	  recorder.release();
		 
	}

}
