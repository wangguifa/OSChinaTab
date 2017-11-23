package com.guifa.navtab;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.guifa.navtab.fragments.BaseFragment;
import com.guifa.navtab.fragments.DynamicTabFragment;
import com.guifa.navtab.fragments.ExploreFragment;
import com.guifa.navtab.fragments.TweetViewPagerFragment;
import com.guifa.navtab.fragments.UserInfoFragment;
import com.guifa.navtab.widget.NavigationButton;

import net.oschina.common.widget.drawable.shape.BorderShape;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author GuiFa
 * @description NavFragment
 * @date 2017/11/23
 */
public class NavFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.nav_item_news)
    NavigationButton mNavNews;
    @Bind(R.id.nav_item_tweet)
    NavigationButton mNavTweet;
    @Bind(R.id.nav_item_explore)
    NavigationButton mNavExplore;
    @Bind(R.id.nav_item_me)
    NavigationButton mNavMe;
    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavigationButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;

    public NavFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new ColorDrawable(getResources().getColor(R.color.white)), lineDrawable});
        root.setBackgroundDrawable(layerDrawable);
        mNavNews.init(R.drawable.tab_icon_new,
                R.string.main_tab_name_news,
                DynamicTabFragment.class);
        mNavTweet.init(R.drawable.tab_icon_tweet,
                R.string.main_tab_name_tweet,
                TweetViewPagerFragment.class);
        mNavExplore.init(R.drawable.tab_icon_explore,
                R.string.main_tab_name_explore,
                ExploreFragment.class);
        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_my,
                UserInfoFragment.class);
    }

    @OnClick({R.id.nav_item_news, R.id.nav_item_tweet, R.id.nav_item_explore, R.id.nav_item_me, R.id.nav_item_tweet_pub})
    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        } else if (v.getId() == R.id.nav_item_tweet_pub) {
            Toast.makeText(getActivity(), "您点击了加号！！！", Toast.LENGTH_SHORT).show();
        }
    }

    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;
        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavNews);
    }

    public void select(int index) {
        if (mNavMe != null) {
            doSelect(mNavMe);
        }
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0) {
            return;
        }
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit) {
            transaction.commitNow();
        }
    }

    private void doSelect(NavigationButton newNavButton) {
        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    private void onReselect(NavigationButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    /**
     * navTab监听
     */
    public interface OnNavigationReselectListener {
        /**
         * 二次点击
         *
         * @param navigationButton navigationButton
         */
        void onReselect(NavigationButton navigationButton);
    }
}