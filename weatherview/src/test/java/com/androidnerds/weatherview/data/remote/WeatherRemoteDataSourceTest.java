package com.androidnerds.weatherview.data.remote;

import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.data.remote.service.WeatherDataService;
import com.androidnerds.weatherview.di.RemoteModule;
import com.androidnerds.weatherview.rx.DefaultScheduler;
import com.androidnerds.weatherview.testutils.MockResponseFileReader;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;

public class WeatherRemoteDataSourceTest {

    private WeatherRemoteDataSource weatherRemoteDataSource;
    private MockWebServer mockWebServer;
    private MockResponseFileReader mockResponseFileReader;
    WeatherDataService weatherDataService;
    private SchedulerProvider schedulerProvider;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new DefaultScheduler();
        Retrofit retrofit = RemoteModule.provideRetrofit(new ApiConfig(ApiConstants.BASE_URL),
                RemoteModule.provideOkHttpClient(
                        RemoteModule.provideHttpLoggingInterceptor()
                ),
                RemoteModule.provideGsonConverterFactory(
                        RemoteModule.provideGson()
                ),
                RemoteModule.provideRxJava3CallAdapterFactory()
        );
        weatherDataService = retrofit.create(WeatherDataService.class);
        weatherRemoteDataSource = new WeatherRemoteDataSource(weatherDataService, schedulerProvider);
        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
        if (null != mockResponseFileReader) {
            mockResponseFileReader = null;
        }
    }

    @Test
    public void testGetLocationForQuerySuccess() {
        this.mockResponseFileReader = new MockResponseFileReader(ApiConstants.LOCATION_SEARCH);
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
                return new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockResponseFileReader.getContent());
            }
        });

        LocationInfoDto locationInfoDto = weatherRemoteDataSource.getLocation("London").blockingGet();
        Assert.assertNotNull(locationInfoDto);
    }

    @Test
    public void testGetLocationForLatLngSuccess() {
        this.mockResponseFileReader = new MockResponseFileReader(ApiConstants.LOCATION_SEARCH);
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
                return new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockResponseFileReader.getContent());
            }
        });

        LocationInfoDto locationInfoDto = weatherRemoteDataSource.getLocation(10.0, 10.0).blockingGet();
        Assert.assertNotNull(locationInfoDto);
    }

    @Test
    public void testGetWeatherSuccess() {
        this.mockResponseFileReader = new MockResponseFileReader(ApiConstants.WEATHER_DATA);
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
                return new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockResponseFileReader.getContent());
            }
        });

        WeatherInfoDto weatherInfoDto = weatherRemoteDataSource.getWeather(10).blockingGet();
        Assert.assertNotNull(weatherInfoDto);
    }

}