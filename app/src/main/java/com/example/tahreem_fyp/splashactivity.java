package com.example.tahreem_fyp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class splashactivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    SessionManager manager;
    ProgressBar pb;

    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splashactivity);
//        getSupportActionBar().hide();
        pb = (ProgressBar) findViewById(R.id.proog);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.tahreem_fyp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", "printHashKey()", e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", "printHashKey()", e);
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Intent t = new Intent(splashactivity.this, MainActivity.class);
                        Thread timer = new Thread() {
                            public void run() {
                                try {
                                    sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    startActivity(t);
                                    finish();
                                }

                            }
                        };
                        timer.start();
                    }
                });
            }
        }).start();
        //your work---------------------------------------------------------
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                Intent MainIntent = new Intent(splashactivity.this, MainActivity.class);
//                startActivity(MainIntent);
//                finish();
//
//            }
//        }, SPLASH_TIME_OUT);
//    }
        //your work end **-----------------------------------------------------------

//    private void printHashKey(Context pContext) {
//        try {
//            PackageInfo info = pContext.getPackageManager().getPackageInfo(
//                    pContext.packageName,
//                    PackageManager.GET_SIGNATURES
//            );
//            for (signature : info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.encode(md.digest(), 0))
//                Log.i("HASH_KEY", "printHashKey() Hash Key: $hashKey")
//            }
//        } catch (e: NoSuchAlgorithmException) {
//            Log.e("HASH_KEY", "printHashKey()", e)
//        } catch (e: Exception) {
//            Log.e("HASH_KEY", "printHashKey()", e)
//        }
    }
}
