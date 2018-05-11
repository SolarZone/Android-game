package com.example.wzbc.znote;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wzbc.constants.State;
import com.example.wzbc.fragment.MyNoteFragment;
import com.example.wzbc.fragment.OnNoteClickListener;


public class MainActivity extends AppCompatActivity implements OnNoteClickListener{

    private boolean mDualPane;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        //判断横竖屏,true为横
        View mNoteFrame=findViewById(R.id.note_deatil);
        mDualPane=mNoteFrame!=null&&mNoteFrame.getVisibility()==View.VISIBLE;
        if(mDualPane){
            showNote(0, State.NEW_STATE);
        }
    }

    private void showNote(long id,int state){
        MyNoteFragment myNoteFragment=(MyNoteFragment) getSupportFragmentManager().findFragmentById(R.id.note_deatil);
        if(myNoteFragment==null||myNoteFragment.getCurrentItemId()!=id){
            myNoteFragment =MyNoteFragment.NewInstance(id,state);
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.note_deatil,myNoteFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack("aa");
            ft.commit();
        }


    }

    //实现接口方法
    public void onItemClick(long id,int state){

        if(mDualPane){
            showNote(id,state);
        }else{
            //存放接收的id与state
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,MyNoteActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("state",state);
            startActivity(intent);
        }

    }
}
