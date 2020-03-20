package com.example.yolo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFrag()).commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navigate,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == R.id.facebook )
        { openWebsite("https://www.facebook.com/ROBOTICS.ZINE/");
        }
        else if( id == R.id.inst){


            openWebsite("https://www.instagram.com/zine.robotics/");

        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFrag();
                            break;
                        case R.id.nav_att:
                            selectedFragment = new Attractions();
                            break;
                        case R.id.nav_supp:
                            selectedFragment = new Supp();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Login();
                            break;



                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,selectedFragment).commit();
                    return true;
                }
            };
    public void openWebsite(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW) ;
        intent.setData(Uri.parse(url));
        startActivity(intent) ;}
}
