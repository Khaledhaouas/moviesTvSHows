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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.adapters.MoviesRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.ui.moviedetails.MovieDetailsFragment;
import khaledhaouas.com.tmdbmovies.utils.Utils;

public class MovieListFragment extends Fragment {
    private static final String TAG = "TvShowListFragment";

    private MovieListViewModel mViewModel;

    //UI Elements
    private RecyclerView mRVMoviesList;
    private TabLayout mTabsMovieListType;
    private EditText mEdtSearchMovies;
    private RelativeLayout mLayoutSearch;
    private ViewGroup mLayoutSearchParent;
    private ImageView mImgSearch;
    private ImageView mImgHideSearch;
    private boolean isUp = false;
    private LinearLayoutManager layoutManager;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;

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

    }

    private void initUIElements() {
        try {
            ((TextView)getActivity().findViewById(R.id.txt_section_name)).setText("Movies");

            mRVMoviesList = getActivity().findViewById(R.id.rv_similar_movies);
            mTabsMovieListType = getActivity().findViewById(R.id.tabLayout);
            mEdtSearchMovies = getActivity().findViewById(R.id.edt_search_text);
            mLayoutSearch = getActivity().findViewById(R.id.layout_search);
            mImgSearch = getActivity().findViewById(R.id.btn_search);
            mLayoutSearchParent = (ViewGroup) mLayoutSearch.getParent();
            mImgHideSearch = getActivity().findViewById(R.id.img_hide_search);

            //Recycler View Setup
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRVMoviesList.setLayoutManager(layoutManager);
            mRVMoviesList.setItemAnimator(new DefaultItemAnimator());

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
                }
            }
        });

        mImgHideSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchTabs(mTabsMovieListType.getSelectedTabPosition());

                slideDown(mLayoutSearch);
                Utils.hideKeyboard(getActivity());
                isUp = false;
            }
        });

        mTabsMovieListType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTabs(tab.getPosition());
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
                    final CharSequence finalS = s;
                    mViewModel.getSearchResultMoviesList(s.toString(), new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies, int totalPages) {
//                            Log.e("Search", movies.toString() );
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            mRVMoviesList.setLayoutManager(layoutManager);
                            mRVMoviesList.setItemAnimator(new DefaultItemAnimator());
                            final MoviesRecyclerViewAdapter moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mViewModel.getSearchedMovies());
                            moviesRecyclerViewAdapter.setClickListener(new MoviesRecyclerViewAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
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
                            mRVMoviesList.clearOnScrollListeners();
                            mRVMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                                    int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                                    if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentSearchedMoviesPage() != mViewModel.getTotalSearchedMoviesPage()) {
                                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                                && firstVisibleItemPosition >= 0
                                                && totalItemCount >= 20 * mViewModel.getCurrentUpcomingMoviesPage()) {

                                            mViewModel.getNextPageSearchedMoviesList(finalS.toString(), new OnMoviesListLoadedCallback() {
                                                @Override
                                                public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                                                    moviesRecyclerViewAdapter.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onError() {

                                                }
                                            });
                                        }
                                    }
                                }
                            });
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

    private void switchTabs(int tabPos) {
        switch (tabPos) {
            case 0:
                moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mViewModel.getPopularMovies());
                moviesRecyclerViewAdapter.setClickListener(new MoviesRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("MovieId", mViewModel.getPopularMovies().get(position).getId());
                        movieDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, movieDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                mRVMoviesList.setAdapter(moviesRecyclerViewAdapter);
                mRVMoviesList.clearOnScrollListeners();
                mRVMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentPopularMoviesPage() != mViewModel.getTotalPopularMoviesPage()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0
                                    && totalItemCount >= 20 * mViewModel.getCurrentPopularMoviesPage()) {

                                mViewModel.getNextPagePopularMoviesList(new OnMoviesListLoadedCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                                        moviesRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        }
                    }
                });

                if (mViewModel.getPopularMovies().isEmpty()) {
                    mViewModel.getPopularMoviesList(new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies, int totalPages) {
                            moviesRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                break;
            case 1:
                moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mViewModel.getUpcomingMovies());
                moviesRecyclerViewAdapter.setClickListener(new MoviesRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("MovieId", mViewModel.getUpcomingMovies().get(position).getId());
                        movieDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, movieDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                mRVMoviesList.setAdapter(moviesRecyclerViewAdapter);
                mRVMoviesList.clearOnScrollListeners();
                mRVMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentUpcomingMoviesPage() != mViewModel.getTotalUpcomingMoviesPage()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0
                                    && totalItemCount >= 20 * mViewModel.getCurrentUpcomingMoviesPage()) {

                                mViewModel.getNextPageUpcomingMoviesList(new OnMoviesListLoadedCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                                        moviesRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        }
                    }
                });

                if (mViewModel.getUpcomingMovies().isEmpty()) {
                    mViewModel.getUpcomingMoviesList(new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies, int totalPages) {
                            moviesRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                break;
            case 2:
                moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mViewModel.getNowPlayingMovies());
                moviesRecyclerViewAdapter.setClickListener(new MoviesRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("MovieId", mViewModel.getNowPlayingMovies().get(position).getId());
                        movieDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, movieDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                mRVMoviesList.setAdapter(moviesRecyclerViewAdapter);
                mRVMoviesList.clearOnScrollListeners();
                mRVMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentNowPlayingMoviesPage() != mViewModel.getTotalNowPlayingMoviesPage()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0
                                    && totalItemCount >= 20 * mViewModel.getCurrentNowPlayingMoviesPage()) {

                                mViewModel.getNextPageNowPlayingMoviesList(new OnMoviesListLoadedCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                                        moviesRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        }
                    }
                });
                if (mViewModel.getNowPlayingMovies().isEmpty()) {
                    mViewModel.getNowPlayingMoviesList(new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies, int totalPages) {
                            moviesRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                break;
            default:
                break;
        }
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
