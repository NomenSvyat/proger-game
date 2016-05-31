package com.sml.utils;

import android.content.Context;
import android.content.Intent;

import com.sml.LeaderActivity;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class LeaderScreen implements ILeaderScreen {

    private static LeaderScreen instance;
    public Context context;
    public static LeaderScreen getInstance() {
        if (instance == null)
            instance = new LeaderScreen();
        return instance;
    }

    private LeaderScreen() {}

    @Override
    public void showLeaderScreen(int score) {
        if (context != null) {
            Intent intent = new Intent(context, LeaderActivity.class);
            intent.putExtra("score", score);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }
}
