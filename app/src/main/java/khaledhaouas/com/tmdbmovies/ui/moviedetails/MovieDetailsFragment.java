package khaledhaouas.com.tmdbmovies.ui.moviedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
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
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.utils.Utils;

import static android.view.View.GONE;

//import com.google.android.youtube.player.YouTubePlayerView;

public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "MovieDetailsFragment";

    private int mMovieId = 450465;

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

    private TextView mTxtSynopsis;
    private LinearLayout mLayoutSynopsis;
    private TextView mTxtCast;
    private LinearLayout mLayoutCast;
    private TextView mTxtReviews;
    private TextView mTxtSimilar;
    private LinearLayout mLayoutReviews;
    private LinearLayout mLayoutSimilar;
    private ImageView mImgEmpty;

    private FrameLayout mPlotIndicator;
    private FrameLayout mCastIndicator;
    private FrameLayout mReviewsIndicator;
    private FrameLayout mSimilarIndicator;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        if (getArguments() != null) {
            mMovieId = getArguments().getInt("MovieId");
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        // TODO: Use the ViewModel

        initUIElements();
        initUIEvents();

        mViewModel.getMovieDetails(mMovieId, new OnMovieLoadedCallback() {
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
                mTxtMovieReleaseDate.setText(movie.getReleaseDate());
            }

            @Override
            public void onError() {

            }
        });

        mViewModel.getMovieVideosList(mMovieId, new OnVideoListLoadedCallback() {
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

            mTxtSynopsis = getActivity().findViewById(R.id.txt_synopsis);
            mTxtCast = getActivity().findViewById(R.id.txt_cast);
            mTxtReviews = getActivity().findViewById(R.id.txt_reviews);
            mTxtSimilar = getActivity().findViewById(R.id.txt_similar);
            mLayoutSynopsis = getActivity().findViewById(R.id.layout_overview);
            mLayoutCast = getActivity().findViewById(R.id.layout_cast);
            mLayoutSimilar = getActivity().findViewById(R.id.layout_similar);
            mLayoutReviews = getActivity().findViewById(R.id.layout_reviews);
            mPlotIndicator = getActivity().findViewById(R.id.fr_plot_indicator);
            mCastIndicator = getActivity().findViewById(R.id.fr_cast_indicator);
            mReviewsIndicator = getActivity().findViewById(R.id.fr_reviews_indicator);
            mSimilarIndicator = getActivity().findViewById(R.id.fr_similar_indicator);

            mRVVideosList = getActivity().findViewById(R.id.rv_videos);
            mRVCreditList = getActivity().findViewById(R.id.rv_cast);
            mRVReviewList = getActivity().findViewById(R.id.rv_reviews);
            mRVSimilarMoviesList = getActivity().findViewById(R.id.rv_similar_movies);
            mImgEmpty = getActivity().findViewById(R.id.img_empty);
            Utils.initRatingBar(getActivity(), mRtMovieRating);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {
        mTxtSynopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelectedSection(1);
            }
        });
        mTxtCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getCredits().isEmpty()) {
                    mViewModel.getMovieCreditList(mMovieId, new OnCreditListLoadedCallback() {
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

            }
        });

        mTxtReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getmReviews().isEmpty()) {
                    mViewModel.getMovieReviewList(mMovieId, new OnReviewListLoadedCallback() {
                        @Override
                        public void onSuccess(ArrayList<Review> reviews) {
                            switchSelectedSection(3);
                            if (reviews.isEmpty()) {
                                mImgEmpty.setVisibility(View.VISIBLE);
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
            }
        });

        mTxtSimilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mViewModel.getmSimilarMovies().isEmpty()) {
                    mViewModel.getSimilarMoviesList(mMovieId, new OnMoviesListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<Movie> movies) {
                            switchSelectedSection(4);

                            if (movies.isEmpty()) {
                                mImgEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mRVSimilarMoviesList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
                                mRVSimilarMoviesList.setAdapter(moviesRecyclerViewAdapter);
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

                mPlotIndicator.setVisibility(View.VISIBLE);
                mCastIndicator.setVisibility(GONE);
                mReviewsIndicator.setVisibility(GONE);
                mSimilarIndicator.setVisibility(GONE);

                mTxtSynopsis.setTypeface(mTxtSynopsis.getTypeface(), Typeface.BOLD);
                mTxtCast.setTypeface(Typeface.create(mTxtCast.getTypeface(), Typeface.NORMAL));
                mTxtReviews.setTypeface(Typeface.create(mTxtCast.getTypeface(), Typeface.NORMAL));
                mTxtSimilar.setTypeface(Typeface.create(mTxtCast.getTypeface(), Typeface.NORMAL));

                break;
            case 2:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(GONE);
                mLayoutSimilar.setVisibility(GONE);
                mLayoutCast.setVisibility(View.VISIBLE);

                mPlotIndicator.setVisibility(GONE);
                mCastIndicator.setVisibility(View.VISIBLE);
                mReviewsIndicator.setVisibility(GONE);
                mSimilarIndicator.setVisibility(GONE);

                mTxtSynopsis.setTypeface(Typeface.create(mTxtSynopsis.getTypeface(), Typeface.NORMAL));
                mTxtCast.setTypeface(mTxtCast.getTypeface(), Typeface.BOLD);
                mTxtReviews.setTypeface(Typeface.create(mTxtReviews.getTypeface(), Typeface.NORMAL));
                mTxtSimilar.setTypeface(Typeface.create(mTxtSimilar.getTypeface(), Typeface.NORMAL));
                break;
            case 3:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(View.VISIBLE);
                mLayoutSimilar.setVisibility(GONE);
                mLayoutCast.setVisibility(View.GONE);

                mPlotIndicator.setVisibility(GONE);
                mCastIndicator.setVisibility(GONE);
                mReviewsIndicator.setVisibility(View.VISIBLE);
                mSimilarIndicator.setVisibility(GONE);

                mTxtSynopsis.setTypeface(Typeface.create(mTxtSynopsis.getTypeface(), Typeface.NORMAL));
                mTxtCast.setTypeface(Typeface.create(mTxtCast.getTypeface(), Typeface.NORMAL));
                mTxtReviews.setTypeface(mTxtReviews.getTypeface(), Typeface.BOLD);
                mTxtSimilar.setTypeface(Typeface.create(mTxtSimilar.getTypeface(), Typeface.NORMAL));
                break;
            case 4:
                mLayoutSynopsis.setVisibility(GONE);
                mLayoutReviews.setVisibility(GONE);
                mLayoutSimilar.setVisibility(View.VISIBLE);
                mLayoutCast.setVisibility(View.GONE);

                mPlotIndicator.setVisibility(GONE);
                mCastIndicator.setVisibility(GONE);
                mReviewsIndicator.setVisibility(GONE);
                mSimilarIndicator.setVisibility(View.VISIBLE);

                mTxtSynopsis.setTypeface(Typeface.create(mTxtSynopsis.getTypeface(), Typeface.NORMAL));
                mTxtCast.setTypeface(Typeface.create(mTxtCast.getTypeface(), Typeface.NORMAL));
                mTxtReviews.setTypeface(Typeface.create(mTxtReviews.getTypeface(), Typeface.NORMAL));
                mTxtSimilar.setTypeface(mTxtSimilar.getTypeface(), Typeface.BOLD);
                break;
            default:
                break;
        }
    }


}
