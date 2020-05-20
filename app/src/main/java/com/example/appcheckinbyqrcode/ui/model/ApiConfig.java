package com.example.appcheckinbyqrcode.ui.model;

import android.content.Context;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ApiConfig {
    private Context context;
    private String auth;
    private String baseUrl;
}
