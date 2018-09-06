package com.twovsodds.retrofit_smpl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This class contains RESTful method call. They are supposed to be called from the methods of Presenter class.

public class RestMethodCalls {
    //The url to request the data
    private String url = "https://albertasights.herokuapp.com/";
    private static final String TAG = "RestClientImpl";

    public RestMethodCalls() {
        Log.d(TAG, "enter RestMethodCalls ()");

        Log.d(TAG, "exit RestMethodCalls ()");
    }

    public Observable startUrlCall() {
        // This method call the method of RestClient interface, which is supposed to deliver the list of Point objects
        //from the server (url is defined above). It wraps the list into Observable object to return it as an Observable to
        //the method of a Presenter class. In turn, the method in the Presenter class delivers this Observable to the UI
        // (PointsFragment class), all the methods calls are being done in a separate thread, what is defined in the related
        //Observer of PointsFragment class.

        Log.i(TAG, "enter startUrlCall()");

        RestClient client = buildRetrofit().create(RestClient.class);
        Observable<List<Point>> call = client.getPointsByDistrict("Calgary");
        Log.i(TAG, "exit startUrlCall()");
        return call;
    }

    private Retrofit buildRetrofit() {
        //Here all the necessary attributes of Retrofit object are being configured
        Log.i(TAG, "enter buildRetrofit()");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Log.i(TAG, "exit buildRetrofit()");
        return retrofit;
    }

}