package com.example.alma.network;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
AsyncTasks are used to perform time-consuming work on a separate thread from the UI thread,
thus preventing the UI getting unresponsive while executing the task.
 */
public abstract class GetDevicesTask extends AsyncTask<Void, Void, List<DeviceBean>> {
    private static final String TAG = "GetDevicesTask";
    private static final String BASE_URL = "http://vm39.cs.lth.se:9000/";

    private Retrofit retrofit;
    private BackendService service;

    public GetDevicesTask() {
        //Gson is a library used for work on JSON formatted data.
        Gson gson = new GsonBuilder().create();

        //Retrofit is a library used for HTTP requests. Its widely used in the Android community
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        /*
        Thanks to the GsonConverterFactory, the responses from the backend (which are formatted
        in JSON) get automatically converted to Java objects without any effort. The Java objects
        have to have same names as the JSON properties for this to work.
         */

        //The service interface is a way to define the HTTP API. For more information about
        //Retrofit, read http://square.github.io/retrofit/
        service = retrofit.create(BackendService.class);
    }

    @Override
    protected void onPreExecute() {
        //This method is called before the main task is started. Good for preparations
        Log.d(TAG, "onPreExecute");
    }

    @Override
    protected List<DeviceBean> doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground");

        Call<List<DeviceBean>> call = service.getDeviceList();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Return an empty list in case the request fails
        return new ArrayList<DeviceBean>();
    }

    @Override
    protected void onPostExecute(List<DeviceBean> results) {
        //This method is called after the main task has completed. Good for cleanup
        Log.d(TAG, "onPostExecute");
        presentResults(results);
    }

    //This method has to be implemented before this class can be instantiated.
    protected abstract void presentResults(List<DeviceBean> results);
}
