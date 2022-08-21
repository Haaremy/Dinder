package com.hsa.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        button =  findViewById(R.id.buttonlogin);
        button2 =  findViewById(R.id.buttonregister);
        button.setOnClickListener(view -> openLogIn());
        button2.setOnClickListener(view -> openRegister());
    }
    public void openLogIn() {
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }
    public void openRegister() {
        Intent intent= new Intent(this, Register1.class);
        startActivity(intent);
    }

}