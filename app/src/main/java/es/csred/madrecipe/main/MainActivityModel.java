package es.csred.madrecipe.main;

import es.csred.madrecipe.http.RecipePuppyAPI;
import es.csred.madrecipe.http.apimodel.RecipePuppy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivityModel implements MainActivityMVP.Model{

    private RecipePuppyAPI recipePuppyAPI;

    public MainActivityModel(RecipePuppyAPI recipePuppyAPI){

        this.recipePuppyAPI = recipePuppyAPI;

    }

    @Override
    public void getRecipes(String text, final DataCallback callback) {
        Call<RecipePuppy> call = recipePuppyAPI.getResults(text);

        call.enqueue(new Callback<RecipePuppy>() {
            @Override
            public void onResponse(Call<RecipePuppy> call, Response<RecipePuppy> response) {
                if (response.body() != null) {
                     callback.onSuccess(response.body().getResults());
                }

            }

            @Override
            public void onFailure(Call<RecipePuppy> call, Throwable t) {
                callback.onError();
                t.printStackTrace();
            }
        });


    }

}
