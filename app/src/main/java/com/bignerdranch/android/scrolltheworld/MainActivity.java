package com.bignerdranch.android.scrolltheworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.scrolltheworld.HomePage.Activity.WorldMapActivity;
import com.bignerdranch.android.scrolltheworld.common.Utils.CheckNetError;
import com.bignerdranch.android.scrolltheworld.common.Utils.SingleClick;
import com.bignerdranch.android.scrolltheworld.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.jump)
    Button mJump;

    @BindView(R.id.count)
    TextView text;

    int k = 0;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @CheckNetError
    @SingleClick
    @OnClick(R.id.jump)
    public void jumptomap(View view) {
//        startActivity(new Intent(this, WorldMapActivity.class));
        text.setText("" + k);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        k++;
        return super.dispatchTouchEvent(ev);
    }
}
