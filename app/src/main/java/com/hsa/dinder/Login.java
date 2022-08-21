package com.hsa.dinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.neo4j.driver.Config;

import testPack.DBCom;

public class Login extends AppCompatActivity implements AsyncResponse {

    public String getMail(){
        EditText mail = findViewById(R.id.mail);
        return mail.getText().toString();
    }

    public String getPassword(){
        EditText password = findViewById(R.id.password1);
        return password.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText mail = findViewById(R.id.mail);
        EditText pw = findViewById(R.id.password1);
        findViewById(R.id.login).setOnClickListener(view -> sendData()); // Button send Data

        mail.addTextChangedListener(new TextWatcher() { // watches Email Input (optional)
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(true){ // Abfrage, ob E-Mail ist valide xxx@yyy.zz / xxx@yyy.z1.zx / ... - funktioniert nicht
                    findViewById(R.id.login).setEnabled(true);
                }
            }
        });


    }
    public void sendData(){
        MyTask mytask = new MyTask();
        mytask.execute();
        mytask.delegate= (AsyncResponse) this;
        System.out.println(mytask.getIsUser());
        if(mytask.getIsUser()){ // email & password sind korrekt
            openMyPage();
        }
    }

    public void openMyPage() { // Login.class -> MyPage
        Intent intent= new Intent(this, Meetings.class);
        startActivity(intent);
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        private boolean isUser;

        private boolean getIsUser(){
            return isUser;
        }
        String result;
        public AsyncResponse delegate = null;
        @Override
        protected Void doInBackground(Void... voids) {
            String uri = "neo4j+s://0a1e255a.databases.neo4j.io:7687";
            String user = "neo4j";
            String psw= "dEn2QFo4_9d2Q0INYabLQzgqfXDP3fIJEQ4k_wWgO_A";
            try (DBCom app = new DBCom(uri, user, psw, Config.defaultConfig())) {
                isUser = app.checkPasswort(getMail(), getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            delegate.processFinish(aVoid);
        }


    }
}