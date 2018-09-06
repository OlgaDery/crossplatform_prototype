package com.twovsodds.retrofit_smpl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

//This interface is to define all the necessary RESTful methods. Now it contains only one GET method to receive the list of the
//Point objects which have been wrapped up into Observable.
public interface RestClient {

    //Here the headers, the path and the Query string are being declared.
    @Headers("X-Api-Key: 3.14")
    @GET("api/v1/points_by_district")
    Observable<List<Point>> getPointsByDistrict(@Query("district") String district);
}
