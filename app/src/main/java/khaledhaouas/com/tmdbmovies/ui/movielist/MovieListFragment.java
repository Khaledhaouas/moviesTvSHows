package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private EditText mEdtSearchMovies;
    private LinearLayout mLayoutSearch;
    private ViewGroup mLayoutSearchParent;
    private ImageView mImgSearch;
    private boolean isUp = false;

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
                mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
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
                                .addToBackStack(null)
                                .commit();
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
            mEdtSearchMovies = getActivity().findViewById(R.id.edt_search_text);
            mLayoutSearch = getActivity().findViewById(R.id.layout_search);
            mImgSearch = getActivity().findViewById(R.id.btn_search);
            mLayoutSearchParent = (ViewGroup) mLayoutSearch.getParent();

            slideDown(mLayoutSearch);
            isUp = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {

        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayoutSearch.getVisibility() == View.INVISIBLE) {
                    mLayoutSearch.setVisibility(View.VISIBLE);
                }
                if (!isUp) {
                    isUp = true;
                    slideUp(mLayoutSearch);

                } else {
                    slideDown(mLayoutSearch);
                    isUp = false;

                }
            }
        });

        mTabsMovieListType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mViewModel.getPopularMoviesList(new OnMoviesListLoadedCallback() {
                            @Override
                            public void onSuccess(final ArrayList<Movie> movies) {

                                mRVMoviesList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
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
                                                .addToBackStack(null)
                                                .commit();
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
                                mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
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
                                                .addToBackStack(null)
                                                .commit();
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
                                mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
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
                                                .addToBackStack(null)
                                                .commit();
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

        mEdtSearchMovies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mViewModel.getSearchResultMoviesList(s.toString(), new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies) {
//                            Log.e("Search", movies.toString() );
                            mRVMoviesList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
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
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                            mRVMoviesList.setAdapter(moviesRecyclerViewAdapter);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initTabLayout() {
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Popular"));
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Coming Soon"));
        mTabsMovieListType.addTab(mTabsMovieListType.newTab().setText("Now Showing"));
    }

    public void slideUp(final View view) {
//        view.setVisibility(View.VISIBLE);
        mLayoutSearchParent.addView(view);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                mLayoutSearchParent.addView(view);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    // slide the view from its current position to below itself
    public void slideDown(final View view) {
        ((ViewGroup) view.getParent()).removeView(view);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
//                Log.e(TAG, "onAnimationEnd: "+view.getVisibility() );
//                ((ViewGroup) view.getParent()).removeView(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
