package com.example.whha;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.whha.utils.TokenTools;
import com.tencent.mmkv.MMKV;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    TokenTools tokentools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MMKV.initialize(this);
        tokentools = new TokenTools();
        Thread isLogin = new Thread(new Runnable() {
            @Override
            public void run() {
//                isLoginNow = TokenTools.ExistToken();
                if(!TokenTools.ExistToken()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "还未登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "已经登录", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        isLogin.start();


    }
}