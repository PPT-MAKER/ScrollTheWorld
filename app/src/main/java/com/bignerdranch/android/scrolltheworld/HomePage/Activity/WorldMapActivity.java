package com.bignerdranch.android.scrolltheworld.HomePage.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.bignerdranch.android.scrolltheworld.R;
import com.bignerdranch.android.scrolltheworld.common.activity.BaseActivity;

import butterknife.BindView;

public class WorldMapActivity extends BaseActivity {

    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    private BDAbstractLocationListener myListener;

    @BindView(R.id.mmap)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap();
        myListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double latitude = bdLocation.getLatitude();    //获取纬度信息
                double longitude = bdLocation.getLongitude();    //获取经度信息
                float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f

                String coorType = bdLocation.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                int errorCode = bdLocation.getLocType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            }
        };
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_world_map;
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
    }
}
