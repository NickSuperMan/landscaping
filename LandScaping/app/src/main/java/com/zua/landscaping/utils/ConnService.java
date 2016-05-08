package com.zua.landscaping.utils;


import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Leave;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.bean.Sign;
import com.zua.landscaping.bean.Technical;
import com.zua.landscaping.bean.Update;
import com.zua.landscaping.bean.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by roy on 4/24/16.
 */
public interface ConnService {


    @GET("LoginServlet")
    Call<User> getUser();

    @GET("ProjectServlet")
    Call<List<Project>> getAllProjects();

    @Multipart
    @POST("UploadVideoServlet")
    Call<String> upload1(
            @PartMap Map<String, RequestBody> params
    );

    @POST("SceneServlet")
    Call<List<Scene>> getAllScene(
            @Query("pageNum") String pageNum,
            @Query("status") String status
    );

    @POST("SignServlet")
    Call<Code> userSign(
            @QueryMap HashMap<String, String> map
    );

    @POST("SignServlet")
    Call<Sign> getPeople(
            @QueryMap HashMap<String, String> map
    );

    @POST("LeaveSerlvlet")
    Call<Code> applyLeave(
            @QueryMap HashMap<String, String> map
    );

    @POST("LeaveSerlvlet")
    Call<List<Leave>> getLeaveList(
            @QueryMap HashMap<String, String> map
    );

    @POST("UpdateProcessServlet")
    Call<List<Update>> getUpdateData(
            @Query("method") String method
    );

    @POST("UpdateProcessServlet")
    Call<Code> updateProcess(
            @Query("pId") String pId,
            @Query("method") String method
    );

    @POST("DeviceServlet")
    Call<List<Device>> getAllDevices(
            @Query("method") String method
    );

    @POST("TechnicalServlet")
    Call<List<Technical>> getAllTechnical();
}


