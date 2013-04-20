package com.example.callRecorder;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Audio_Recorder_Class {

	  final MediaRecorder recorder = new MediaRecorder();
	  final String path;

	  /**
	   * Creates a new audio recording at the given path (relative to root of SD card).
	   */
	  public Audio_Recorder_Class(String path) {
	    this.path = sanitizePath(path);
	  }

	  private String sanitizePath(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".amr";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + "/My records" +path;
	  }

	  /**
	   * Starts a new recording.
	   */
	  public void start() throws IOException {
	    String state = android.os.Environment.getExternalStorageState();
	    if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {
	        throw new IOException("SD Card is not mounted.  It is " + state + ".");
	    }

	    // make sure the directory we plan to store the recording in exists
	    File directory = new File(path).getParentFile();
	    if (!directory.exists() && !directory.mkdirs()) {
	      throw new IOException("Path to file could not be created.");
	    }

	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(path);
	    recorder.prepare();
	    recorder.start();
	    Log.d("TAG", "rec stared");
	  }

	  /**
	   * Stops a recording that has been previously started.
	   */
	  public void stop() throws IOException {
	    recorder.stop();
	    recorder.release();
	    
	    Log.d("TAG", "rec stoped");
	  }

	}