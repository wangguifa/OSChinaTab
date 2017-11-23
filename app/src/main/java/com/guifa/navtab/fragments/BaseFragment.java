package com.guifa.navtab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author GuiFa
 * @description 基类Fragment
 * @date 2017/11/23
 */
public abstract class BaseFragment extends Fragment {
    protected View mRoot;
    protected LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            ButterKnife.bind(this, mRoot);
            initWidget(mRoot);
        }
        return mRoot;
    }

    /**
     * 获取布局
     *
     * @return 布局
     */
    protected abstract int getLayoutId();

    protected void initWidget(View root) {
    }
}