package com.example.wzbc.znote;

import android.support.v4.app.Fragment;

import com.example.wzbc.fragment.WelcomeFragment;

/**
 * Created by WZBC on 2018/4/9.
 */

public class WelcomeActivity extends SingleFragmentActivity{
    @Override
    protected Fragment getFragment() {
        return new WelcomeFragment();
    }
}
