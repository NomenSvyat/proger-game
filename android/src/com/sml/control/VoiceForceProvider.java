package com.sml.control;

import android.media.MediaRecorder;
import android.support.annotation.Nullable;

import com.sml.App;
import com.sml.utils.FileStorage;

import java.io.IOException;

public class VoiceForceProvider implements IForceProvider {
    @Nullable
    private MediaRecorder mediaRecorder;
    private FileStorage fileStorage = new FileStorage(App.getInstance());
    private int maxAmplitude = 0;

    @Override
    public float getForce() {
        if (mediaRecorder == null) {
            return 0f;
        }
        maxAmplitude = (maxAmplitude + mediaRecorder.getMaxAmplitude()) / 2;
        System.out.println(String.valueOf(maxAmplitude / 4f));
        return mediaRecorder.getMaxAmplitude() / 4f;
    }

    public void start() {
        if (mediaRecorder != null) return;

        maxAmplitude = 0;

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            // TODO: 31.05.16 check permissions
            //noinspection MissingPermission
            mediaRecorder.setOutputFile(fileStorage.getOutputFile().getAbsolutePath());
        } catch (IOException e) {
            mediaRecorder = null;
            return;
        }

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            mediaRecorder = null;
            return;
        }
        mediaRecorder.start();
    }

    public void stop() {
        if (mediaRecorder == null) return;

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
