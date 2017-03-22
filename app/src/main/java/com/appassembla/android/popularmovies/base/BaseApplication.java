package com.appassembla.android.popularmovies.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by richardthompson on 11/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //installLeakCanary();
    }

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }
}
