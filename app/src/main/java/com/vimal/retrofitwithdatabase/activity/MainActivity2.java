/**
 * Created by Vimal on July-2021.
 */
package com.vimal.retrofitwithdatabase.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vimal.retrofitwithdatabase.R;
import com.vimal.retrofitwithdatabase.api.Hero;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private int current_position = 0;
    ArrayList<Hero> imageUrls = new ArrayList<Hero>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        current_position = i.getIntExtra("position", 0);
        imageUrls = (ArrayList<Hero>) i.getSerializableExtra("imagelist");
        Log.e("vml", imageUrls.size() + " questions_size");
        Log.e("vml", imageUrls + " ArrayList");
        Log.e("vml", current_position + " current_position");
    }
}