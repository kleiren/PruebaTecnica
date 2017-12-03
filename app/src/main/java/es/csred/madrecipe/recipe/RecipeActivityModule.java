package es.csred.madrecipe.recipe;

import dagger.Module;
import dagger.Provides;
import es.csred.madrecipe.main.MainActivityMVP;
import es.csred.madrecipe.main.MainActivityPresenter;


@Module
public class RecipeActivityModule {

    @Provides
    public RecipeActivityMVP.Presenter provideRecipeActivityPresenter(RecipeActivityMVP.Model model){
        return new RecipeActivityPresenter(model);
    }

    @Provides
    public RecipeActivityMVP.Model provideRecipeActivityModel() {
        return new RecipeActivityModel();
    }
}
