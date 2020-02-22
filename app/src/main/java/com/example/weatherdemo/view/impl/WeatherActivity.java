package com.example.weatherdemo.view.impl;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.example.weatherdemo.R;
import com.example.weatherdemo.adapter.ListItemAdapter;
import com.example.weatherdemo.model.entity.Weather;
import com.example.weatherdemo.presenter.impl.WeatherPresenter;
import com.example.weatherdemo.presenter.impl.WeatherPresenterImpl;

import java.lang.ref.WeakReference;


public class WeatherActivity extends AppCompatActivity implements WeatherView, View.OnClickListener {

    private WeatherPresenter weatherPresenter;
    //声明UI组件
    private AutoCompleteTextView atv_city_name;
    private Button btn_submit;
    private ProgressBar progressBar;
    private ListView listView;

    private UIHandler handler = new UIHandler(this);  //在Presenter更新UI的handler
    private ListItemAdapter listAdapter;//ListView的适配器，用于填充天气信息
    private String[] values = new String[9];//临时存储listView中要设置的天气信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //绑定View
        bindView();
        //初始化WeatherPresenter控制器
        weatherPresenter = new WeatherPresenterImpl(handler, this);

    }

    /**
     * 绑定UI组件的方法
     */
    private void bindView() {
        atv_city_name = (AutoCompleteTextView) findViewById(R.id.atv_city_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //初始化为不可见
        progressBar.setVisibility(View.INVISIBLE);
        //设置按钮监听
        btn_submit.setOnClickListener(this);
        //设置自动填充文本框内容
        String[] citys = getResources().getStringArray(R.array.city_names);
        ArrayAdapter cityNamesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, citys);
        atv_city_name.setAdapter(cityNamesAdapter);
        //初始化listView
        listAdapter = new ListItemAdapter(values, this);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btn_submit.setEnabled(false);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        btn_submit.setEnabled(true);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "天气查询失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setWeatherInfo(Weather weather) {

        //{"地点", "发布日期", "当前温度","天气", "舒适度", "湿度", "风力","风速", "雨量"}
        values[0] = atv_city_name.getText().toString();
        values[1] = weather.getUpdatetime();
        values[2] = weather.getTemperature() + "℃";
        values[3] = weather.getPhenomena();
        values[4] = weather.getFeelst();
        values[5] = weather.getHumidity();
        values[6] = weather.getWindpower();
        values[7] = weather.getWindspeed();
        values[8] = weather.getRain();

        //通知数据更新
        listAdapter.notifyDataSetChanged();
    }

    /**
     * 按钮点击事件
     * 当按钮被点击时，执行以下操作
     * ①检查城市名是否为null
     * ②显示数据加载进度条，设置查询按钮不可用
     * ③调用WeatherPresenter的getWeather()方法去查询天气
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(atv_city_name.getText())) {
            String cityName = atv_city_name.getText().toString();
            showLoading();
            weatherPresenter.getWeather(cityName);
        } else {
            Toast.makeText(this, "城市名称不能为空", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 自定义Handler，这样写的目的是为了解决防止内存溢出。
     * 如果不理解也不用在意这些细节，因为这不是本文的重点（其实小编也不是很理解，只知道是用了一个弱引用，哈哈。。。）
     * 主要看里面的public void handleMessage(Message msg)方法
     */
    private static class UIHandler extends Handler {
        private WeakReference<WeatherActivity> reference = null;

        public UIHandler(WeatherActivity weatherActivity) {
            reference = new WeakReference<WeatherActivity>(weatherActivity);

        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            WeatherActivity weatherActivity = reference.get();
            weatherActivity.dismissLoading();
            Weather weather;
            switch (what) {
                //0x123:代表数据更新成功，进行刷新数据操作
                case 0x123:
                    weather = (Weather) msg.obj;
                    weatherActivity.setWeatherInfo(weather);
                    break;
                //0x123：数据更新失败，提升用户
                case 0x000:
                    weatherActivity.showError();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
