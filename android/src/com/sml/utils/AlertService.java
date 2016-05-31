package com.sml.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created on 30.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class AlertService {

    public static void showMessage(Context ctx, String message) {
        new AlertDialog.Builder(ctx)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
