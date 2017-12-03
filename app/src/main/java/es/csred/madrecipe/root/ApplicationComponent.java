package es.csred.madrecipe.root;


import javax.inject.Singleton;

import dagger.Component;
import es.csred.madrecipe.main.MainActivity;
import es.csred.madrecipe.http.ApiModule;
import es.csred.madrecipe.main.MainActivityModule;
import es.csred.madrecipe.recipe.RecipeActivity;
import es.csred.madrecipe.recipe.RecipeActivityModule;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class, MainActivityModule.class, RecipeActivityModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
    void inject(RecipeActivity target);


}
