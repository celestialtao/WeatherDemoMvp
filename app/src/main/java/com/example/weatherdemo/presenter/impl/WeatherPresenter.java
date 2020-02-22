package com.example.weatherdemo.presenter.impl;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:01
 */
public interface WeatherPresenter {
    /**
     * 前台调用该方法去间接调用Model的loadWeather()方法
     * @param cityName  要查询的城市名称
     */
    void getWeather(String cityName);
}

