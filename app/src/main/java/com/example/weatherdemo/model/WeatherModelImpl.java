package com.example.weatherdemo.model;

import android.content.Context;
import android.text.style.SuperscriptSpan;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherdemo.model.entity.Weather;
import com.example.weatherdemo.presenter.impl.OnWeatherLitener;
import com.example.weatherdemo.util.CityAndCodeUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/18
 * Time: 23:21
 */
public class WeatherModelImpl implements WeatherModel {
    private static final String TAG = "WeatherModelImpl";

    //根据城市名称查询的API地址
    private static final String URL = "http://service.envicloud.cn:8082/v2/weatherlive/";
    //环境云分配的请求ID
    private static final String ACCESSID = "Y2VSZXN0AWFSDGFVMTU4MTK1MTMZMJC0OQ==";

    private OkHttpClient mOkHttpClient;
    private Gson gson = new Gson();


    public WeatherModelImpl(Context context) {

    }

    @Override
    public void loadWeather(String cityName, OnWeatherLitener litener) {
        String cityCode = CityAndCodeUtil.getCityCode(cityName);
        Log.d(TAG, cityCode);
        doGet(cityCode, litener);
    }

    private void doGet(String cityCode, final OnWeatherLitener litener) {
        String url = URL  + ACCESSID + "/" + cityCode ;
        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                System.out.println(s);
                //将json字符串转换为weather对象
                Weather weather = gson.fromJson(s, Weather.class);
                //如果weather的resultCode==0证明查询数据成功
                if ("200".equals(weather.getRcode())) {
                    //调用listener的onSuccess（）方法，通知数据查询成功，更新数据。
                    litener.onSuccess(weather);
                } else {
                    //数据查询失败
                    litener.onFiled();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //数据查询失败
                System.out.println("ERROR" + volleyError.getMessage());
                litener.onFiled();
            }
        });

        //执行post请求
        requestQueue.add(stringRequest);*/

        mOkHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();


            Call task = mOkHttpClient.newCall(request);

            task.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "onFailure --->" + e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    int code = response.code();
                    Log.d(TAG, "Code --->" + code);
                    if (code == HttpURLConnection.HTTP_OK) {
                        ResponseBody responseBody = response.body();
                        Weather weather = (Weather) gson.fromJson(responseBody.string(), Weather.class);

                        if (200 == weather.getRcode()) {
                            //调用listener的onSuccess（）方法，通知数据查询成功，更新数据。
                            litener.onSuccess(weather);
                        } else {
                            //数据查询失败
                            litener.onFiled();
                        }
                    }
                }
            });

    }
}
