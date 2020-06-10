package com.github.yaffaharari.speechrecognizerapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView result_tv;
    private Button start_listen_btn,stop_listen_btn,mute;
    private SpeechRecognizerManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
    }


    private void findViews()
    {
        result_tv=(TextView)findViewById(R.id.result_tv);
        start_listen_btn=(Button)findViewById(R.id.start_listen_btn);
   /*     stop_listen_btn=(Button)findViewById(R.id.stop_listen_btn);
        mute=(Button)findViewById(R.id.mute);*/
    }


    private void setClickListeners()
    {
        start_listen_btn.setOnClickListener(this);
//        stop_listen_btn.setOnClickListener(this);
//        mute.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(PermissionHandler.checkPermission(this,PermissionHandler.RECORD_AUDIO)) {

            switch (v.getId()) {
                case R.id.start_listen_btn:
                    if(mSpeechManager==null)
                    {
                        SetSpeechListener();
                    }
                    else if(!mSpeechManager.ismIsListening())
                    {
                        mSpeechManager.destroy();
                        SetSpeechListener();
                    }
                    result_tv.setText(getString(R.string.you_may_speak));

                    break;
 /*               case R.id.stop_listen_btn:
                    if(mSpeechManager!=null) {
                        result_tv.setText(getString(R.string.destroied));
                        mSpeechManager.destroy();
                        mSpeechManager = null;
                    }
                    break;
                case R.id.mute:
                    if(mSpeechManager!=null) {
                        if(mSpeechManager.isInMuteMode()) {
                            mute.setText(getString(R.string.mute));
                            mSpeechManager.mute(false);
                        }
                        else
                        {
                            mute.setText(getString(R.string.un_mute));
                            mSpeechManager.mute(true);
                        }
                    }
                    break;*/
            }
        }
        else
        {
            PermissionHandler.askForPermission(PermissionHandler.RECORD_AUDIO,this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case PermissionHandler.RECORD_AUDIO:
                if(grantResults.length>0) {
                    if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                        start_listen_btn.performClick();
                    }
                }
                break;

        }
    }

    private void SetSpeechListener()
    {
        mSpeechManager = new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {
                if(results!=null && results.size()>0)
                {
                    if(results.size()==1)
                    {
                       // mSpeechManager.destroy();
                        mSpeechManager = null;
                        result_tv.setText(results.get(0));
                    }
                    else {
                        StringBuilder sb = new StringBuilder();
                        if (results.size() > 5) {
                            results = (ArrayList<String>) results.subList(0, 5);
                        }
                        for (String result : results) {
                            sb.append(result).append("\n");
                        }
                        result_tv.setText(sb.toString());
                    }

                    for (String result : results) {
                        if(String.valueOf(result).contentEquals("אוכל"))
                            Toast.makeText(MainActivity.this, "coollll!!", Toast.LENGTH_SHORT).show();
                    }
                }

                else
                    result_tv.setText(getString(R.string.no_results_found));
            }
        });
    }

/*    @Override
    protected void onPause() {
        if(mSpeechManager!=null) {
          mSpeechManager.destroy();
            mSpeechManager=null;
        }
        super.onPause();
    }*/

    @Override
    protected void onDestroy() {
        if(mSpeechManager != null)
            SetSpeechListener();
        super.onDestroy();
    }
}