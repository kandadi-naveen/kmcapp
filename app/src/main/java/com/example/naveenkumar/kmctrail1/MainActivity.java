package com.example.naveenkumar.kmctrail1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String mobilenum="";
    private static final String TWITTER_KEY = "w5HiRXmDuANtQK6JS5bESmRVv";
    private static final String TWITTER_SECRET = "TTEKHG6drfQBpYNX2I1kCcvWpNbgXi1k7MpoWnPWGg7QOGk6bp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_main);
        final EditText editText_mobilenum=(EditText)findViewById(R.id.editText_mobile);
        final Button button_login=(Button)findViewById(R.id.button_login);
        final TextView mobileno =(TextView)findViewById(R.id.textView_no);
        final TextView text_mob =(TextView)findViewById(R.id.textView_mobile);
        final TextView text_welcome =(TextView)findViewById(R.id.textView_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mobileno.setVisibility(View.INVISIBLE);
        final DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setText("Verify with OTP");
        digitsButton.setBackgroundColor(Color.parseColor("#4dd0e1"));
        digitsButton.setVisibility(View.INVISIBLE);
        //***********************************Animations*************************************
        final Animation animation1=new TranslateAnimation(0,0,Animation.INFINITE, 200);
        animation1.setDuration(2000);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 20);
        myAnim.setInterpolator(interpolator);
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);

        //**********************************************************************************

        text_welcome.startAnimation(animation1);
        text_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_welcome.startAnimation(animation1);

            }
        });
        digitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Re-Enter the Mobile Number", Toast.LENGTH_SHORT).show();

            }
        });

        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_mobilenum.setVisibility(View.VISIBLE);
                text_mob.setVisibility(View.VISIBLE);
                mobileno.setVisibility(View.INVISIBLE);
                button_login.setVisibility(View.VISIBLE);
                digitsButton.setVisibility(View.INVISIBLE);
                mobileno.startAnimation(animTranslate);
                digitsButton.startAnimation(animTranslate);
            }
        });




        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobilenum=editText_mobilenum.getText().toString();
                if(mobilenum.length()==10) {
                    mobileno.setText("Mobile Number\n" + mobilenum);
                    digitsButton.setVisibility(View.VISIBLE);
                    mobileno.setVisibility(View.VISIBLE);
                    button_login.setVisibility(View.INVISIBLE);
                    editText_mobilenum.setVisibility(View.INVISIBLE);
                    text_mob.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Tap To Change", Toast.LENGTH_SHORT).show();
                    mobileno.startAnimation(myAnim);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Enter Valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setBackgroundColor(Color.parseColor("#4dd0e1"));
        //fab.setBackground(getDrawable(Color.parseColor("#FDD835")));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {

                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
