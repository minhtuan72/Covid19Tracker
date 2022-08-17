package com.example.appcovid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcovid.DetailActivity;
import com.example.appcovid.R;
import com.example.appcovid.api.CoronaApi;
import com.example.appcovid.api.CoronaService;
import com.example.appcovid.data.AllCountry;
import com.example.appcovid.data.CountryList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> implements Filterable {
    private List<CountryList> countriesList;
    private List<CountryList> countriesListed;

    private Context context;

    public void setCountryList(Context context, final  List<CountryList> countriesList){
        this.context = context;
        if (this.countriesList == null){
            this.countriesList = countriesList;
            this.countriesListed = countriesList;
            notifyItemChanged(0, countriesListed.size());
        } else{
            final DiffUtil.DiffResult result =DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CountryAdapter.this.countriesList.size();
                }

                @Override
                public int getNewListSize() {
                    return countriesList.size();

                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CountryAdapter.this.countriesList.get(oldItemPosition).getCountry()== countriesList.get(newItemPosition).getCountry();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CountryList newList =CountryAdapter.this.countriesList.get(oldItemPosition);
                    CountryList oldList =countriesList.get(newItemPosition);
                    return newList.getCountry()== oldList.getCountry();
                }
            });
            this.countriesList = countriesList;
            this.countriesListed = countriesList;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public CountryHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);
        return new CountryHolder(view);
    }


    @Override
    public void onBindViewHolder(CountryHolder holder, final int position) {
        holder.countryTitle.setText("Ca nhiễm: " + countriesListed.get(position).getCases());
        holder.countryName.setText(countriesListed.get(position).getCountry());
        Picasso.with(context).load(countriesListed.get(position).getCountryInfo().getFlag()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), DetailActivity.class);


                view.getContext().startActivity(intent);

                CoronaService coronaService =
                        CoronaApi.getRetrofitInstance().create(CoronaService.class);


                Call<CountryList> call = coronaService.getCountryInfo(countriesListed.get(position).getCountry());
               call.enqueue(new Callback<CountryList>() {
                   @Override
                   public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                       Intent intent = new Intent(view.getContext(), DetailActivity.class);

                        if (response.body() != null) {
                            intent.putExtra("country", response.body().getCountry());
                            intent.putExtra("todayCase", response.body().getTodayCases());
                            intent.putExtra("todayDeath", response.body().getTodayDeaths());
                            intent.putExtra("flag", response.body().getCountryInfo().getFlag());
                            intent.putExtra("cases", response.body().getCases());
                            intent.putExtra("deaths", response.body().getDeaths());
                            intent.putExtra("tests", response.body().getTests());
                            intent.putExtra("recovered", response.body().getRecovered());
                        }


                        view.getContext().startActivity(intent);
                   }

                   @Override
                   public void onFailure(Call<CountryList> call, Throwable t) {
                       Log.d("Error", t.getMessage());
                   }
               });
           }
       });
    }


    @Override
    public Filter getFilter() {
        return CountriesFilter;
    }

    private Filter CountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryList> filteredCovidCountry = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCovidCountry.addAll(countriesListed);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryList itemCovidCountry : countriesListed) {
                    if (itemCovidCountry.getCountry().toLowerCase().contains(filterPattern)) {
                        filteredCovidCountry.add(itemCovidCountry);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countriesList.clear();
            countriesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public int getItemCount() {
        if (countriesList != null) {
            return countriesListed.size();
        } else {
            return 0;
        }
    }

    public class CountryHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView countryTitle;
        TextView countryName;
        ImageView image;
        public CountryHolder( View v) {
            super(v);

            relativeLayout = v.findViewById(R.id.cvCountry);
            countryTitle = v.findViewById(R.id.tvCountryDeath);
            countryName = v.findViewById(R.id.tvCountryName);
            image = v.findViewById(R.id.ivCountryPoster);
        }
    }
}
