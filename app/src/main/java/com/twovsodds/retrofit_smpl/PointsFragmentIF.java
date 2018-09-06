package com.twovsodds.retrofit_smpl;

//This interface is a prototype for PointsFragment class. It contains the methods representing different UI states (when user
//is filtering data). Each method implementation is supposed to contain the Observer which subscribes the Observable from
//Presenter class, and this Observable emits the data being modified according to user`s request.

public interface PointsFragmentIF {

    //To filter list of points (being stored in Presenter)
    void countPointsWithCategory(String category);

    //To call Rest API to receive data from the server
    void showAllPointsFirstTime();
}
