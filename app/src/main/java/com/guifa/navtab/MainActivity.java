package com.guifa.navtab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.guifa.navtab.widget.NavigationButton;

/**
 * @author GuiFa
 * @description 主aty
 * @date 2017/11/23
 */
public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

    public static final String ACTION_NOTICE = "ACTION_NOTICE";
    private NavFragment mNavBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doNewIntent(getIntent(), true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doNewIntent(intent, false);
    }

    private void doNewIntent(Intent intent, boolean isCreate) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        String action = intent.getAction();
        if (action.equals(ACTION_NOTICE)) {
            NavFragment bar = mNavBar;
            if (bar != null) {
                bar.select(3);
            }
        }
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null && fragment instanceof OnTabReselectListener) {
            OnTabReselectListener listener = (OnTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }
}