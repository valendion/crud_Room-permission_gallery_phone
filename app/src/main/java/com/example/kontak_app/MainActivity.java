package com.example.kontak_app;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private TextInputLayout textinput_username,textinput_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btn_login = findViewById(R.id.btn_login);
        textinput_username = findViewById(R.id.text_input_username);
        textinput_pass = findViewById(R.id.text_input_password);






        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, daftar_teman_activity.class);
                startActivity(intent);
            }
        });*/

    }

    private boolean validasiUsername(){
        String usernameInput = textinput_username.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()){
            textinput_username.setError("Username anda tidak boleh kosong");
            return  false;
        }else  if (usernameInput.length() > 5){
            textinput_username.setError("Username anda terlalu panjang");
            return  false;
        }else {
            textinput_username.setError(null);
            return  true;
        }

    }

    private  boolean validasiPassword(){
        String passwordInput = textinput_pass.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textinput_pass.setError("Password anda tidak boleh kosong");
            return  false;

        }else {
            textinput_pass.setError(null);
            return  true;
        }


    }

    public  void  confrimInput(View view){
       if (validasiUsername() | validasiPassword()){
           String usernameInput = textinput_username.getEditText().getText().toString().trim();
           String passwordInput = textinput_pass.getEditText().getText().toString().trim();
           if (usernameInput.equals("admin") && passwordInput.equals("12345")){
               Intent intent = new Intent(MainActivity.this, daftar_teman_activity.class);
               startActivity(intent);
           }else
           {
               textinput_username.setError("Username anda salah");
               textinput_pass.setError("Password anda salah");

           }
       }

    }




}
