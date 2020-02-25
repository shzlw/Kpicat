package com.kpicat.kpicat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kpicat.kpicat.R;
import com.kpicat.kpicat.model.Configuration;
import com.kpicat.kpicat.service.WebServiceClient;
import com.kpicat.kpicat.util.Constants;

public class LoginActivity extends Activity {

    //private CustomProgressDialog progress;

    private WebServiceClient webServiceClient = new WebServiceClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //progress = new CustomProgressDialog(this, "Loading");

        final EditText username = (EditText) findViewById(R.id.usernameInput);
        final EditText password = (EditText) findViewById(R.id.passwordInput);
        final EditText corpName = (EditText) findViewById(R.id.corpNameInput);

        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username or password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    //progress.getDialog().show();
                    new LoginTask().execute(username.getText().toString(),
                                            password.getText().toString(),
                                            corpName.getText().toString());
                }
            }
        });

    }

    public class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String corpName = params[2];

            return webServiceClient.login(username, password, corpName);
        }

        @Override
        protected void onPostExecute(final String result) {

            //progress.getDialog().dismiss();
            if (result != null && !result.equals("bad")) {

                SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.MOBILE_KEY, result);
                editor.commit();

                new ConfigurationTask().execute(result);
            } else {
                Toast.makeText(getApplicationContext(), "Login failed. Check username, password, corporation and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ConfigurationTask extends AsyncTask<String, Void, Configuration> {
        @Override
        protected Configuration doInBackground(String... params) {
            String mobileKey = params[0];
            return webServiceClient.fetchConfiguration(mobileKey);
        }

        @Override
        protected void onPostExecute(final Configuration config) {
            if (config == null) {
                Toast.makeText(getApplicationContext(), "Server cannot be connected. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                final Intent pageIntent = new Intent(LoginActivity.this, PageActivity.class);
                pageIntent.putExtra("configuration", config);
                startActivity(pageIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
