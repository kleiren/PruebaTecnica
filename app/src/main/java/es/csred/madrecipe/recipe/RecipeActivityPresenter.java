package es.csred.madrecipe.recipe;

import android.support.annotation.Nullable;


public class RecipeActivityPresenter implements RecipeActivityMVP.Presenter {


    @Nullable
    private RecipeActivityMVP.View view;
    private RecipeActivityMVP.Model model;

    public RecipeActivityPresenter(RecipeActivityMVP.Model model) {
        this.model = model;
    }


    @Override
    public void setView(RecipeActivityMVP.View view) {
        this.view = view;

    }
}
