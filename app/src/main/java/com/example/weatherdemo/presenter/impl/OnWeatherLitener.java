package com.example.weatherdemo.presenter.impl;

import com.example.weatherdemo.model.entity.Weather;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:02
 */
public interface OnWeatherLitener {
    /**
     * 成功时回调该方法
     * @param weather
     */
    void onSuccess(Weather weather);

    /**
     * 失败时调用
     */
    void onFiled();
}
