package com.bignerdranch.android.scrolltheworld;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.scrolltheworld.HomePage.Activity.WorldMapActivity;
import com.bignerdranch.android.scrolltheworld.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.jump)
    Button mJump;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.jump)
    public void jumptomap(View view) {
        startActivity(new Intent(this, WorldMapActivity.class));
    }
}
