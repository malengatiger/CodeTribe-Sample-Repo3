package com.boha.lifecycle;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;


public class LifeCycleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(LOG, "############ onCreate");
        setContentView(R.layout.activity_life_cycle_demo);

        setFields();

    }
    private void setFields() {
        Log.w(LOG, "############ setFields");
        txt = (TextView)findViewById(R.id.text2);
        txtComms = (TextView)findViewById(R.id.AA_text);
        btn = (Button)findViewById(R.id.AA_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWebSocketMessage();
            }
        });
        final ObjectAnimator an = ObjectAnimator.ofFloat(txt, View.ALPHA, 0);
        //an.setRepeatCount(1);
        an.setDuration(1000);
        an.setRepeatMode(ValueAnimator.REVERSE);
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(LOG, "##### onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(LOG, "##### onAnimationEnd - starting 2nd activity");
                startSecondActivity();
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
                an.start();
            }
        });
    }
    static  final SimpleDateFormat adf = new SimpleDateFormat("HH:mm:ss");
    class RequestDTO {
        int requestType;
        int authorID, companyID;
        public RequestDTO(int requestType, int authorID, int companyID) {
            this.authorID = authorID;
            this.companyID = companyID;
            this.requestType = requestType;
        }
    }
    public void sendWebSocketMessage() {
        RequestDTO r = new RequestDTO(224,1,1);
        mWebSocketClient.send(gson.toJson(r));
        Log.d(LOG, "########### web socket message sent" + gson.toJson(r));
    }
    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.1.111:8050/cm/wsauthor");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.w(LOG, "########## WEBSOCKET Opened: " + serverHandshake.getHttpStatusMessage() + " httpStatus: " + serverHandshake.getHttpStatus());
                //mWebSocketClient.send("Heita from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                Log.i(LOG, "########## onMessage String: " + s);
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtComms.setText(txtComms.getText() + "\n" + message);
                    }
                });
            }
            @Override
            public void onMessage(ByteBuffer bb) {

                File dir = Environment.getExternalStorageDirectory();
                File zip = new File(dir, "data.zip");
                File unZip = new File(dir, "data.json");
                BufferedOutputStream stream = null;
                String content = null;
                try {
                    FileOutputStream fos = new FileOutputStream(zip);
                    stream = new BufferedOutputStream(fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    stream.write(bb.array());
                    stream.flush();
                    stream.close();
                    Log.d(LOG,"###### zip file: " + zip.getAbsolutePath() + " length: " + zip.length());
                    content = ZipUtil.unpack(zip, unZip);
                    Log.d(LOG,"################ unpacked length: " + unZip.length());
                    if (content != null) {
                        Log.w(LOG, "############# onMessage ByteBuffer:\n" + content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final String message = content;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtComms.setText(txtComms.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(final int i, String s, boolean b) {
                Log.e(LOG, "########## WEBSOCKET onClose, int:  " + i + " s: " + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Web Socket Closed ... " + i, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                Log.i(LOG, "$$$$$$$$$$ Error " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
        mWebSocketClient.connect();
    }
    WebSocketClient mWebSocketClient;
    TextView txt, txtComms, btn;
    @Override
    public void onResume() {
        Log.w(LOG, "############ onResume");
        txt.setAlpha(1f);
        txtComms.setText("");
        connectWebSocket();
        super.onResume();
    }
    @Override
    public void onStart() {
        Log.i(LOG, "############ onStart");
        super.onStart();
    }
    @Override
    public void onPause() {
        Log.w(LOG, "############ onPause, closing web socket client ...");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        mWebSocketClient.close();
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
    public void onBackPressed() {
        Log.w(LOG, "############ onBackPressed");
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w(LOG, "############ onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_navigate) {
            startSecondActivity();
            return true;
        }
        if (id == R.id.action_close) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static final String LOG = LifeCycleDemoActivity.class.getName();
    static final Gson gson = new Gson();
    TextView txtNumber;
    private void startSecondActivity() {
        Intent i = new Intent(this, MyActivity.class);
        startActivity(i);
    }
}
