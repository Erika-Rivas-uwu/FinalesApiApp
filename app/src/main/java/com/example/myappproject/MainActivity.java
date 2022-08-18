package com.example.myappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FirstFragment f = new FirstFragment();
    MapsFragment s = new MapsFragment();
    ThirdFragment t = new ThirdFragment();
    FourthFragment fo = new FourthFragment();
    HiddenFragment hF = new HiddenFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView n = findViewById(R.id.bottomNavigationView);
        n.setOnNavigationItemSelectedListener(mThing);

        loadFragment(hF);//este lo llamamos aqui para cargar el gatito
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mThing = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.calendar:
                    loadFragment(f);
                    return true;
                case R.id.location:
                    loadFragment(s);
                    return true;
                case R.id.wallet:
                    loadFragment(t);
                    return true;
                case R.id.settings:
                    loadFragment(fo);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();


    }

    public void showTicketImage(View view) {
        Intent next = new Intent(this,tickets.class);
        startActivity(next);
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            Toast.makeText(this, "You checked this..", Toast.LENGTH_SHORT).show();
        }
    }
}