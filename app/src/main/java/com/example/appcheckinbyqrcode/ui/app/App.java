package com.example.appcheckinbyqrcode.ui.app;

import android.app.Application;

import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.example.appcheckinbyqrcode.ui.model.SessionManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.getInstance().init(this);

        ApiConfig config = ApiConfig.builder().context(this).baseUrl(SessionManager.getInstance().getKeySaveCityName())
                .auth(SessionManager.getInstance().getKeySaveToken())
                .build();
        ApiClient.getInstance().init(config);
    }
}
