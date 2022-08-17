package com.example.appcovid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcovid.data.CountryList;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    ImageView ivCountryPoster;
    TextView tvCountryName, tvTodayCases, tvTodayDeath, tvTotalTests, tvTotalCases, tvTotalDeaths, tvTotalRecovered;
    private Button btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCountryName = findViewById(R.id.tvCountryName);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeath = findViewById(R.id.tvTodayDeath);
        tvTotalTests = findViewById(R.id.tvTotalTests);
        tvTotalCases = findViewById(R.id.tvTotalCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = findViewById(R.id.tvTotalRecovered);

        ivCountryPoster = findViewById(R.id.ivCountryPoster);
//        CountryList covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");
//
//        tvCountryName.setText(covidCountry.getCountry());
//        tvTodayCases.setText(covidCountry.getTodayCases());
//        tvTodayDeath.setText(covidCountry.getTodayDeaths());
//        tvTotalTests.setText(covidCountry.getTests());
//        tvTotalCases.setText(covidCountry.getCases());
//        tvTotalDeaths.setText(covidCountry.getDeaths());
//        tvTotalRecovered.setText(covidCountry.getRecovered());


        String countryName = getIntent().getStringExtra("country");
        String todayCase = getIntent().getStringExtra("todayCase");
        String todayDeath = getIntent().getStringExtra("todayDeath");
        String totalCases = getIntent().getStringExtra("cases");
        String totalDeaths = getIntent().getStringExtra("deaths");
        String totalTests = getIntent().getStringExtra("tests");
        String totalRecovered = getIntent().getStringExtra("recovered");

        tvCountryName.setText(countryName);
        tvTodayCases.setText(todayCase);
        tvTodayDeath.setText(todayDeath);
        tvTotalTests.setText(totalTests);
        tvTotalCases.setText(totalCases);
        tvTotalDeaths.setText(totalDeaths);
        tvTotalRecovered.setText(totalRecovered);


        Picasso.with(getApplicationContext()).
                load(getIntent().getStringExtra("flag"))
                .into(ivCountryPoster);


    }
}
