package com.webteklabs.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shubhamgupta on 4/7/17.
 */

public class CursorAdapterClass extends CursorAdapter {

    LayoutInflater cursorInflater;
    CheckBox checkBox;
    StoreData sd ;

    public CursorAdapterClass(Context context, Cursor c) {
        super(context, c, 0);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        sd = new StoreData(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.customlayout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView textview  = (TextView) view.findViewById(R.id.tv);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView tv_date = (TextView) view.findViewById(R.id.tvdate);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

        String title,date_day,spinner_type,checked;
        title = cursor.getString( cursor.getColumnIndex("TASK_TODO.TITLE"));

        date_day = cursor.getString( cursor.getColumnIndex("TASK_TODO.DAY"));
        spinner_type = cursor.getString( cursor.getColumnIndex("TASK_TODO.TYP"));
        checked = cursor.getString( cursor.getColumnIndex("TASK_TODO.COMPLETE"));
        if(checked.equals("1"))
        {
         checkBox.setChecked(true);
        }
        else
        {
            checkBox.setChecked(false);
        }

        textview.setText(title);
        type.setText(spinner_type);
        tv_date.setText("Day | "+date_day);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View parent = (View) v.getParent();
                TextView tv = (TextView) parent.findViewById(R.id.tv);
                String str = tv.getText().toString();

                if (checkBox.isChecked()) {

                  boolean isInserted =  sd.updateCheckBox(str,"1");
                    if(isInserted)
                    {
                        Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                } else if (!checkBox.isChecked()) {
                    Toast.makeText(context, "Not Completed", Toast.LENGTH_SHORT).show();
                    sd.updateCheckBox(str,"0");
                }
            }
        });


    }
}
