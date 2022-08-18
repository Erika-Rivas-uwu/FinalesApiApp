package com.example.myappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class tickets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onCheckboxClicked(View view) {
        // Casilla marcada y desmarcada unu
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            Toast.makeText(this, "You checked this uwu", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "You unchecked this umu", Toast.LENGTH_SHORT).show();
        }
    }
}