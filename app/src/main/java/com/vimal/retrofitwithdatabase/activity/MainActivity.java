/**
 * Created by Vimal on July-2021.
 */
package com.vimal.retrofitwithdatabase.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.vimal.retrofitwithdatabase.R;
import com.vimal.retrofitwithdatabase.adapter.PostAdapter;
import com.vimal.retrofitwithdatabase.api.Hero;
import com.vimal.retrofitwithdatabase.api.RetrofitClient;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerviewpost;
    ArrayList<Hero> categorypost;
    PostAdapter postAdapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        recyclerviewpost = findViewById(R.id.recyclerviewpost);

        categorypost = new ArrayList<Hero>();
        if (isConnected()) {
            getHeroes();
        } else {
            Toast.makeText(getApplicationContext(), "Offline", Toast.LENGTH_SHORT).show();
            loadoffline();
        }
    }

    private void getHeroes() {
        Call<ArrayList<Hero>> call = RetrofitClient.getInstance().getMyApi().getHeroes();
        call.enqueue(new Callback<ArrayList<Hero>>() {
            @Override
            public void onResponse(Call<ArrayList<Hero>> call, Response<ArrayList<Hero>> response) {

                ArrayList<Hero> heroList = response.body();
                if (heroList.size() > 0) {

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.delete(Hero.class);
                    realm.copyToRealmOrUpdate(heroList);
                    realm.commitTransaction();

                    postAdapter = new PostAdapter(MainActivity.this, heroList);
                    recyclerviewpost.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerviewpost.setAdapter(postAdapter);
                    recyclerviewpost.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_left_to_right));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Hero>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loadoffline();
            }
        });
    }

    public void loadoffline() {
        RealmResults<Hero> heroList = realm.where(Hero.class).findAll();
		 //Hero hero = realm.where(Hero.class).equalTo("name", "Spiderman").findFirst();
        //RealmResults<Hero> herolst = realm.where(Hero.class).equalTo("name", "Spiderman").findAll();
        if (heroList.size() > 0) {
            postAdapter = new PostAdapter(MainActivity.this, heroList);
            recyclerviewpost.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            recyclerviewpost.setAdapter(postAdapter);
            recyclerviewpost.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_left_to_right));
        }
        Log.e("vml", heroList + " list");
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}