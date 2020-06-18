package com.example.appcheckinbyqrcode.ui.app;

import android.app.Application;

import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.getInstance().init(this);

        ApiConfig config = ApiConfig.builder().context(this).baseUrl(url.getUrl())
                .auth(SessionManager.getInstance().getKeySaveToken())
                .build();
        ApiClient.getInstance().init(config);
    }
}
