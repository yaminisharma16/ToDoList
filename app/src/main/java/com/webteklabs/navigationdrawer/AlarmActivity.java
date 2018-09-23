package com.webteklabs.navigationdrawer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    DatePicker pickerDate;
    TimePicker pickerTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

    }

    public Calendar getCalendarTime()
    {

        Calendar current = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(pickerDate.getYear(),
                pickerDate.getMonth(),
                pickerDate.getDayOfMonth(),
                pickerTime.getCurrentHour(),
                pickerTime.getCurrentMinute(),
                00);

        if(cal.compareTo(current) <= 0){
            //The set Date/Time already passed
            Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
        }else{
            return cal;
        }
        return null;
    }

    public void set_alarm(View view) {
        Calendar car = getCalendarTime();
        if(car!=null) {
            int hour = car.get(Calendar.HOUR_OF_DAY);
            long min = car.getTimeInMillis();
            min=min/(1000*60);
            int day = car.get(Calendar.DAY_OF_MONTH);

            Toast.makeText(this,"Alarm set ", Toast.LENGTH_SHORT).show();

       /* if(car!=null) {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, car.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm set in " + car.getTime() + " seconds", Toast.LENGTH_LONG).show();
            CreateTask.setDateValues(hour,min,day,car);

        }*/

            CreateTask.setDateValues(hour, min, day, car);
        }

    }

}
