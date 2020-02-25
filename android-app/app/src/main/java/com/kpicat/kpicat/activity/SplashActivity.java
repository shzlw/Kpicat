package com.kpicat.kpicat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kpicat.kpicat.R;
import com.kpicat.kpicat.model.Configuration;
import com.kpicat.kpicat.service.WebServiceClient;
import com.kpicat.kpicat.util.Common;
import com.kpicat.kpicat.util.Constants;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    private WebServiceClient webServiceClient = new WebServiceClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String splashText = sharedPref.getString(Constants.SPLASH_TEXT, Constants.DEFAULT_CONFIG.getSplashText());
        String splashFontColor = sharedPref.getString(Constants.SPLASH_FONT_COLOR, Constants.DEFAULT_CONFIG.getSplashFontColor());
        String splashBgColor = sharedPref.getString(Constants.SPLASH_BG_COLOR, Constants.DEFAULT_CONFIG.getSplashBgColor());

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_login);
        layout.setBackgroundColor(Color.parseColor(splashBgColor));

        TextView splashTextView = (TextView) findViewById(R.id.splashText);
        splashTextView.setTextColor(Color.parseColor(splashFontColor));
        splashTextView.setText(splashText);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String mobileKey = sharedPref.getString(Constants.MOBILE_KEY, "");

                if (Common.isEmpty(mobileKey)) {
                    final Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    new LoginTask().execute(mobileKey);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }


    public class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String mobileKey = params[0];
            return webServiceClient.login(mobileKey);
        }

        @Override
        protected void onPostExecute(final String result) {
            if (result != null && !result.equals("bad")) {
                SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.MOBILE_KEY, result);
                editor.commit();

                new ConfigurationTask().execute(result);
            } else {
                final Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
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
                final Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                Toast.makeText(getApplicationContext(), "Server cannot be connected. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                final Intent pageIntent = new Intent(SplashActivity.this, PageActivity.class);
                pageIntent.putExtra("configuration", config);
                startActivity(pageIntent);
            }
        }
    }
}
