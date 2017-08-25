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

public abstract class GetDevicesTask extends AsyncTask<Void, Void, List<DeviceBean>> {
    private static final String TAG = "GetDevicesTask";
    private static final String BASE_URL = "http://vm39.cs.lth.se:9000/";

    private Retrofit retrofit;
    private BackendService service;

    public GetDevicesTask() {
        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

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
        return new ArrayList<DeviceBean>();
    }

    @Override
    protected void onPostExecute(List<DeviceBean> results) {
        //This method is called after the main task has completed. Good for cleanup
        Log.d(TAG, "onPostExecute");
        presentResults(results);
    }

    protected abstract void presentResults(List<DeviceBean> results);
}
