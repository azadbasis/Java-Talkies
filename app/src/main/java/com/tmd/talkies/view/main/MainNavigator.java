package com.tmd.talkies.view.main;

public interface MainNavigator {
    void onStarted();

    void onSuccess(String message);

    void onFailure(String message);
}
