package xzy.zzia.com.landscaping.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import xzy.zzia.com.landscaping.bean.User;

/**
 * Created by roy on 4/24/16.
 */
public interface ConnService {


    @GET("LoginServlet")
    Call<User> getUser();

}


