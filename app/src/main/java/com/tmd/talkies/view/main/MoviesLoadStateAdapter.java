package com.tmd.talkies.view.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tmd.talkies.R;
import com.tmd.talkies.databinding.LoadStateItemBinding;

public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder> {

    // Define Retry Callback
    private View.OnClickListener mRetryCallback;

    public MoviesLoadStateAdapter(View.OnClickListener mRetryCallback) {
        this.mRetryCallback = mRetryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder holder, @NonNull LoadState loadState) {

        // Call Bind Method to bind visibility  of views
        holder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        // Return new LoadStateViewHolder object
        return new LoadStateViewHolder(parent, mRetryCallback);
    }

    public class LoadStateViewHolder extends RecyclerView.ViewHolder {
        // Define Progress bar
        private ProgressBar mProgressBar;
        // Define error TextView
        private TextView mErrorMsg;
        // Define Retry button
        private Button mRetry;

        public LoadStateViewHolder( @NonNull ViewGroup parent,
                                    @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_state_item, parent, false));
            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            mProgressBar = binding.progressBar;
            mErrorMsg = binding.errorMsg;
            mRetry = binding.retryButton;
            mRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            // Check load state
            if (loadState instanceof LoadState.Error) {
                // Get the error
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                // Set text of Error message
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            // set visibility of widgets based on LoadState
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        }
    }
}
