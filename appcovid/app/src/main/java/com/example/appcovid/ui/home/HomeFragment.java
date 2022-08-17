package com.example.appcovid.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcovid.R;
import com.example.appcovid.api.CoronaApi;
import com.example.appcovid.api.CoronaService;
import com.example.appcovid.data.AllCountry;
import com.example.appcovid.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered, tvLastUpdated;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tvTotalConfirmed = root.findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths = root.findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = root.findViewById(R.id.tvTotalRecovered);
        tvLastUpdated = root.findViewById(R.id.tvLastUpdated);
       // progressBar = root.findViewById(R.id.progress_circular_home);
        callApi();
        return root;
    }
    private String getDate(long milliSecond){
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void callApi() {
        CoronaService coronaService =
                CoronaApi.getRetrofitInstance().create(CoronaService.class);
        Call<AllCountry> call = coronaService.getAllCountries();
        call.enqueue(new Callback<AllCountry>() {
            @Override
            public void onResponse(Call<AllCountry> call, Response<AllCountry> response) {

                AllCountry allcountry =response.body();
                if(allcountry!=null){
                    tvTotalConfirmed.setText(allcountry.getCases());
                    tvTotalDeaths.setText(allcountry.getDeaths());
                    tvTotalRecovered.setText(allcountry.getRecovered());
                    tvLastUpdated.setText("Cập nhật lần cuối"+"\n"+getDate(allcountry.getUpdated()));

                }
            }

            @Override
            public void onFailure(Call<AllCountry> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


}