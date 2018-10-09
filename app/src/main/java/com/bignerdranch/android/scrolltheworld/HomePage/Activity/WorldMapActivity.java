package com.bignerdranch.android.scrolltheworld.HomePage.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    @BindView(R.id.mmap)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap();
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
