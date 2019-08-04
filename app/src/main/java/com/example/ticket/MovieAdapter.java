package com.example.ticket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mCtx;
    private List<Movie> movieList;
    public static final String title = "nameKey";
    public static final String descr = "phoneKey";

    public MovieAdapter(Context mCtx, List<Movie> movieList) {
        this.mCtx = mCtx;
        this.movieList = movieList;
    }
        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.movieslist, null);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MovieViewHolder holder, int position) {
            final Movie movie = movieList.get(position);

            Glide.with(mCtx)
                    .load(movie.getImage())
                    .into(holder.imageView);

            holder.textViewTitle.setText(movie.getTitle());
            holder.textViewShortDesc.setText(movie.getShortdesc());

            holder.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences details = mCtx.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

                    String user_name = details.getString("username","");
                    String user_id = details.getString("userid","");

                    SharedPreferences.Editor editor = details.edit();
                    editor.clear();
                    editor.putString("id",Integer.toString(movie.getId()));
                    editor.putString("title",movie.getTitle());
                    editor.putString("desc",movie.getShortdesc());
                    editor.putString("img",movie.getImage());
                    editor.putString("price",movie.getPrice());
                    editor.putString("user_name",user_name);
                    editor.putString("user_id",user_id);
                    editor.apply();
                    mCtx.startActivity(new Intent(mCtx,BookingActivity.class));
                }
            });
        }
        @Override
        public int getItemCount() {
            return movieList.size();
        }

        class MovieViewHolder extends RecyclerView.ViewHolder {

            TextView textViewTitle, textViewShortDesc, textViewRating;
            Button book;
            ImageView imageView;

            private MovieViewHolder(View itemView) {
                super(itemView);

                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
                textViewRating = itemView.findViewById(R.id.textViewRating);
                book = itemView.findViewById(R.id.buttonBook);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }

    }