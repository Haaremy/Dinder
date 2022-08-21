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

public class Register1 extends AppCompatActivity {

    protected static String email;
    //protected String email = "";
    protected static String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        EditText mail = findViewById(R.id.mail);
        EditText pw1 = findViewById(R.id.password1);
        EditText pw2 = findViewById(R.id.password2);
        findViewById(R.id.reg2).setOnClickListener(view -> {
                openRegister2();
        });

        final boolean[] emc = {false};

                mail.setOnFocusChangeListener(new View.OnFocusChangeListener() { //E-Mail -> ist das eine automatisierung und die Funktion kann auch so aufgerufen werden?
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // nach Beendigung der E-Mail-Eingabe
                    {
                        if(true){ // Abfrage, ob E-Mail ist valide xxx@yyy.zz / xxx@yyy.z1.zx / ... - funktioniert nicht
                            email = mail.getText().toString();
                        } else ((TextView) findViewById(R.id.ErReg)).setText("E-Mail hat ungültiges Format.");
                    }
                }
            }
        });

        pw1.addTextChangedListener(new TextWatcher() { // watches Password

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(pw1.getText().toString().length()>=5) { // text not shorter 5 chars
                    if (pw1.getText().toString().equals(pw2.getText().toString())) { // password1 /= password2 (repeat)
                        ((TextView) findViewById(R.id.ErReg)).setText("");
                        findViewById(R.id.reg2).setEnabled(true);
                        password = pw1.getText().toString();
                    } else ((TextView) findViewById(R.id.ErReg)).setText("Passwort ist nicht identisch.: " + pw1.getText().toString() + " & " + pw2.getText().toString());
                } else ((TextView) findViewById(R.id.ErReg)).setText("Passwort zu kurz.");
            }
        });
        pw2.addTextChangedListener(new TextWatcher() { // watches Password equals Password

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (pw1.getText().toString().equals(pw2.getText().toString())) { // password1 /= password2 (repeat)
                    ((TextView) findViewById(R.id.ErReg)).setText("");
                    findViewById(R.id.reg2).setEnabled(true);
                } else ((TextView) findViewById(R.id.ErReg)).setText("Passwort ist nicht identisch.: " + pw1.getText().toString() + " & " + pw2.getText().toString());
            }
        });
    }

    public void openRegister2() {
        new MyTask().execute();
        Intent intent= new Intent(this, Register2.class);
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
                app.createUser(username,email, password,clearname);
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