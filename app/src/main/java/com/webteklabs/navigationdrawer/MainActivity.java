package com.webteklabs.navigationdrawer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Cursor cursor;
    ListView listview;
    String sb = new String();
    CursorAdapterClass adapter;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        selectItem(R.id.all_tasks);
        //listview = (ListView) findViewById(R.id.test);

      /* try {
            StoreData sd = new StoreData(this);
            cursor = sd.getAllData();
            Toast.makeText(this, "Resume Method", Toast.LENGTH_LONG).show();

            adapter = new CursorAdapterClass(this, cursor);

            while (cursor.moveToNext()) {
                sb = sb + cursor.getInt(0) + cursor.getString(1) + cursor.getString(2) + "\n";
            }



            // SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.customlayout,cursor,new String[]{"TITLE"},new int[]{R.id.tv},0);
            listview.setAdapter(adapter);
            Toast.makeText(this, "Using Cursor Adapter "+sb, Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error: ",ex.toString());
        }
        */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectItem(item.getItemId());
        //make this method blank
        return true;

    }

    private void selectItem(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.all_tasks:
                fragment = new TaskList();
                break;
            case R.id.completed_tasks:
                fragment = new CompletedTasks();
                break;
            case R.id.not_completed_tasks:
                fragment = new UnCompletedTasks();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    public void newEvent(View view) {

        Intent intent = new Intent(this,CreateTask.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

      /*  try {
            StoreData sd = new StoreData(this);
            cursor = sd.getAllData();
            Toast.makeText(this, "Resume Method", Toast.LENGTH_LONG).show();

            adapter = new CursorAdapterClass(this, cursor);

            while (cursor.moveToNext()) {
               sb = sb + cursor.getInt(0) + cursor.getString(1) + cursor.getString(2) + "\n";
            }



            // SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.customlayout,cursor,new String[]{"TITLE"},new int[]{R.id.tv},0);
            listview.setAdapter(adapter);
            Toast.makeText(this, "Using Cursor Adapter "+sb, Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error: ",ex.toString());
        }
*/

    }


    public void arrowbutton(View view) {
        View parent = (View) view.getParent();
        TextView tv = (TextView) parent.findViewById(R.id.tv);
        String str = tv.getText().toString();
        Intent intent = new Intent(this,displayContent.class);
        intent.putExtra("Title",str);
        startActivity(intent);


    }

}
