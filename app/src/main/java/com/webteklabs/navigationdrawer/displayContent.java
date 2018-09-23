package com.webteklabs.navigationdrawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class displayContent extends AppCompatActivity {

    String title,oldtitle;
    StringBuilder description = new StringBuilder();
    StringBuilder type = new StringBuilder();
    TextView tv1,tv2;
    EditText et1,et2,showtype;
    SQLiteDatabase db;
    Cursor cursor;
    int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);
        title = getIntent().getExtras().getString("Title");
        oldtitle = title;
        //tv1 = (TextView) findViewById(R.id.titletv);
        et1 = (EditText) findViewById(R.id.titleet);
        showtype = (EditText) findViewById(R.id.showtype);
        et1.setText("Title:  "+title);
        et1.setEnabled(false);
        StoreData sd = new StoreData(this);
        cursor = sd.getData(title);
        while (cursor.moveToNext()) {
            description.append( cursor.getString( cursor.getColumnIndex("TASK_TO.DESCRIPTION")));
            description.append("\n");
            type.append( cursor.getString( cursor.getColumnIndex("TASK_TO.TYP")));
        }
        //Toast.makeText(this, description.toString(), Toast.LENGTH_SHORT).show();
        //TextView tv2 = (TextView) findViewById(R.id.descriptiontv);

        et2 = (EditText) findViewById(R.id.descriptionet);
        et2.setText("Description:  "+description);
        et2.setEnabled(false);
        showtype.setText("Type:  "+type);
        showtype.setEnabled(false);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle("Task");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.edit:
                count=2;
                et1.setEnabled(true);
                et2.setEnabled(true);
                return true;
            case R.id.delete:
                if(count==1 || count==2)
                {
                    StoreData sd = new StoreData(this);
                    boolean isInserted = sd.delete(title);
                    if(isInserted) {
                        Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    count=1;
                    et1.setEnabled(false);
                    et2.setEnabled(false);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }

                return true;
            case  R.id.cancle:
                et1.setText(title);
                et2.setText(description);
                et1.setEnabled(false);
                et2.setEnabled(false);
                return true;
            case R.id.save:
                if(count==2)
                {
                    StoreData sd = new StoreData(this);
                    boolean isInserted = sd.update(et1.getText().toString(),et2.getText().toString(),oldtitle);
                    if(isInserted) {
                        Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    count=1;
                    et1.setEnabled(false);
                    et2.setEnabled(false);
                }
                else{
                    Toast.makeText(this, "It is already saved", Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }



}
