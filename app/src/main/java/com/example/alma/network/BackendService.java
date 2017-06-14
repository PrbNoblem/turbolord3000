package com.example.alma.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendService {
    @GET("device")
    Call<List<DeviceBean>> getDeviceList();
}
