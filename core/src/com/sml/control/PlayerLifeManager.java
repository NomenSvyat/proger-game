package com.sml.control;

import com.sml.GameWorldConsts;

public class PlayerLifeManager implements LifeManager {

    private OnLifeChangeListener onLifeChangeListener;
    private int lifeCount;

    public PlayerLifeManager(OnLifeChangeListener onLifeChangeListener) {
        lifeCount = GameWorldConsts.PLAYER_LIFE_COUNT;
        this.onLifeChangeListener = onLifeChangeListener;
    }

    public PlayerLifeManager(OnLifeChangeListener onLifeChangeListener, int lifeCount) {
        this.lifeCount = lifeCount;
        this.onLifeChangeListener = onLifeChangeListener;
    }

    @Override
    public void minusLife() {
        lifeCount--;
        onLifeChangeListener.onLifeMinus();
        if(lifeCount <= 0){
            onLifeChangeListener.onLifeCountEnded();
        }
    }

    public interface OnLifeChangeListener {
        void onLifeMinus();
        void onLifeCountEnded();

    }
}
