package com.boha.mymodule.app2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class LifeCycleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(LOG, "############ onCreate");
        setContentView(R.layout.activity_life_cycle_demo);
    }
    @Override
    public void onResume() {
        Log.w(LOG, "############ onResume");
        super.onResume();
    }
    @Override
    public void onStart() {
        Log.e(LOG, "############ onStart");
        super.onStart();
    }
    @Override
    public void onPause() {
        Log.w(LOG, "############ onPause");
        super.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle data) {
        Log.w(LOG, "############ onSaveInstanceState ");
        super.onSaveInstanceState(data);
    }
    @Override
    public void onStop() {
        Log.w(LOG, "############ onStop");
        super.onStop();
    }
    @Override
    public void onDestroy() {
        Log.d(LOG, "############ onDestroy");
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.e(LOG, "############ onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.life_cycle_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w(LOG, "############ onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static final String LOG = LifeCycleDemoActivity.class.getName();
}
