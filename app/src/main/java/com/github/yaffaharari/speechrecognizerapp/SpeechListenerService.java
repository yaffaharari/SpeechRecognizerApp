package com.github.yaffaharari.speechrecognizerapp;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SpeechListenerService extends IntentService {

    public SpeechListenerService() {
        super("SpeechListenerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
