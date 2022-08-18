package com.example.myappproject;

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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerWs();
            }
        });
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
                    JSONObject jsonObject = new JSONObject(response);


                    //System.out.println(jsonObject.getString("location"));
                    Loc.setText("Location:"+jsonObject.getString("location"));
                    //System.out.println(jsonObject.getString("current"));
                    Curr.setText("Current:"+jsonObject.getString("current"));
                    //System.out.println(jsonObject.getString("forecast"));
                    Fore.setText("Forecast:"+jsonObject.getString("forecast"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.out.println("ERRRRRRRRRRRROR");
                Log.e("Error",error.getMessage());
            }
        });

        //Consumir API Volley Method
        Volley.newRequestQueue(getActivity()).add(getReq);

    }
}