package com.example.wzbc.fragment;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wzbc.constants.State;
import com.example.wzbc.sqlite.D;
import com.example.wzbc.sqlite.NotesDao;
import com.example.wzbc.znote.R;
import com.example.wzbc.db.*;

import java.util.List;
import java.util.Map;

/**
 * Created by WZBC on 2018/4/11.
 */

public class MyNoteFragment extends Fragment{
    private View view;
    private long id;
    private int state;
    private EditText editName,editContext;
    private Button saveButton,cancelButton;
    private NotesDao dao;


    public static MyNoteFragment NewInstance(long id,int state){
        //将id与state存放到bundle中
        MyNoteFragment fragment=new MyNoteFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("state",state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //创建后读取id与state
        super.onCreate(savedInstanceState);
        id=getArguments().getLong("id");
        state=getArguments().getInt("state");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.note,container,false);
        editName=(EditText) view.findViewById(R.id.note_name);
        editContext=(EditText) view.findViewById(R.id.note_deatil);
        saveButton=(Button)view.findViewById(R.id.ok);
        cancelButton=(Button)view.findViewById(R.id.canel);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        List<Map<String,Object>> list=getDate.getMyData();

        if(state== State.EDIT_STATE||state==State.READ_STATE){

            NotesDao notesDao=new NotesDao(getActivity());
            //以读取到的id为条件获取数据库中的一条符合条件的数据存入cursor,并将数据放入文本中
            Cursor cursor= notesDao.get(id);
            if (cursor.moveToFirst()){
                editName.setText(cursor.getString(cursor.getColumnIndex(D.Notes.FIELD_NOTE)));
                editContext.setText(cursor.getString(cursor.getColumnIndex(D.Notes.FIELD_NOTEETAIL)));
            }
            notesDao.close();
        }
        //点击ok后对数据库进行相应的操作
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao=new NotesDao(getActivity());
                if(state==State.NEW_STATE){
                    dao.insertNote(editName.getText().toString(),editContext.getText().toString());
                    dao.close();
                }
                if(state==State.EDIT_STATE){
                    dao.updateNote(id,editName.getText().toString(),editContext.getText().toString());
                    dao.close();
                }
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    getActivity().finish();
                } else {

                }

            }
        });
    }

    public  long getCurrentItemId(){
        return id;
    }


}
