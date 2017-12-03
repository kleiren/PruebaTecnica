package es.csred.madrecipe.main;

import android.support.annotation.Nullable;

import java.util.List;

import es.csred.madrecipe.http.apimodel.Result;

public class MainActivityPresenter implements MainActivityMVP.Presenter {


    @Nullable
    private MainActivityMVP.View view;
    private MainActivityMVP.Model model;

    public MainActivityPresenter(MainActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(MainActivityMVP.View view) {

        this.view = view;

    }


    @Override
    public void searchRequest(String text) {

        model.getRecipes(text, new DataCallback() {
            @Override
            public void onSuccess(List<Result> results) {
                view.setRecipes(results);
            }

            @Override
            public void onError() {

            }
        });
    }


}
