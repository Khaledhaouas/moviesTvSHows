package khaledhaouas.com.tmdbmovies.ui.moviedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.adapters.CreditsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.MoviesRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.ReviewsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.VideosRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.utils.Utils;

import static android.view.View.GONE;

//import com.google.android.youtube.player.YouTubePlayerView;

public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "TvShowDetailsFragment";

    private MovieDetailsViewModel mViewModel;

    //UI elements
    private ScrollView mScrollViewMainContent;

    private ImageView mImgMovieBackground;
    private ImageView mImgMoviePoster;
    private TextView mTxtMovieTitle;
    private TextView mTxtMovieGenres;
    private RatingBar mRtMovieRating;
    private TextView mTxtMovieReviewNbrs;
    private TextView mTxtMovieReleaseDate;
    private TextView mTxtMovieRunTime;
    private TextView mTxtMovieLang;
    private TextView mTxtMoviePlot;

    private TabLayout mTabsMovieType;

    private LinearLayout mLayoutSynopsis;
    private LinearLayout mLayoutCast;
    private LinearLayout mLayoutReviews;
    private LinearLayout mLayoutSimilar;

    private ImageView mImgEmptyReviews;
    private ImageView mImgEmptySimilar;

    private RecyclerView mRVVideosList;
    private RecyclerView mRVCreditList;
    private RecyclerView mRVReviewList;
    private RecyclerView mRVSimilarMoviesList;


    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.movie_details_fragment, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        // TODO: Use the ViewModel
        if (getArguments() != null) {
            mViewModel.setMovieId(getArguments().getInt("MovieId"));
        }

        initUIElements();
        initUIEvents();
        initTabLayout();

        mViewModel.getMovieDetails(new OnMovieLoadedCallback() {
            @Override
            public void onSuccess(Movie movie) {

                loadImageFromURL(mImgMovieBackground, movie.getBackgroundImageUrl());
                loadRoundedImageFromURL(mImgMoviePoster, movie.getPosterImageUrl());
                mTxtMovieTitle.setText(movie.getTitle());
                mTxtMovieGenres.setText(movie.getGenres());
                mRtMovieRating.setRating((float) movie.getRating() / 2);
                mTxtMovieReviewNbrs.setText(movie.getReviewNbrs() + "");
                mTxtMoviePlot.setText(movie.getPlot());
                mTxtMovieRunTime.setText(Utils.formatTimeFromMinutes(movie.getRunTime()));
                mTxtMovieLang.setText(Utils.getCountryFromCode(movie.getLanguage()));
                mTxtMovieReleaseDate.setText(Utils.formatDate(movie.getReleaseDate()));
            }

            @Override
            public void onError() {

            }
        });

        mViewModel.getMovieVideosList(new OnVideoListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Video> videos) {
                mRVVideosList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mRVVideosList.setAdapter(new VideosRecyclerViewAdapter(getActivity(), videos));
            }

            @Override
            public void onError() {

            }
        });

    }

    private void initUIElements() {
        try {
            mScrollViewMainContent = getActivity().findViewById(R.id.scroll_movie_details);

            mImgMovieBackground = getActivity().findViewById(R.id.img_movie_background);
            mImgMoviePoster = getActivity().findViewById(R.id.img_movie_poster);
            mTxtMovieTitle = getActivity().findViewById(R.id.txt_movie_title);
            mTxtMovieGenres = getActivity().findViewById(R.id.txt_movie_genres);
            mTxtMovieReviewNbrs = getActivity().findViewById(R.id.txt_movie_reviews_nbre);
            mTxtMovieReleaseDate = getActivity().findViewById(R.id.txt_movie_release_date);
            mTxtMovieRunTime = getActivity().findViewById(R.id.txt_movie_runtime);
            mTxtMovieLang = getActivity().findViewById(R.id.txt_movie_language);
            mTxtMoviePlot = getActivity().findViewById(R.id.txt_movie_plot);
            mRtMovieRating = getActivity().findViewById(R.id.txt_movie_rating);

            mLayoutSynopsis = getActivity().findViewById(R.id.layout_overview);
            mLayoutCast = getActivity().findViewById(R.id.layout_cast);
            mLayoutSimilar = getActivity().findViewById(R.id.layout_similar);
            mLayoutReviews = getActivity().findViewById(R.id.layout_reviews);

            mRVVideosList = getActivity().findViewById(R.id.rv_videos);
            mRVCreditList = getActivity().findViewById(R.id.rv_cast);
            mRVReviewList = getActivity().findViewById(R.id.rv_reviews);
            mRVSimilarMoviesList = getActivity().findViewById(R.id.rv_similar_movies);
            mImgEmptyReviews = getActivity().findViewById(R.id.img_empty_review);
            mImgEmptySimilar = getActivity().findViewById(R.id.img_empty_similar);
            Utils.initRatingBar(getActivity(), mRtMovieRating);

            mTabsMovieType = getActivity().findViewById(R.id.tabLayout);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {

        mTabsMovieType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSelectedSection(1);
                        break;
                    case 1:
                        if (mViewModel.getCredits().isEmpty()) {
                            mViewModel.getMovieCreditList(new OnCreditListLoadedCallback() {
                                @Override
                                public void onSuccess(ArrayList<Credit> credits) {
                                    switchSelectedSection(2);
                                    GridLayoutManager m = new GridLayoutManager(getActivity(), Utils.calculateNoOfColumns(getActivity()) + 1);
                                    mRVCreditList.setLayoutManager(m);
                                    mRVCreditList.setAdapter(new CreditsRecyclerViewAdapter(getActivity(), credits));
                                    mScrollViewMainContent.fullScroll(View.FOCUS_UP);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                        } else {
                            switchSelectedSection(2);
                        }
                        break;
                    case 2:
                        if (mViewModel.getmReviews().isEmpty()) {
                            mViewModel.getMovieReviewList(new OnReviewListLoadedCallback() {
                                @Override
                                public void onSuccess(ArrayList<Review> reviews) {
                                    switchSelectedSection(3);
                                    if (reviews.isEmpty()) {
                                        mImgEmptyReviews.setVisibility(View.VISIBLE);
                                    } else {
                                        mRVReviewList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                        mRVReviewList.setAdapter(new ReviewsRecyclerViewAdapter(getActivity(), reviews));
                                    }
                                    mScrollViewMainContent.fullScroll(View.FOCUS_UP);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                        } else {
                            switchSelectedSection(3);
                        }
                        break;
                    case 3:
                        if (mViewModel.getmSimilarMovies().isEmpty()) {
                            mViewModel.getSimilarMoviesList(new OnMoviesListLoadedCallback() {
                                @Override
                                public void onSuccess(final ArrayList<Movie> movies, int totalPages) {
                                    switchSelectedSection(4);

                                    if (movies.isEmpty()) {
                                        mImgEmptySimilar.setVisibility(View.VISIBLE);
                                    } else {
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                        mRVSimilarMoviesList.setLayoutManager(layoutManager);
                                        final MoviesRecyclerViewAdapter moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mViewModel.getmSimilarMovies());
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
                                        mRVSimilarMoviesList.setAdapter(moviesRecyclerViewAdapter);
//                                        mRVSimilarMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                                            @Override
//                                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                                super.onScrollStateChanged(recyclerView, newState);
//                                            }
//
//                                            @Override
//                                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                                super.onScrolled(recyclerView, dx, dy);
//                                                int visibleItemCount = layoutManager.getChildCount();
//                                                int totalItemCount = layoutManager.getItemCount();
//                                                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//
//                                                if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentSimilarMoviesPage() != mViewModel.getTotalSimilarMoviesPage()) {
//                                                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                                                            && firstVisibleItemPosition >= 0
//                                                            && totalItemCount >= 20 * mViewModel.getCurrentSimilarMoviesPage()) {
//
//                                                        mViewModel.getNextPageSimilarMoviesList(mViewModel.getMovieId(), new OnMoviesListLoadedCallback() {
//                                                            @Override
//                                                            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
//                                                                moviesRecyclerViewAdapter.notifyDataSetChanged();
//                                                            }
//
//                                                            @Override
//                                                            public void onError() {
//
//                                                            }
//                                                        });
//                                                    }
//                                                }
//                                            }
//                                        });

                                    }
                                    mScrollViewMainContent.fullScroll(View.FOCUS_UP);


                                }

                                @Override
                                public void onError() {

                                }
                            });
                        } else {
                            switchSelectedSection(4);
                        }
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

    private void loadRoundedImageFromURL(ImageView imgView, String url) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .apply(new RequestOptions().placeholder(R.drawable.movie_poster_placeholder))
                .into(imgView);
    }

    private void loadImageFromURL(ImageView imgView, String url) {
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.movie_background_placeholder))
                .into(imgView);
    }


    private void switchSelectedSection(int sectionId) {
        switch (sectionId) {
            case 1:
                mLayoutSynopsis.setVisibility(View.VISIBLE);
                mLayoutReviews.setVisibility(GONE);
                mLayoutSimilar.setVisibility(GONE);
                mLayoutCast.setVisibility(View.GONE);

                break;
            case 2:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(GONE);
                mLayoutSimilar.setVisibility(GONE);
                mLayoutCast.setVisibility(View.VISIBLE);

                break;
            case 3:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(View.VISIBLE);
                mLayoutSimilar.setVisibility(GONE);
                mLayoutCast.setVisibility(View.GONE);

                break;
            case 4:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(GONE);
                mLayoutSimilar.setVisibility(View.VISIBLE);
                mLayoutCast.setVisibility(View.GONE);

                break;
            default:
                break;
        }
    }

    private void initTabLayout() {
        mTabsMovieType.addTab(mTabsMovieType.newTab().setText("Plot"));
        mTabsMovieType.addTab(mTabsMovieType.newTab().setText("Cast"));
        mTabsMovieType.addTab(mTabsMovieType.newTab().setText("Reviews"));
        mTabsMovieType.addTab(mTabsMovieType.newTab().setText("Similar"));
    }


}
