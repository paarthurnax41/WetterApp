package com.example.wetterappb;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;


import com.android.volley.toolbox.Volley;
import com.example.wetterappb.model.WeatherResponse;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    EditText eingabeText;
    TextView ortTextView;
    TextView temperaturTextView;
    TextView beschreibungTextView;
    TextView gefuhlteTemperatur;
    ImageButton sucheButton;
    TextView windGeschwnindigKeitZwei;
    TextView windGeschwnindigKeit;
    TextView temperaturTextViewZwei;
    TextView gefuhlteTemperaturZwei;
    TextView beschreibungTextViewZwei;
    ImageView wetterBild;


    RequestQueue queue;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        eingabeText = findViewById(R.id.editTextStadt);
        ortTextView = findViewById(R.id.ortTextView);
        temperaturTextView = findViewById(R.id.TemperaturTextZwei);
        beschreibungTextView = findViewById(R.id.BeschreibungTextZwei);
        gefuhlteTemperatur = findViewById(R.id.GefuhlteTempViewZwei);
        windGeschwnindigKeit = findViewById(R.id.WindText2);
        windGeschwnindigKeitZwei = findViewById(R.id.WindText);
        gefuhlteTemperaturZwei = findViewById(R.id.GefuhlteTempView);
        beschreibungTextViewZwei = findViewById(R.id.BeschreibungText);
        temperaturTextViewZwei = findViewById(R.id.TemperaturText);
        wetterBild = findViewById(R.id.wetterBildView);


        queue = Volley.newRequestQueue(this);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    public void holeWetterDaten(View view) {
        String eingabe = eingabeText.getText().toString();
        if (!eingabe.equals("")) {
            url = "http://api.weatherstack.com/current?access_key=958c6cca401515995777540c9968dde2&query=" + eingabe;
//            ortTextView.setVisibility(View.VISIBLE);
//            beschreibungTextView.setVisibility(View.VISIBLE);
//            temperaturTextView.setVisibility(View.VISIBLE);
//            windGeschwnindigKeit.setVisibility(View.VISIBLE);
//            gefuhlteTemperatur.setVisibility(View.VISIBLE);
//            gefuhlteTemperaturZwei.setVisibility(View.VISIBLE);
//            beschreibungTextViewZwei.setVisibility(View.VISIBLE);
//            temperaturTextViewZwei.setVisibility(View.VISIBLE);
//            windGeschwnindigKeitZwei.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.weatherstack.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //
            WeatherInformationIF api = retrofit.create(WeatherInformationIF.class);
            api.wetter("958c6cca401515995777540c9968dde2", eingabe).enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    WeatherResponse weather = response.body();
                    Log.i("FullscreenActivityBek", "Temperatur: " + weather.getCurrent().getTemperature());
                    //    DIE WETTER DATEN ALS JSONOBJECT ABSPEICHERN IN JAVA
                    // JSONObject wetterdatenJson = new JSONObject(response);

                    //JSONObject lokation = (JSONObject) wetterdatenJson.get("location");
                    // JSONObject wetterBeschreibung = (JSONObject) wetterdatenJson.get("weather_descriptions");

                    // JSONObject current = (JSONObject) wetterdatenJson.get("current");
                    // CONDITION IST EIN CHILD VON CURRENT ; DESWEGEN CURRENT.GET(CONDITION);
                    // JSONObject kondition = (JSONObject) current.get("condition");
                    // JSONObject wetterBild = (JSONObject) current.get("weather_icons");
                    // JSONObject gefuhlteTempJson = (JSONObject) wetterdatenJson.get("feelslike");

                    // ortTextView.setText("Wetter in "+lokation.get("name").toString()+" am "+ lokation.get("localtime").toString());
                    ortTextView.setText(weather.getRequest().getQuery() + " - " + weather.getLocation().getLocaltime());
                    // Log.i("FehlerLocation",lokation.getString("name"));
                    // temperaturTextView.setText(current.get("temperature").toString() +" Grad");
                    temperaturTextView.setText(weather.getCurrent().getTemperature());
                    gefuhlteTemperatur.setText(weather.getCurrent().getFeelslike());
                    windGeschwnindigKeit.setText(weather.getCurrent().getWind_speed());
                    // beschreibungTextView.setText(current.get("weather_descriptions").toString().replaceAll("[\",\\[]","").replaceAll("\\]",""));
                    beschreibungTextView.setText(Arrays.toString(weather.getCurrent().getWeather_descriptions()).replaceAll("[\",\\[]", ""));
                    // String bildUrl = current.get("weather_icons").toString().replaceAll("[\\[\\]\"]","");
                    String bildUrl = Arrays.toString(weather.getCurrent().getWeather_icons()).replaceAll("[\\[\\]\"]", "");
                    Log.i("WetterIcon", bildUrl);

                    Picasso.get().load(bildUrl).into(wetterBild);


                    ortTextView.setVisibility(View.VISIBLE);
                    beschreibungTextView.setVisibility(View.VISIBLE);
                    temperaturTextView.setVisibility(View.VISIBLE);
                    windGeschwnindigKeit.setVisibility(View.VISIBLE);
                    gefuhlteTemperatur.setVisibility(View.VISIBLE);
                    gefuhlteTemperaturZwei.setVisibility(View.VISIBLE);
                    beschreibungTextViewZwei.setVisibility(View.VISIBLE);
                    temperaturTextViewZwei.setVisibility(View.VISIBLE);
                    windGeschwnindigKeitZwei.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {

                    Log.i("Fehler",t.toString());
                }
            });


        }


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}