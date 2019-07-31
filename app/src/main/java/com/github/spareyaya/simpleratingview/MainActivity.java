package com.github.spareyaya.simpleratingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.spareyaya.SimpleRatingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleRatingView simpleRatingView1 = findViewById(R.id.simple_rating_view_1);
        simpleRatingView1.setOnRatingChangeListener(new SimpleRatingView.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int oldRating, int newRating) {
                Toast.makeText(MainActivity.this, "oldRating:" + oldRating + " newRating:" + newRating, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
