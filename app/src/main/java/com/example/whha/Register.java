package com.example.whha;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.whha.utils.TokenTools;

public class Register extends AppCompatActivity {
    EditText username;
    EditText passwd;
    EditText passwd2;
    EditText nickname;
    Button register;
    Button reset;
    TextView warning_username;
    TextView warning_passwd;
    TextView warning_passwd2;
    TextView warning_nickname;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        init();
        reset.setOnClickListener(v -> {
            username.setText("");
            passwd.setText("");
            passwd2.setText("");
            nickname.setText("");
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning_nickname.setText("");
                warning_passwd2.setText("");
                warning_passwd.setText("");
                warning_username.setText("");
                String tpUsername = username.getText().toString();
                String tpPasswd = passwd.getText().toString();
                String tpPasswd2 = passwd2.getText().toString();
                String tpNickname = nickname.getText().toString();
                if(tpNickname.isEmpty() || tpPasswd2.isEmpty() || tpPasswd.isEmpty() || tpUsername.isEmpty()){
                    if(tpNickname.isEmpty())
                        warning_nickname.setText("不能为空");
                    if(tpPasswd2.isEmpty())
                        warning_passwd2.setText("不能为空");
                    if(tpPasswd.isEmpty())
                        warning_passwd.setText("不能为空");
                    if(tpUsername.isEmpty())
                        warning_username.setText("不能为空");
                    return;
                }
                if(!tpPasswd.equals(tpPasswd2)) {
                    warning_passwd2.setText("两次密码不一致");
                    return;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String registermsg = TokenTools.register(tpUsername, tpPasswd, tpNickname);
                            if(registermsg.equals("注册成功")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }
                                });
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Register.this, registermsg, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch (Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this, "注册失败!" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });
    }

    void init(){
        username = findViewById(R.id.register_username_edit);
        passwd = findViewById(R.id.register_password_edit);
        passwd2 = findViewById(R.id.register_confirmpassword_edit);
        nickname = findViewById(R.id.register_nickname_edit);
        register = findViewById(R.id.register_register_button);
        reset = findViewById(R.id.register_reset_button);
        warning_username = findViewById(R.id.register_username_edit_warning);
        warning_passwd = findViewById(R.id.register_passwd_edit_warning);
        warning_passwd2 = findViewById(R.id.register_confirmpasswd_edit_warning);
        warning_nickname = findViewById(R.id.register_nickname_edit_warning);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
