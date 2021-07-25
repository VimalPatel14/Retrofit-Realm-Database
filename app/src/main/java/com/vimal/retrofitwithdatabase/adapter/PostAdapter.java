/**
 * Created by Vimal on July-2021.
 */
package com.vimal.retrofitwithdatabase.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vimal.retrofitwithdatabase.R;
import com.vimal.retrofitwithdatabase.activity.MainActivity2;
import com.vimal.retrofitwithdatabase.api.Hero;

import java.io.Serializable;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyView> {

    Context context;
    private List<Hero> list;


    public class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        CardView mainlay;
        ImageView image;

        public MyView(View view) {
            super(view);
            mainlay = view.findViewById(R.id.mainlay);
            textView = view.findViewById(R.id.textview);
            image = view.findViewById(R.id.image);
        }
    }


    public PostAdapter(Context context, List<Hero> horizontalList) {
        this.context = context;
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post, parent, false);
        return new MyView(itemView);
    }


    public int DpToPx(Activity activity, int dp) {
        Resources r = activity.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;

    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        Glide.with(context).load(list.get(position).getImageurl()).placeholder(R.drawable.couple).into(holder.image);
        holder.textView.setText(list.get(position).getName());

        holder.mainlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity2.class);
                intent.putExtra("position", position);
                intent.putExtra("imagelist", (Serializable) list);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
