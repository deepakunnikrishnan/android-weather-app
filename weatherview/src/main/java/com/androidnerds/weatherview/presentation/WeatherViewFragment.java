package com.androidnerds.weatherview.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.mylocation.LocationUtility;
import com.androidnerds.weatherview.R;
import com.androidnerds.weatherview.databinding.WeatherViewFragmentBinding;
import com.androidnerds.weatherview.di.DaggerWeatherViewComponent;
import com.androidnerds.weatherview.di.WeatherModule;
import com.androidnerds.weatherview.framework.PermissionUtility;
import com.androidnerds.weatherview.presentation.card.WeatherCardsAdapter;
import com.androidnerds.weatherview.presentation.dailyforecast.ForecastListAdapter;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;
import com.androidnerds.weatherview.presentation.util.PermissionCallbackDelegate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Fragment implements the feature as below:
 *
 * 1. Displays the Weather data for the major cities.
 * 2. Depends on the {@link WeatherViewViewModel} to get the required data.
 * 3. Displays an option for the user to fetch the WeatherData of user's location.
 *
 * The UI displays a carousel view of the weather info for different cities around the world.
 * When the user swipes through the weather card, the daily forecast for the respective city will be displayed below the carousel.
 *
 * Carousel displays an extra weather card for initiating the fetching of Weather Data for user's current location.
 * On tapping of the card, it initiates the flow for fetching the location and its weather data.
 *
 */
public class WeatherViewFragment extends Fragment implements
        PermissionCallbackDelegate.PermissionCallback, WeatherCardsAdapter.OnMyLocationClickListener {

    private WeatherViewViewModel mViewModel;
    private WeatherViewFragmentBinding binding;
    private PermissionCallbackDelegate permissionCallbackDelegate;
    private List<WeatherCard> weatherCardList;
    private List<ForecastViewData> forecastViewDataList;
    private WeatherCardsAdapter adapter;
    private ForecastListAdapter forecastListAdapter;

    @Inject
    WeatherViewModelFactory weatherViewModelFactory;

    public static WeatherViewFragment newInstance() {
        return new WeatherViewFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        DaggerWeatherViewComponent.builder().weatherModule(new WeatherModule(context))
                .build().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_view_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        permissionCallbackDelegate = new PermissionCallbackDelegate(this, this);
        setupWeatherInfoCarousel();
        setupForecastList();
    }

    /**
     * Checks whether the Location is enabled in the device or not.
     * Checks whether the user has provided Location permission for the app.
     * If available, then calls the ViewModel to get the weather data for the user's location.
     * If not available, then requests the location permission.
     */
    private void fetchUserLocationWeatherData() {
        if (LocationUtility.hasLocationEnabled(requireContext())) {
            if (PermissionUtility.hasLocationPermission(requireContext())) {
                mViewModel.fetchUserLocation();
            } else {
                PermissionUtility.requestPermission(this, PermissionCallbackDelegate.REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, weatherViewModelFactory).get(WeatherViewViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        mViewModel.getWeatherInfoMediatorLiveData()
                .observe(getViewLifecycleOwner(), weatherViewData -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.textViewLabel.setVisibility(View.VISIBLE);
                    List<WeatherCard> cardList = new ArrayList<>();
                    for (WeatherViewData viewData : weatherViewData) {
                        cardList.add(viewData.getWeatherCard());
                    }
                    adapter.submitList(cardList);
                });
        mViewModel.getErrorMessageEvent().observe(this, this::showError);
    }

    /**
     * Displays a Snackbar similar to the toast message.
     * @param s
     */
    private void showError(String s) {
        binding.progressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "" + s, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtility.onPermissionResult(requestCode, permissions, grantResults, permissionCallbackDelegate);
    }

    /**
     * Initialize and set the CarouselView for the weather.
     */
    private void setupWeatherInfoCarousel() {
        if (null == weatherCardList) {
            weatherCardList = new ArrayList<>();
        }
        adapter = new WeatherCardsAdapter(weatherCardList, this);
        binding.carouselView.setAdapter(adapter);
        binding.carouselView.setItemSelectedListener(this::onCardSelected);
    }

    /**
     * Initialize and set the recyclerview for displaying the Daily Forecast.
     */
    private void setupForecastList() {
        if (null == forecastViewDataList) {
            forecastViewDataList = new ArrayList<>();
        }
        forecastListAdapter = new ForecastListAdapter(forecastViewDataList);
        binding.recyclerViewForecast.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.recyclerViewForecast.setHasFixedSize(true);
        binding.recyclerViewForecast.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.recyclerViewForecast.setAdapter(forecastListAdapter);
    }

    /**
     * Action when the user swipes the carousel view.
     * Based on the selection, the Forecast list is updated.
     * @param position
     */
    private void onCardSelected(int position) {
        if (position >= 0) {
            List<WeatherViewData> viewData = mViewModel.getWeatherInfoMediatorLiveData().getValue();
            if (null != viewData && !viewData.isEmpty() && position < viewData.size()) {
                forecastListAdapter.submitList(viewData.get(position).getForecastViewDataList());
            }
        }
        binding.recyclerViewForecast.smoothScrollToPosition(0);
    }

    @Override
    public void onPermissionGranted() {
        fetchUserLocationWeatherData();
    }

    @Override
    public void onMyLocationClicked() {
        fetchUserLocationWeatherData();
    }
}