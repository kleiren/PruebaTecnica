package es.csred.madrecipe.recipe;


import es.csred.madrecipe.http.apimodel.Result;


public interface RecipeActivityMVP {


    interface View{

        int getActionBarSize();

        void fillInfo(Result result);

    }

    interface Presenter {

        void setView(RecipeActivityMVP.View view);

    }

    interface Model {

    }
}
