package com.twovsodds.retrofit_smpl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//This Fragment contains all the UI elements. When a user interacts with the application or the state or the
// configuration changes, the Observers are being created, then they subscribe the Observables from Presenter class
//and receive the data. It is critical to have the only one instance of the Presenter class that it can provide the internal
//state of data for UI. It may be achieved by different mechanisms (including DI into Singleton class), here
// for the simplification are creating the instance of it and saving it in bundle on configuration changes.

public class PointsFragment extends Fragment implements PointsFragmentIF{

    private static final String TAG = "PointsFragment";
    private static final String PRESENTER = "PRESENTER";
    public static final String NO_FILTER = "NO_FILTER";
    public static final String FILTER_BUILDING = "FILTER_BUILDING";
    public static final String FILTER_MONUMENTS = "FILTER_MONUMENTS";

    public Presenter presenter;
    private TextView text;
    private ProgressBar progressBar;
    private Button showBuildings;
    private Button showMonuments;
    CompositeDisposable disposable = new CompositeDisposable();

    public PointsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "enter onCreate(Bundle savedInstanceState) ");
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            if (savedInstanceState.getParcelable(PRESENTER)!=null) {
                presenter = savedInstanceState.getParcelable(PRESENTER);
                Log.i(TAG, "presenter has already initialized");
            } else {
                presenter = new Presenter();
                Log.i(TAG, "creating a new presenter");
            }
        } else {
            presenter = new Presenter();
            Log.i(TAG, "creating a new presenter");
        }
        Log.d(TAG, "exit onCreate(Bundle savedInstanceState) ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Here the UI elements are being initialized
        Log.d(TAG, "enter onCreateView(LayoutInflater inflater, ViewGroup container,\n" +
                "                             Bundle savedInstanceState) ");

        View view = inflater.inflate(R.layout.fragment_points, container, false);
        text = view.findViewById(R.id.text);
        progressBar = view.findViewById(R.id.progressBar);

        showBuildings = view.findViewById(R.id.seeOnlyArchitecture);
        showBuildings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilter(FILTER_BUILDING);
                countPointsWithCategory("building/construction");
            };
        });

        showMonuments = view.findViewById(R.id.seeOnlyMonuments);
        showMonuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilter(FILTER_MONUMENTS);
                countPointsWithCategory("monument/installation");
            };
        });

        text.setVisibility(View.INVISIBLE);
        showBuildings.setVisibility(View.INVISIBLE);
        showMonuments.setVisibility(View.INVISIBLE);

        //Below is the logic to determine if the API has been previously called and the data is already stored
        // //in the Presenter class
        if (presenter.getPoints().size()==0) {
            //No points have been previously saved, calling the REST API and returning observable
            showAllPointsFirstTime();

        } else {
            Log.i(TAG, "data exists");
            //Finding out if any filters had been applied before the activity have been destroyed

            if (presenter.getFilter().equals(NO_FILTER)) {
                updateUI(presenter.getPoints().size());
            } else if (presenter.getFilter().equals(FILTER_BUILDING)) {
                countPointsWithCategory("building/construction");

            } else if (presenter.getFilter().equals(FILTER_MONUMENTS)) {
                countPointsWithCategory("monument/installation");
            }
        }

        Log.d(TAG, "exit onCreateView(LayoutInflater inflater, ViewGroup container,\n" +
                "                             Bundle savedInstanceState) ");
        return view;
    }

    private void updateUI(int pointsListSize) {
        //This method is responsible for UI modification. It is being called from Observer`s "onComplete" method, when
        //the data is ready to show up
        if (progressBar.getVisibility()!=View.GONE) {
            progressBar.setVisibility(View.GONE);
        }
        if (showBuildings.getVisibility()!=View.VISIBLE) {
            showBuildings.setVisibility(View.VISIBLE);
            showMonuments.setVisibility(View.VISIBLE);
        }
        if (text.getVisibility()!=View.VISIBLE) {
            text.setVisibility(View.VISIBLE);
        }
        text.setText("Points requested: "+pointsListSize);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "enter onAttach(Context context)");
        super.onAttach(context);
        Log.d(TAG, "exit onAttach(Context context)");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "enter onDetach()");
        super.onDetach();
        Log.d(TAG, "exit onDetach()");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "enter onStop()");
        super.onStop();
        //disposing disposable to avoid memory leaks
        if (disposable!=null && !disposable.isDisposed()) {
            Log.i(TAG, "disposing disposable");
            disposable.dispose();
        }
        Log.d(TAG, "exit onStop()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "enter onSaveInstanceState(@NonNull Bundle outState)");
        super.onSaveInstanceState(outState);
        outState.putParcelable(PRESENTER, presenter);
        Log.d(TAG, "exit onSaveInstanceState(@NonNull Bundle outState)");
    }


    @Override
    public void countPointsWithCategory(String category) {
        //This method is to filter the points (being stored in the Presenter class) using the RxJava filtering
        //options. The "category" parameter is being received from the front end.
        LinkedList<Point> points = new LinkedList<>();
        Observer obs3 = new Observer() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "enter onCompleted observer3()");
                updateUI(points.size());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "enter onNext observer3(Object o)");
                points.add((Point)o);
            }
        };

        presenter.filterCategoriesOnly(category).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obs3);

    }

    @Override
    public void showAllPointsFirstTime() {
        Observer obs1 = new Observer() {
            final List<Point> sess = new LinkedList<>();

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "enter onCompleted()");
                Log.i(TAG, "points size: "+sess.size());
                presenter.setPoints(sess);
                updateUI(sess.size());
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "enter onNext(Object o)");
                sess.addAll((ArrayList<Point>)o);

            }
        };
        presenter.createSelectObservable().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obs1);

    }


}
