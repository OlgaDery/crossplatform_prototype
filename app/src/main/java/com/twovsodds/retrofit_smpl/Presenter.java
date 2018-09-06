package com.twovsodds.retrofit_smpl;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import io.reactivex.Observable;

//This class is generally an equivalent of ModelView class in the MVVM architecture. It is supposed to store the data which
//can be used by UI classes and also observables to be called from the UI classes (in this project, from PointsFragment).
// Observables are being used to make network/DB calls asynchronously.
// When a user conducts a certain manipulations (like button click), the observer in PointsFragment class
// is being created, subscribe the observable from this class, and observable returns the data to the UI.
//Other data (Strings, collections etc) is being used for the internal manipulations.
//To avoid the unneccesary network calls, the state of this class is supposed to be saved even while the configuration is being changed.
//To achieve this, the instance of this class is created only once, and is is being saved in "onSaveInstanceState" method
//of the fragment.

public class Presenter implements Parcelable{
    //the class implements Parcelable interface because it is being saved in the Bundle object
    private static final String TAG = "Presenter";
    private RestMethodCalls client = new RestMethodCalls();

    //Here the points received from the server are being stored. They may be used by UI classes
    protected List<Point> points = new LinkedList<>();

    //Here the name of the filter applied in UI is being stored.
    // It may be used to restore the state of the UI after config changes (like screen rotation).
    protected String filter = PointsFragment.NO_FILTER;


    public Presenter() {

    }

    protected Presenter(Parcel in) {
    }

    public static final Creator<Presenter> CREATOR = new Creator<Presenter>() {
        @Override
        public Presenter createFromParcel(Parcel in) {
            return new Presenter(in);
        }

        @Override
        public Presenter[] newArray(int size) {
            return new Presenter[size];
        }
    };

    //"Getter" and "Setter" for points
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    //"Getter" and "Setter" for filter
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    //This observable gets the server data (calling Retrofit method) and returns it to UI.
    public Observable createSelectObservable() {
        Log.d(TAG, "enter createSelectObservable()");
        Log.d(TAG, "exit createSelectObservable()");
        return client.startUrlCall();
    }

    //This observable filter the data being stored in points list
    public Observable filterCategoriesOnly(String category) {
        Log.d(TAG, "enter filterBuildingsOnly()");
        Log.d(TAG, "exit filterBuildingsOnly()");
        return Observable.fromIterable(getPoints()).filter(point -> point.getCategory().equals(category));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
