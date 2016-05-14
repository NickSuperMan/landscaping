package com.zua.landscaping.utils;


import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Leave;
import com.zua.landscaping.bean.News;
import com.zua.landscaping.bean.Note;
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

    @GET("NewsServlet")
    Call<List<News>> getAllNews();

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
            @Query("method") String method,
            @Query("pageNum") String pageNum,
            @Query("status") String status
    );

    @POST("SceneServlet")
    Call<Code> uploadOpinion(
            @QueryMap HashMap<String, String> map
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

    @POST("NoteServlet")
    Call<List<Note>> getAllNotes(
            @Query("userId") String userId,
            @Query("method") String method
    );

    @POST("NoteServlet")
    Call<Code> addNote(
            @QueryMap HashMap<String, String> map
    );

    @POST("NoteServlet")
    Call<Code> updateNote(
            @QueryMap HashMap<String, String> map
    );

    @POST("NoteServlet")
    Call<Code> deleteNote(
            @Query("noteId") String noteId,
            @Query("method") String method
    );

    @POST("UserServlet")
    Call<Code> updateUser(
            @QueryMap HashMap<String, String> map
    );
}


