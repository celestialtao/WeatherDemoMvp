package com.example.weatherdemo.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.weatherdemo.model.WeatherModel;
import com.example.weatherdemo.model.WeatherModelImpl;
import com.example.weatherdemo.model.entity.Weather;


/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:51
 */
public class WeatherPresenterImpl implements WeatherPresenter, OnWeatherLitener {
    private Handler handler;

    private WeatherModel model;//model的引用
    private OnWeatherLitener mLitener ;


    public WeatherPresenterImpl(Handler handler, Context context) {
        this.handler = handler;
        model = new WeatherModelImpl(context);
        mLitener = this;

    }

    /**
     * 向model发出查询天气信息
     * @param cityName  要查询的城市名称
     */
    @Override
    public void getWeather(final String cityName) {

        model.loadWeather(cityName, mLitener);

    }

    @Override
    public void onSuccess(Weather weather) {
        Message msg = handler.obtainMessage();
        msg.obj = weather;
        msg.what = 0x123;
        handler.sendMessage(msg);
    }




    @Override
    public void onFiled() {
        handler.sendEmptyMessage(0x000);
    }

    }


