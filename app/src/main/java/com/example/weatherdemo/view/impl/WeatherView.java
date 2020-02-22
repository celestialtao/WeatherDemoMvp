package com.example.weatherdemo.view.impl;

import com.example.weatherdemo.model.entity.Weather;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:05
 */
public interface WeatherView {
    /**
     * 显示进度条
     */
    void showLoading();

    /**
     *隐藏进度条
     */
    void dismissLoading();

    /**
     * 显示更新失败
     */
    void showError();

    /**
     * 更新UI
     * @param weather
     */
    void setWeatherInfo(Weather weather);
}
