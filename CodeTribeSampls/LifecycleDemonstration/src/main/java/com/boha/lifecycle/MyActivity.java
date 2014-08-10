package com.boha.lifecycle;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "############ onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setFields();
    }

    private void setFields() {
        Log.w(LOG, "############ setFields");
        final TextView txt = (TextView)findViewById(R.id.text2);
        final ObjectAnimator an = ObjectAnimator.ofFloat(txt, View.SCALE_X, 0);
       // an.setRepeatCount(1);
        an.setDuration(1000);
       // an.setRepeatMode(ValueAnimator.REVERSE);
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(LOG, "##### onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(LOG, "##### onAnimationEnd - closing 2nd activity");
                onBackPressed();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setTextColor(getResources().getColor(R.color.blue));
                an.start();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.w(LOG, "############ onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w(LOG, "############ onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_back) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        Log.w(LOG, "############ onResume");
        super.onResume();
    }
    @Override
    public void onStart() {
        Log.i(LOG, "############ onStart");
        super.onStart();
    }
    @Override
    public void onPause() {
        Log.w(LOG, "############ onPause");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
    public void onBackPressed() {
        Log.w(LOG, "############ onBackPressed");
        super.onBackPressed();
    }

    static final String LOG = MyActivity.class.getName();
}
