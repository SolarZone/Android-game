package com.example.wzbc.znote;

import android.support.v4.app.Fragment;

import com.example.wzbc.fragment.MyNoteFragment;

/**
 * Created by WZBC on 2018/4/11.
 */

public class MyNoteActivity extends SingleFragmentActivity{
    @Override
    protected  Fragment getFragment() {
        //读取保存的id与state,默认情况为0
        long id=getIntent().getLongExtra("id",0);
        int state=getIntent().getIntExtra("state",0);
        return MyNoteFragment.NewInstance(id,state);
    }
}
