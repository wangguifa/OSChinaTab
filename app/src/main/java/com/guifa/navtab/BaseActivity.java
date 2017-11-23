package com.guifa.navtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author GuiFa
 * @description 基类aty
 * @date 2017/11/23
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initWidget();
    }

    /**
     * 获取布局
     *
     * @return 布局
     */
    protected abstract int getContentView();

    protected void initWidget() {
    }
}