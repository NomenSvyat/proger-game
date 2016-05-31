package com.sml;

import android.app.Application;

import com.sml.utils.SettingsService;

/**
 * Created on 30.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SettingsService.getInstance().context = this;
    }
}
