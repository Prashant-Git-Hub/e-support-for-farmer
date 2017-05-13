package com.example.rahul.farm;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

public class StartUpActivity extends AppCompatActivity {

    Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        if(SharedPrefManager.getmInstance(StartUpActivity.this).isLoggedIn().equals("1")) {

            String language = SharedPrefManager.getmInstance(StartUpActivity.this).getLanguage();
            myLocale=new Locale(language);
            Resources res=getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale=myLocale;
            res.updateConfiguration(conf,dm);
            Intent intent=new Intent(StartUpActivity.this,HomeActivity.class);
            startActivity(intent);
        }

    }

    public void onClickRadio(View view)
    {
        switch (view.getId())
        {
            case R.id.rdoEnglish:
                setLocal("en");
                break;
            case R.id.rdoHindi:
                setLocal("hi");
                break;
            case R.id.rdoPunjabi:
                setLocal("pu");
                break;
            default:
                setLocal("en");
                break;
        }
    }

    private void setLocal(String language) {

        SharedPrefManager.getmInstance(StartUpActivity.this).saveUserLanguage(language);
        myLocale=new Locale(language);
        Resources res=getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale=myLocale;
        res.updateConfiguration(conf,dm);
        Intent intent=new Intent(StartUpActivity.this,MainActivity.class);
        startActivity(intent);

    }

}
