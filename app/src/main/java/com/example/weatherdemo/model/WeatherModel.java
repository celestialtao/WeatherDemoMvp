package com.example.weatherdemo.model;

import com.example.weatherdemo.presenter.impl.OnWeatherLitener;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:04
 */
public interface WeatherModel {
    /**
     * 访问环境云API，获取数据
     *
     * @param cityName 要查询城市名称
     * @param litener  得到天气数据后回调接口方法
     */
    void loadWeather(String cityName, OnWeatherLitener litener);

}
