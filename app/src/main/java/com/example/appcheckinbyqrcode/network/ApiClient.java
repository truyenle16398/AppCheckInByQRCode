package com.example.appcheckinbyqrcode.network;

import com.example.appcheckinbyqrcode.BuildConfig;
import com.example.appcheckinbyqrcode.network.core.BooleanAdapter;
import com.example.appcheckinbyqrcode.network.core.DoubleAdapter;
import com.example.appcheckinbyqrcode.network.core.ForbiddenInterceptor;
import com.example.appcheckinbyqrcode.network.core.IntegerAdapter;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final long TIME_OUT = 300000;
    private static final String AUTHORIZATION = "authorization";
    private static final String AUTHORIZATION_TYPE = "Bearer ";
    private static ApiClient sInstance;
    private ApiService mApiService;

    /**
     * constructor
     */
    private ApiClient() {
        // no instance
    }

    /**
     * Api client
     */
    @SuppressWarnings("unused")
    public synchronized static ApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new ApiClient();
        }
        return sInstance;
    }

    /**
     * Service
     */
    @SuppressWarnings("unused")
    public synchronized static ApiService getService() {
        return getInstance().mApiService;
    }

    /**
     * init confirm url
     */
    @SuppressWarnings("unused")
    public void init(ApiConfig config) {
        // Author
        final String auth;
        auth = AUTHORIZATION_TYPE + config.getAuth();
        // init
        BooleanAdapter booleanAdapter = new BooleanAdapter();
        IntegerAdapter integerAdapter = new IntegerAdapter();
        DoubleAdapter doubleAdapter = new DoubleAdapter();
        // init Gson
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAdapter)
                .registerTypeAdapter(boolean.class, booleanAdapter)
                .registerTypeAdapter(Integer.class, integerAdapter)
                .registerTypeAdapter(int.class, integerAdapter)
                .registerTypeAdapter(Double.class, doubleAdapter)
                .registerTypeAdapter(double.class, doubleAdapter)
                .disableHtmlEscaping()
                .setLenient()
                .create();
        // init OkHttpClient
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient().newBuilder();
        //okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS) // connect timeout
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS) // write timeout
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS); // read timeout
        okHttpBuilder.retryOnConnectionFailure(true);
        okHttpBuilder.interceptors().add(new ForbiddenInterceptor());


        //okHttpClient = okHttpBuilder.build();

        // Log
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.interceptors().add(logInterceptor);
        }
        // AUTHORIZATION
//        Log.d(TAG, "intercept: " + Locale.getDefault().getISO3Language().substring(0, ic_Two));
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header(AUTHORIZATION, auth)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept-Language", Locale.getDefault().getLanguage())
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.15:8888/sdc_event/public/api/")
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }
}
