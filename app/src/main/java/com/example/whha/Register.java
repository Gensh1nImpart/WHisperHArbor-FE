package com.example.whha;

import android.annotation.SuppressLint;
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

public class Register extends LoadTools{
    EditText username;
    EditText passwd;
    EditText passwd2;
    EditText nickname;

    EditText mail;
    Button register;
    Button reset;
    TextView warning_username;
    TextView warning_passwd;
    TextView warning_passwd2;
    TextView warning_nickname;
    TextView warning_mail;

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
            mail.setText("");
        });
        register.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                warning_nickname.setText("");
                warning_passwd2.setText("");
                warning_passwd.setText("");
                warning_username.setText("");
                warning_mail.setText("");
                String tpUsername = username.getText().toString();
                String tpPasswd = passwd.getText().toString();
                String tpPasswd2 = passwd2.getText().toString();
                String tpNickname = nickname.getText().toString();
                String tpMail = mail.getText().toString();
                if(tpMail.isEmpty() || tpNickname.isEmpty() || tpPasswd2.isEmpty() || tpPasswd.isEmpty() || tpUsername.isEmpty()){
                    if(tpNickname.isEmpty())
                        warning_nickname.setText("不能为空");
                    if(tpPasswd2.isEmpty())
                        warning_passwd2.setText("不能为空");
                    if(tpPasswd.isEmpty())
                        warning_passwd.setText("不能为空");
                    if(tpUsername.isEmpty())
                        warning_username.setText("不能为空");
                    if(tpMail.isEmpty())
                        warning_mail.setText("不能为空");
                    return;
                }
                boolean flag = true;
                if(!tpUsername.matches("^[a-zA-Z0-9]{6,12}$")){
                    flag = false;
                    warning_nickname.setText("用户名应该6-12位，仅包含数字的字母!");
                }
                if(!tpPasswd.matches("^[a-zA-Z0-9]{6,12}$")){
                    flag = false;
                    warning_nickname.setText("密码应该6-12位，仅包含数字的字母!");
                }
                if(!tpPasswd.equals(tpPasswd2)) {
                    warning_passwd2.setText("两次密码不一致");
                    flag = false;
                }
                if(!tpNickname.matches("^.{1,8}$")){
                    flag = false;
                    warning_nickname.setText("用户名应该1-8位");
                }
                if(!tpMail.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")){
                    flag = false;
                    warning_mail.setText("请输入正确的邮箱");
                }
                if(!flag)
                    return;
                Register.super.showLoadingDialog();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String registermsg = TokenTools.register(tpUsername, tpPasswd, tpNickname, tpMail);
                            if(registermsg.equals("注册成功")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Register.super.dismissLoadingDialog();
                                        Toast.makeText(Register.this, "注册成功, 请前往邮箱进行验证", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }
                                });
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Register.super.dismissLoadingDialog();
                                        Toast.makeText(Register.this, registermsg, Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }catch (Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Register.super.dismissLoadingDialog();
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
        mail = findViewById(R.id.register_mail_edit);
        register = findViewById(R.id.register_register_button);
        reset = findViewById(R.id.register_reset_button);
        warning_username = findViewById(R.id.register_username_edit_warning);
        warning_passwd = findViewById(R.id.register_passwd_edit_warning);
        warning_passwd2 = findViewById(R.id.register_confirmpasswd_edit_warning);
        warning_nickname = findViewById(R.id.register_nickname_edit_warning);
        warning_mail = findViewById(R.id.register_mail_edit_warning);
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
