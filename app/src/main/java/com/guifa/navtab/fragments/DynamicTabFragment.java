package com.guifa.navtab.fragments;

import android.widget.Toast;

import com.guifa.navtab.OnTabReselectListener;
import com.guifa.navtab.R;

/**
 * @author GuiFa
 * @description 综合页面
 * @date 2017/11/23
 */
public class DynamicTabFragment extends BaseFragment implements OnTabReselectListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_tab;
    }

    @Override
    public void onTabReselect() {
        Toast.makeText(getActivity(), "再次点击", Toast.LENGTH_SHORT).show();
    }
}
