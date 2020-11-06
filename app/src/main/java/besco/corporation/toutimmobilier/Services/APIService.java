package besco.corporation.toutimmobilier.Services;


import java.util.List;

import besco.corporation.toutimmobilier.Models.Annonces;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by fbessan on 15/06/2017.
 */

public interface APIService {


    //Get All Annonces
    @GET("allannonces/")
    Call<List<Annonces>> doGetAnnoncesList();


    //Get All SubCategory
    //@GET("prod-cat/{uid}/")
    //Call<List<SubCategory>> doGetSubCategoriesList(@Header("Authorization") String token, @Path(value = "uid", encoded = true) String uid, @Query("format") String query);

    //http://localdevbpromo.com/api/v0/e/?format=json
    //@GET("/search/repositories")
    //RepositoriesResponse searchRepos(@Query("q") String query, @Query("since") Date since);
    // /search/repositories?q=retrofit&since=2015-08-27
    // /search/repositories?q=retrofit&since=20150827


    //@FormUrlEncoded
    //@POST("/RetrofitExample/insert.php")
    //public void insertUser(@Field("name") String name,@Field("username") String username,@Field("password") String password,@Field("email") String email,Callback<Response> callback);

}
