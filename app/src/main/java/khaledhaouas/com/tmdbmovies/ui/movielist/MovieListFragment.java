package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.adapters.MoviesRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.ui.moviedetails.MovieDetailsFragment;

public class MovieListFragment extends Fragment {
    private static final String TAG = "MovieListFragment";

    private MovieListViewModel mViewModel;

    //UI Elements
    private RecyclerView mRVMoviesList;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);

        initUIElements();
        initUIEvents();

        mViewModel.getPopularMoviesList(new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(final ArrayList<Movie> movies) {

                mRVMoviesList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                MoviesRecyclerViewAdapter moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), movies);
                moviesRecyclerViewAdapter.setClickListener(new MoviesRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e(TAG, "onItemClick: " + movies.get(position).getTitle());
                        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("MovieId", movies.get(position).getId());
                        movieDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, movieDetailsFragment)
                                .commitNow();
                    }
                });
                mRVMoviesList.setAdapter(moviesRecyclerViewAdapter);

            }

            @Override
            public void onError() {

            }
        });
    }

    private void initUIElements() {
        try {

            mRVMoviesList = getActivity().findViewById(R.id.rv_similar_movies);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {

    }

}
