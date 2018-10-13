package com.bignerdranch.android.scrolltheworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.scrolltheworld.HomePage.Activity.WorldMapActivity;
import com.bignerdranch.android.scrolltheworld.common.Utils.BanClickUtil;
import com.bignerdranch.android.scrolltheworld.common.Utils.CheckNetError;
import com.bignerdranch.android.scrolltheworld.common.Utils.SingleClick;
import com.bignerdranch.android.scrolltheworld.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.jump)
    Button mJump;

    @BindView(R.id.jump2)
    Button mJump2;

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
        Integer k = 2;
        mJump.setTag(BanClickUtil.TAG_KEY, k);
        mJump2.setTag(BanClickUtil.TAG_KEY, k);
    }

    @SingleClick(order = BanClickUtil.Order.FIRST, filterType = BanClickUtil.FILTER_BY_TAG)
    @OnClick({R.id.jump, R.id.jump2})
    public void jumptomap(View view) {
        text.setText("点击生效");
        Log.i("点击生效", "我被执行了");
    }
}
