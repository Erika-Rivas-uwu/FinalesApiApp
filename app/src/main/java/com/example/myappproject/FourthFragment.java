package com.example.myappproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText name;
    TextView Loc,Curr,Fore;
    Button btn;
    ImageView todayImage,tomorrowImage,afterTomorrowImage;


    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn = getView().findViewById(R.id.idBtnRegister);
        name = getView().findViewById(R.id.idEdtUserName);
        Loc = getView().findViewById(R.id.idApiResponseLoc);
        Curr = getView().findViewById(R.id.idApiResponseCurr);
        Fore = getView().findViewById(R.id.idApiResponseFore);

        todayImage = getView().findViewById(R.id.TodayImage);
        tomorrowImage = getView().findViewById(R.id.TomorrowImage);
        afterTomorrowImage = getView().findViewById(R.id.AfterTomorrowImage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerWs();
            }
        });

        todayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(getActivity(),"Today's weather", Toast.LENGTH_SHORT).show();
            }
        });
        tomorrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(getActivity(),"Tomorrow's weather", Toast.LENGTH_SHORT).show();
            }
        });
        afterTomorrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(getActivity(),"after tomorrow's weather", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void changeIcons(String todayText,String tomorrowText,String afterTomorrowText) {
        String text = "";
        for (int i=0;i<4;i++){
            if(i==1) text = todayText;
            else if(i==2) text = tomorrowText;
            else text = afterTomorrowText;

            changeIconsSwitch(text,i);

        }
    }

    private void changeIconsSwitch(String text,int day) {
        System.out.println("-----.-.-.-.- Changing to icon: "+text+" ; From day#"+day);

        Drawable rs = null;
        switch (text){
            case "Sunny":
                rs = getResources().getDrawable(R.drawable.sunny);
                break;
            case "Moderate rain":
                rs = getResources().getDrawable(R.drawable.moderate_rain);
                break;
            case "Heavy rain":
                rs = getResources().getDrawable(R.drawable.heavy_rain);
                break;
            case "Patchy rain possible":
                rs = getResources().getDrawable(R.drawable.patchy_rain_possible);
                break;
            default:
                rs = getResources().getDrawable(R.drawable.ic_baseline_cloud_24);
        }

        switch (day){
            case 1:
                todayImage.setImageDrawable(rs);
                break;
            case 2:
                tomorrowImage.setImageDrawable(rs);
                break;
            case 3:
                afterTomorrowImage.setImageDrawable(rs);
                break;
        }
    }

    private void leerWs(){
        String nombre = name.getText().toString();
        System.out.println("---PRUEBA--- Entidad: "+nombre);

        String uri = "https://api.weatherapi.com/v1/forecast.json?key=a1fc9e6c652b4edfaab143006222507&q="+nombre+"&days=5&aqi=no&alerts=no";
        System.out.println("---PRUEBA--- API con entidad: "+uri);
        StringRequest getReq = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //FULL REPONSE
                    JSONObject jsonObject = new JSONObject(response);
                    //Only Location things
                    JSONObject locInfo = jsonObject.getJSONObject("location");
                    //Current and Current/condition
                    JSONObject currInfo = jsonObject.getJSONObject("current");
                    JSONObject currCondition = currInfo.getJSONObject("condition");

                    //----- Forecast
                    JSONObject foreInfo = jsonObject.getJSONObject("forecast");
                    //Forecast/forecastDayArray[Hoy:{jsonHoy},mañana:{jsonMañana},pasado:{jsonPasado}]
                    JSONArray forecastDay =  foreInfo.getJSONArray("forecastday"); //Lista de 3 días. Hoy, mañana y pasado

                    //Hoy: info and condition
                    JSONObject todayDay = forecastDay.getJSONObject(0);
                    JSONObject infoToday = todayDay.getJSONObject("day");
                    JSONObject conditionToday = infoToday.getJSONObject("condition");
                    //tomorrow: info and condition
                    JSONObject tomorrowDay = forecastDay.getJSONObject(1);
                    JSONObject infoTomorrow = tomorrowDay.getJSONObject("day");
                    JSONObject conditionTomorrow = infoTomorrow.getJSONObject("condition");
                    //afterTomorrow: info and condition
                    JSONObject afterTomorrowDay = forecastDay.getJSONObject(2);
                    JSONObject infoAfterTomorrow = afterTomorrowDay.getJSONObject("day");
                    JSONObject conditionAfterTomorrow = infoAfterTomorrow.getJSONObject("condition");

                    //----Location things
                    System.out.println("Location");
                    System.err.println(locInfo);
                    Loc.setText("--Location--\n"+locInfo.getString("name")+
                            ", ID:"+locInfo.getString("tz_id")+
                            ", Country: "+locInfo.getString("country")+
                            ", localTime: "+locInfo.getString("localtime"));

                    //--Current things
                    System.out.println("Current");
                    System.err.println(currInfo);
                    Curr.setText("--Current--\n"+currInfo.getString("temp_c")+
                            ", Condition: "+currCondition.getString("text")+
                            ", wind k/h: "+currInfo.getString("wind_kph")+
                            ", Clouds: "+currInfo.getString("cloud")+
                            ", Humidity: "+currInfo.getString("humidity")+"%");

                    //--Forecast things
                    System.out.println("Forecast");
                    System.err.println(foreInfo);
                    //Variable para los tres dias del forecast...
                    String today,tomorrow,afterTomorrow;

                    //Armado de dias
                    today = "Date: "+todayDay.getString("date")+
                            ", Temp_Max_c: "+infoToday.getString("maxtemp_c")+
                            ", Temp_Min_c: "+infoToday.getString("mintemp_c")+
                            ", Max_Wind_k/h: "+infoToday.getString("maxwind_kph")+
                            ", Average Humidity: "+infoToday.getString("avghumidity")+
                            "-- Condition: "+conditionToday.getString("text");

                    //---
                    tomorrow = "Date: "+tomorrowDay.getString("date")+
                            ", Temp_Max_c: "+infoTomorrow.getString("maxtemp_c")+
                            ", Temp_Min_c: "+infoTomorrow.getString("mintemp_c")+
                            ", Max_Wind_k/h: "+infoTomorrow.getString("maxwind_kph")+
                            ", Average Humidity: "+infoTomorrow.getString("avghumidity")+
                            "-- Condition: "+conditionTomorrow.getString("text");
                    //---
                    afterTomorrow = "Date: "+afterTomorrowDay.getString("date")+
                            ", Temp_Max_c: "+infoAfterTomorrow.getString("maxtemp_c")+
                            ", Temp_Min_c: "+infoAfterTomorrow.getString("mintemp_c")+
                            ", Max_Wind_k/h: "+infoAfterTomorrow.getString("maxwind_kph")+
                            ", Average Humidity: "+infoAfterTomorrow.getString("avghumidity")+
                            "-- Condition: "+conditionAfterTomorrow.getString("text");

                    //Change icons----
                    changeIcons(conditionToday.getString("text"),conditionTomorrow.getString("text"),conditionAfterTomorrow.getString("text"));

                    //Print each day
                    System.out.println("Forecast today");
                    System.out.println(today);
                    System.out.println("Forecast tomorrow");
                    System.out.println(tomorrow);
                    System.out.println("Forecast after tomorrow");
                    System.out.println(afterTomorrow);

                    //Set it to display----
                    Fore.setText("--Forecast--\n-*-*-Today-*-*-\n"+today+
                            "\n-*-*-Tomorrow-*-*-\n "+tomorrow+
                            "\n-*-*-After Tomorrow-*-*-\n "+afterTomorrow);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.getMessage());
            }
        });

        //Consumir API Volley Method
        Volley.newRequestQueue(getActivity()).add(getReq);

    }
}