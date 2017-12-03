package es.csred.madrecipe.root;

import android.app.Application;

import es.csred.madrecipe.http.ApiModule;
import es.csred.madrecipe.main.MainActivityModule;
import es.csred.madrecipe.recipe.RecipeActivityModule;



public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .recipeActivityModule(new RecipeActivityModule())
                .mainActivityModule(new MainActivityModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
