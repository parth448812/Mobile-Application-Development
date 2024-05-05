package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import com.squareup.picasso.Picasso;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private City mCity;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherForecastBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Forecast> forecasts = new ArrayList<>();
    ForecastsAdapter adapter;
    String url;
    //String iconUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forecast");
        getURL();
        //getForecasts();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ForecastsAdapter();
        binding.recyclerView.setAdapter(adapter);


    }

    void getURL(){

        Request request = new Request.Builder()
                .url("https://api.weather.gov/points/"+mCity.getLat()+","+mCity.getLng())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.d("demo", "onResponse: " + body);

                    try {
                        JSONObject rootJson = new JSONObject(body);
                        JSONObject propertiesJsonObject = rootJson.getJSONObject("properties");
                        url = propertiesJsonObject.getString("forecast");
                        Log.d("url", "onResponse: " + url);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                getForecasts();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {

                }
            }
        });
    }
    void getForecasts(){

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.d("demo", "onResponse: " + body);

                    try {
                        JSONObject rootJson = new JSONObject(body);
                        JSONObject propertiesJsonObject = rootJson.getJSONObject("properties");
                        JSONArray forecastsJsonArray = propertiesJsonObject.getJSONArray("periods");
                        forecasts.clear();

                        for (int i = 0; i < forecastsJsonArray.length(); i++) {
                            JSONObject forecastJsonObject = forecastsJsonArray.getJSONObject(i);
                            Forecast forecast = new Forecast(forecastJsonObject);
                            forecasts.add(forecast);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                } else {

                }
            }
        });
    }


    class ForecastsAdapter extends RecyclerView.Adapter<ForecastsAdapter.ContactViewHolder>{
        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactViewHolder(ForecastListItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            Forecast forecast = forecasts.get(position);
            holder.setupUI(forecast);
        }

        @Override
        public int getItemCount() {
            return forecasts.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            ForecastListItemBinding mBinding;
            Forecast mForecast;
            public ContactViewHolder(ForecastListItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding = vhBinding;

            }

            public void setupUI(Forecast forecast){
                this.mForecast = forecast;
                mBinding.textViewDateTime.setText(forecast.getDt_text());
                mBinding.textViewTemperature.setText(forecast.getTemp() + " F");
                mBinding.textViewWindSpeed.setText("Wind Speed: " + forecast.getWindSpeed());
                mBinding.textViewHumidity.setText("Humidity: " + forecast.getHumidity() + "%");
                mBinding.textViewForecast.setText(forecast.getDescription());

                Picasso.get().load(forecast.getIconUrl()).into(mBinding.imageView);
            }
        }

    }

}