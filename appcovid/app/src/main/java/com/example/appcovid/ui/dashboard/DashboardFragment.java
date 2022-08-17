package com.example.appcovid.ui.dashboard;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;



import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcovid.R;
import com.example.appcovid.adapter.CountryAdapter;
import com.example.appcovid.api.CoronaApi;
import com.example.appcovid.api.CoronaService;
import com.example.appcovid.data.CountryList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {


    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<CountryList> countriesList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        recyclerView =  root.findViewById(R.id.rvCountry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        countryAdapter = new CountryAdapter();
        recyclerView.setAdapter(countryAdapter);
        countriesList = new ArrayList<>();

        callApi();

        return root;
    }



    private void callApi() {
        CoronaService coronaService =
                CoronaApi.getRetrofitInstance().create(CoronaService.class);

        Call<List<CountryList>> call = coronaService.getCountries();

        call.enqueue(new Callback<List<CountryList>>() {
            @Override
            public void onResponse(Call<List<CountryList>> call, Response<List<CountryList>> response) {
                countriesList =response.body();
                if (countriesList != null) {
                    for (CountryList countriesResponse : countriesList) {

                        System.out.println("Country Name : " + countriesResponse.getCountry() + " - Death Count : " + countriesResponse.getDeaths() + "\n");

                        countryAdapter.setCountryList(getContext(), countriesList);

                    }
                }

            }

            @Override
            public void onFailure(Call<List<CountryList>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }

        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.country_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = new androidx.appcompat.widget.SearchView(getActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (countryAdapter != null) {
                    countryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

//
}