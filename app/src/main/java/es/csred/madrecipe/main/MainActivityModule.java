package es.csred.madrecipe.main;

import dagger.Module;
import dagger.Provides;
import es.csred.madrecipe.http.RecipePuppyAPI;
import es.csred.madrecipe.recipe.RecipeActivityMVP;
import es.csred.madrecipe.recipe.RecipeActivityModel;



@Module
public class MainActivityModule {

    @Provides
    public MainActivityMVP.Presenter provideMainActivityPresenter(MainActivityMVP.Model model){
        return new MainActivityPresenter(model);
    }
    @Provides
    public MainActivityMVP.Model provideMainActivityModel(RecipePuppyAPI recipePuppyAPI) {
        return new MainActivityModel(recipePuppyAPI);
    }
}
