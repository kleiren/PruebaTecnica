package es.csred.madrecipe.main;

import java.util.List;

import es.csred.madrecipe.http.apimodel.Result;

// Callback to return data from the model request to the view
public interface DataCallback {

    void onSuccess(List<Result> results);
    void onError();
}
