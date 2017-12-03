package es.csred.madrecipe.http;

import es.csred.madrecipe.http.apimodel.RecipePuppy;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RecipePuppyAPI {

    @GET ("api")
    Call <RecipePuppy> getResults(@Query("q") String title);
}
