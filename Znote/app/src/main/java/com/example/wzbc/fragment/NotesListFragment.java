package com.example.wzbc.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wzbc.constants.State;
import com.example.wzbc.loader.NotesLoader;
import com.example.wzbc.sqlite.D;
import com.example.wzbc.sqlite.NotesDao;
import com.example.wzbc.znote.R;


/**
 * Created by WZBC on 2018/4/9.
 */

//list列表,实现了loader接口
public class NotesListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private OnNoteClickListener listener;
    private SimpleCursorAdapter simpleCursorAdapter;
    private View mView;
    private long mid;

    //给listener赋值为现在的activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(OnNoteClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            +"must implement NoteListClickInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.list,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getDate.getMyData(),R.layout.note_list_item,
//                new String[]{"title","info","date"},
//                new int[]{R.id.title,R.id.info,R.id.date});
//        setListAdapter(adapter);
        //将data带入list
        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.note_list_item, null
                , new String[]{D.Notes.FIELD_NOTE,D.Notes.FIELD_NOTEETAIL}
                , new int[]{R.id.title, R.id.info}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(simpleCursorAdapter);
        //创建id为22的loader
        LoaderManager lm = getLoaderManager();
        lm.initLoader(22, null, this);
        mView.findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //因为listener现在代表activity，所以点击后将实现Myactivity中的onItemClick方法
                listener.onItemClick(0, State.NEW_STATE);
            }
        });
        //长按删除
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mid=id;
                new AlertDialog.Builder(getActivity())
                        .setTitle("是否删除？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NotesDao dao=new NotesDao(getActivity());
                                dao.open();
                                dao.delectNote(mid);
                                //删除后重启loader
                                getLoaderManager().restartLoader(22,null,NotesListFragment.this);
                            }
                        })
                        .create().show();

                return true;
            }
        });


    }

    //点击对应的listview实现Myactivity中的onItemClick方法
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.onItemClick(id,State.EDIT_STATE);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return  new NotesLoader(getActivity());
    }

    @Override
    public void onLoaderReset(Loader loader) {
        simpleCursorAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }
}


