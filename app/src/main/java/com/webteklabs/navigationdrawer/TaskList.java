package com.webteklabs.navigationdrawer;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskList extends Fragment {

    Cursor cursor;
    ListView listview;
    String sb = new String();
    CursorAdapterClass adapter;
    CheckBox checkBox;

    public TaskList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");
    }

    @Override
    public void onResume() {
        super.onResume();

        listview = (ListView) getView().findViewById(R.id.test);


        try {
            StoreData sd = new StoreData(getActivity());
            cursor = sd.getAllData();

            adapter = new CursorAdapterClass(getActivity(), cursor);

            while (cursor.moveToNext()) {
                sb = sb + cursor.getInt(0) + cursor.getString(1) + cursor.getString(2) + "\n";
            }



            // SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.customlayout,cursor,new String[]{"TITLE"},new int[]{R.id.tv},0);
            listview.setAdapter(adapter);
            Toast.makeText(getActivity(), "All Tasks ", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error: ",ex.toString());
        }

    }
}
