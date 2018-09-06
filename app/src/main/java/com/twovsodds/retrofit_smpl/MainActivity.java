package com.twovsodds.retrofit_smpl;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//This Activity does not contain any UI elements, it only serves as a PointsFragment container.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    boolean isRecreated = false;
    private final static String IS_RECREATED = "IS_RECREATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "enter onCreate(Bundle savedInstanceState)");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Here it is important to indicate if the Activity is being created first time or it is being recreated after configuration
        //changes. If so, the last visible fragment also exists in FragmentManager and should not be added.
        if (savedInstanceState!=null) {
            isRecreated=savedInstanceState.getBoolean(IS_RECREATED);
        }
        if (!isRecreated) {
            getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new PointsFragment()).commit();
        }
        Log.d(TAG, "enter onCreate(Bundle savedInstanceState)");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "enter onSaveInstanceState(Bundle outState)");
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_RECREATED, true);
        Log.d(TAG, "exit onSaveInstanceState(Bundle outState)");
    }
}
