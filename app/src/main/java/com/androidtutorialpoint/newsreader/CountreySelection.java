package com.androidtutorialpoint.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.assad.newsreader.R;
import com.hbb20.CountryCodePicker;


public class CountreySelection extends AppCompatActivity {
    String countryCodeAndroid = "91";
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrey_selection);
        setTitle("Breaking News");
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        Button button=(Button)findViewById(R.id.btn_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUser.setValue(CountreySelection.this, SPUser.COUNTREY_NAME,"HK");
                SPUser.setValue(CountreySelection.this, SPUser.COUNTREY_NAMEB,"HONG KONG");
                Intent intent = new Intent(CountreySelection.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCodeAndroid = ccp.getSelectedCountryCode();
                String str_fgjds=ccp.getSelectedCountryNameCode();
                String str_name=ccp.getSelectedCountryName();
                Log.d("Country",str_fgjds);
                SPUser.setValue(CountreySelection.this, SPUser.COUNTREY_NAME,str_fgjds);
                SPUser.setValue(CountreySelection.this, SPUser.COUNTREY_NAMEB,str_name);
                Intent intent = new Intent(CountreySelection.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
