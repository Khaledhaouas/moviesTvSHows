package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
    private TabLayout mTabsMovieListType;

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
        initTabLayout();

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
            mTabsMovieListType = getActivity().findViewById(R.id.tabLayout);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {
        mTabsMovieListType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
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
                        break;
                    case 1:
                        mViewModel.getUpcomingMoviesList(new OnMoviesListLoadedCallback() {
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
                        break;
                    case 2:
                        mViewModel.getNowPlayingMoviesList(new OnMoviesListLoadedCallback() {
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
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabLayout() {
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Popular"));
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Coming Soon"));
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Now Showing"));
    }

}
