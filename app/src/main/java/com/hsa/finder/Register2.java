package com.hsa.finder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.neo4j.driver.Config;

import testPack.DBCom;

public class Register2 extends AppCompatActivity {
    protected static String user;
    protected static String clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        EditText un = findViewById(R.id.username);
        EditText cn = findViewById(R.id.clearname);
        findViewById(R.id.CreateProfile).setOnClickListener(view -> {
            finishReg();
        });



    un.addTextChangedListener(new TextWatcher() { // watches Password

        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start,
        int before, int count) {

            user = un.getText().toString();
        }
    });

        cn.addTextChangedListener(new TextWatcher() { // watches Password

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                clear = cn.getText().toString();
            }
        });
    }
    public void finishReg() {
        // is Username taken?{}
        new MyTask().execute();
        Intent intent= new Intent(this, MyPage.class);
        startActivity(intent);
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            String uri = "neo4j+s://0a1e255a.databases.neo4j.io:7687";
            String user = "neo4j";
            String psw= "dEn2QFo4_9d2Q0INYabLQzgqfXDP3fIJEQ4k_wWgO_A";
            try (DBCom app = new DBCom(uri, user, psw, Config.defaultConfig())) {
                app.createUser(Register2.user,Register1.email, Register1.password,Register2.clear);
                result = "true";
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
}