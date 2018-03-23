package com.lei.baidumap;

import android.content.ContentValues;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by yanle on 2018/3/22.
 */

public class MyOrientationListener implements SensorEventListener {
    //拿到传感器的管理者
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;

    private float lastX;


    public MyOrientationListener(Context context) {
        this.mContext = context;
    }

    public void setSensorManager(SensorManager mSensorManager) {
        this.mSensorManager = mSensorManager;
    }

    /**
     * 开始监听
     */
    public void start() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            //拿到方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor != null) {
            //得到精度
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * 结束监听
     */
    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override//方向发生变化
    public void onSensorChanged(SensorEvent event) {
        //返回的时方向传感器
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                if(mOnOrientationListener != null) {
                    mOnOrientationListener.onOrientationChnaged(x);
                }
            }
            lastX = x;
        }
    }

    @Override//精度发生改变时
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private OnOrientationListener mOnOrientationListener;

    public void setOnOrientationListener(OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public interface OnOrientationListener {
        void onOrientationChnaged(float x);
    }
}
