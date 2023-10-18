package com.example.whha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.whha.utils.TokenTools;
import com.tencent.mmkv.MMKV;

public class app extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMKV.initialize(this);
        TokenTools tokentools = new TokenTools();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        });
        thread.start();
    }

    public void checkLogin(){
        Thread isLogin = new Thread(new Runnable() {
            @Override
            public void run() {
//                isLoginNow = TokenTools.ExistToken();
                if(!TokenTools.ExistToken()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(app.this, "还未登录", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(app.this, Login.class));

                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(app.this, "已经登录", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(app.this, Login.class));
                        }
                    });

                }
            }
        });
        isLogin.start();
    }
}
