package com.example.myappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AreYouSigned extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_signed);
    }

    public void displayToast(View view) {
        //no funciona
        Toast.makeText(AreYouSigned.this,"Lorem Ipsum...",Toast.LENGTH_SHORT);
    }

    public void mainMenu(View view) {
        //Inicia el Menu principal o lobby
        Intent next = new Intent(this,MainActivity.class);
        startActivity(next);
    }
}