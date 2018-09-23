package com.webteklabs.navigationdrawer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    EditText et1,et2;
    StoreData sd ;
    Spinner spinner;
    private static String h="NA",m="0",d="NA";
    static Calendar car;
    Calendar temp;
   static long update_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        et1 = (EditText) findViewById(R.id.edittext1);
        et2 = (EditText) findViewById(R.id.edittext2);
        sd = new StoreData(this);
        spinner = (Spinner) findViewById(R.id.spinner);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("Create Task");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.save:

                boolean isInserted = sd.store(et1.getText().toString(),et2.getText().toString(),h,m,d,spinner.getSelectedItem().toString());

                if(isInserted==true) {

                    Toast.makeText(this, "Data Successfully Added ", Toast.LENGTH_LONG).show();
                    long value = Long.parseLong(m);
                   if (value!=0) {
                       value = value * 1000 * 60;
                        setClock(value);
                       updateClock();
                       h = "NA";
                       m = "0";
                       d = "NA";
                   }
                   else
                   {
                       h = "NA";
                       m = "0";
                       d = "NA";
                   }
                }
                else {
                    Toast.makeText(this, "Error ", Toast.LENGTH_LONG).show();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void reminder(View view) {
        Intent intent = new Intent(this,AlarmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public static void setDateValues(int hour, long min, int day,Calendar cal )
    {


        h = Integer.toString(hour);
        m = Long.toString(min);
        d = Integer.toString(day);
        car = cal;
    }
    public void setClock(long value)
    {    update_value = value;
        if(value!=0) {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, value, pendingIntent);
        }
    }
    public void updateClock()
    {
        try {
            long minvalue = sd.getMinValue();
            minvalue = minvalue*1000*60;
            setClock(minvalue);

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "OK E", Toast.LENGTH_SHORT).show();
        }
    }
}
