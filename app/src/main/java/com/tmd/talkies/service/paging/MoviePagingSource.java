package com.tmd.talkies.service.paging;

import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;
import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.service.model.MovieResponse;
import com.tmd.talkies.service.network.APIClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {

    @NotNull
    @Override
    public Single<PagingSource.LoadResult<Integer, Movie>> loadSingle(@NotNull PagingSource.LoadParams<Integer> loadParams) {
        try {
            // If page number is already there then init page variable with it otherwise we are loading fist page
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            // Send request to server with page number
            return APIClient.getAPIInterface()
                    .getMoviesByPage(page)
                    // Subscribe the result
                    .subscribeOn(Schedulers.io())
                    // Map result top List of movies
                    .map(MovieResponse::getResults)
                    // Map result to LoadResult Object
                    .map(movies -> toLoadResult(movies, page))
                    // when error is there return error
                    .onErrorReturn(PagingSource.LoadResult.Error::new);
        } catch (Exception e) {
            // Request ran into error return error
            return Single.just(new PagingSource.LoadResult.Error(e));
        }
    }

    // Method to map Movies to LoadResult object
    private PagingSource.LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page) {
        return new PagingSource.LoadResult.Page(movies, page == 1 ? null : page - 1, page + 1);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, Movie> pagingState) {
        return null;
    }
}
