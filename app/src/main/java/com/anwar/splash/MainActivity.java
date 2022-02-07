package com.anwar.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView slogan,name;
    private ImageView logo;
    private View topView1,topView2,topView3;
    private View bottomView1,bottomView2,bottomView3;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        |View.SYSTEM_UI_FLAG_FULLSCREEN
        |View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        slogan = findViewById(R.id.slogan);

        logo = findViewById(R.id.logo);

        topView1 = findViewById(R.id.topView1);
        topView2 = findViewById(R.id.topView2);
        topView3 = findViewById(R.id.topView3);

        bottomView1 = findViewById(R.id.bottomView1);
        bottomView2 = findViewById(R.id.bottomView2);
        bottomView3 = findViewById(R.id.bottomView3);

        Animation logoAmination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_animation);
        Animation nameAmination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_animation);

        Animation topView1Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_view_animation);
        Animation topView2Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_view_animation);
        Animation topView3Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_view_animation);

        Animation bottomView1Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_view_animation);
        Animation bottomView2Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_view_animation);
        Animation bottomView3Amination = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_view_animation);

        topView1.startAnimation(topView1Amination);
        bottomView1.startAnimation(bottomView1Amination);

        topView1Amination.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                topView2.setVisibility(View.VISIBLE);
                bottomView2.setVisibility(View.VISIBLE);

                topView2.startAnimation(topView2Amination);
                bottomView2.startAnimation(bottomView2Amination);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView2Amination.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                topView3.setVisibility(View.VISIBLE);
                bottomView3.setVisibility(View.VISIBLE);

                topView3.startAnimation(topView3Amination);
                bottomView3.startAnimation(bottomView3Amination);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView3Amination.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.VISIBLE);
                logo.setAnimation(logoAmination);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logoAmination.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                name.setVisibility(View.VISIBLE);
                name.startAnimation(nameAmination);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        nameAmination.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slogan.setVisibility(View.VISIBLE);
                final String animateTxt = slogan.getText().toString();
                slogan.setText("");
                count = 0;
                new CountDownTimer(animateTxt.length()*100,100){
                    @Override
                    public void onTick(long l) {
                        slogan.setText(slogan.getText().toString()+animateTxt.charAt(count));
                        count++;

                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        },9000);

    }
}