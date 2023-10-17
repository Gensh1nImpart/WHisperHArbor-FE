package com.example.whha;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.whha.utils.TokenTools;

public class Login extends AppCompatActivity {
    EditText username;
    EditText passwd;
    Button toLogin;
    Button toRegister;
    TextView usernameWarning;
    TextView passwdWarning;

    final static String TAG = "Login";
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        onInitUsername();
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameWarning.setText("");
                passwdWarning.setText("");
                String tpUsername = username.getText().toString();
                String tpPasswd = passwd.getText().toString();
                if(tpUsername.isEmpty() || tpPasswd.isEmpty()){
                    if(tpUsername.isEmpty())
                        usernameWarning.setText("用户名不能为空");
                    if(tpPasswd.isEmpty())
                        passwdWarning.setText("密码不能为空");
                    return;
                }
                Thread loginThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            boolean loginSuccess = TokenTools.Login(tpUsername, tpPasswd);Log.i(TAG, "run: " + loginSuccess);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(loginSuccess){
                                        Log.i(TAG, "run: successful");
                                        Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Login.this, "登录失败!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }catch (Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this, "登录失败!" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                loginThread.start();
            }
        });
    }
    private void init(){
        username = findViewById(R.id.username_edit);
        passwd = findViewById(R.id.password_edit);
        toLogin = findViewById(R.id.login_signin_button);
        toRegister = findViewById(R.id.login_register_button);
        usernameWarning = findViewById(R.id.username_edit_warning);
        passwdWarning = findViewById(R.id.passwd_edit_warning);
    }
    private void onInitUsername(){
        if(TokenTools.mmkv.containsKey("username_cache")){
            username.setText(TokenTools.mmkv.getString("username_cache", ""));
        }
    }
}
