package com.hsa.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.neo4j.driver.Config;

import testPack.DBCom;

public class Register2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        findViewById(R.id.CreateProfile).setOnClickListener(view -> {
            finishReg();
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
            String username = "testuser1";
            String clearname = "Max Mustermann";
            try (DBCom app = new DBCom(uri, user, psw, Config.defaultConfig())) {
                app.createUser(username,Register1.email, Register1.password,clearname);
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