package es.csred.madrecipe.main;

import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import es.csred.madrecipe.http.apimodel.Result;


public interface MainActivityMVP {


    interface View{


        void initViews();

        void search(SearchView searchView);

        void setRecipes(List<Result> results);

        void startRecipeActivity(Result result);
    }

    interface Presenter {

        void setView(MainActivityMVP.View view);

        void searchRequest(String text);

    }

    interface Model {

        void getRecipes(String text, DataCallback callback);


    }
}
