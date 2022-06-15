package com.tmd.talkies.view.main;


import static com.tmd.talkies.utils.AppConstants.CDN_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.tmd.talkies.databinding.ItemMovieBinding;
import com.tmd.talkies.service.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali Asadi on 24/03/2018.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface MovieListener {
        void onMovieClicked(Movie movie);
    }

    private List<Movie> items;
    private final MovieListener listener;

    public MovieAdapter(MovieListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    public void setItems(List<Movie> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private Movie getItem(int position) {
        return items.get(position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemMovieBinding binding;
        MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            Movie movie = getItem(position);
            setClickListener(movie);
            setTitle(movie.getTitle());
            setImage(CDN_URL+movie.getPosterPath());
            setReleaseDate(movie.getReleaseDate());
        }

        private void setTitle(String title) {
            binding.title.setText(title);
        }

        private void setImage(String imageUrl) {
            Glide.with(itemView.getContext()).load(imageUrl).into(binding.image);
        }

        private void setReleaseDate(String releaseDate) {
            binding.releaseDate.setText(releaseDate);
        }

        private void setClickListener(Movie movie) {
            itemView.setTag(movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onMovieClicked((Movie) view.getTag());
        }
    }
}