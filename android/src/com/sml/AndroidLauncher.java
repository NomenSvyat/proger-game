package com.sml;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sml.control.VoiceForceProvider;
import com.sml.repositories.CodeRepository;
import com.sml.utils.LeaderScreen;

public class AndroidLauncher extends AndroidApplication {
    private VoiceForceProvider voiceForceProvider = new VoiceForceProvider();

    @Override
    protected void onPause() {
        voiceForceProvider.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        voiceForceProvider.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        initialize(new ProgerGame(CodeRepository.getInstance(), voiceForceProvider, LeaderScreen.getInstance()), config);
    }
}
