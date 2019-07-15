package android.collab.connectedmemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    @GET("/test.json")
    Call<String> getTextList();

}