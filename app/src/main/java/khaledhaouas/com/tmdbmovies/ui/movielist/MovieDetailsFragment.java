package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.adapters.CreditsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.ReviewsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.utils.Utils;

import static android.view.View.GONE;

public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "MovieDetailsFragment";

    private MovieDetailsViewModel mViewModel;

    //UI elements
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

    private FrameLayout mPlotIndicator;
    private FrameLayout mCastIndicator;
    private FrameLayout mReviewsIndicator;
    private FrameLayout mSimilarIndicator;

    private RecyclerView mRVCreditList;
    private RecyclerView mRVReviewList;

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
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        // TODO: Use the ViewModel

        initUIElements();
        initUIEvents();
        mViewModel.getMovieDetails(424783, new OnMovieLoadedCallback() {
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


    }

    private void initUIElements() {
        try {
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

            mRVCreditList = getActivity().findViewById(R.id.rv_cast);
            mRVReviewList = getActivity().findViewById(R.id.rv_reviews);

            initRatingBar(mRtMovieRating);


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
                    mViewModel.getMovieCreditList(424783, new OnCreditListLoadedCallback() {
                        @Override
                        public void onSuccess(ArrayList<Credit> credits) {
                            switchSelectedSection(2);
                            GridLayoutManager m = new GridLayoutManager(getActivity(), Utils.calculateNoOfColumns(getActivity()) + 1);
                            mRVCreditList.setLayoutManager(m);
                            mRVCreditList.setAdapter(new CreditsRecyclerViewAdapter(getActivity(), credits));
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
                if (mViewModel.getCredits().isEmpty()) {
                    mViewModel.getMovieReviewList(297802, new OnReviewListLoadedCallback() {
                        @Override
                        public void onSuccess(ArrayList<Review> reviews) {
                            switchSelectedSection(3);
//                            GridLayoutManager m = new GridLayoutManager(getActivity(), Utils.calculateNoOfColumns(getActivity()) + 1);
                            mRVReviewList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL ,false));
                            mRVReviewList.setAdapter(new ReviewsRecyclerViewAdapter(getActivity(), reviews));
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
                switchSelectedSection(4);
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

    private void initRatingBar(RatingBar rating) {
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        //Color filled stars
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        //Color half filled stars
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        //Color empty stars
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey), PorterDuff.Mode.SRC_IN);
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
                mTxtCast.setTypeface(mTxtCast.getTypeface(), Typeface.NORMAL);
                mTxtReviews.setTypeface(mTxtReviews.getTypeface(), Typeface.NORMAL);
                mTxtSimilar.setTypeface(mTxtSimilar.getTypeface(), Typeface.NORMAL);

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

                mTxtSynopsis.setTypeface(mTxtSynopsis.getTypeface(), Typeface.NORMAL);
                mTxtCast.setTypeface(mTxtCast.getTypeface(), Typeface.BOLD);
                mTxtReviews.setTypeface(mTxtReviews.getTypeface(), Typeface.NORMAL);
                mTxtSimilar.setTypeface(mTxtSimilar.getTypeface(), Typeface.NORMAL);
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

                mTxtSynopsis.setTypeface(mTxtSynopsis.getTypeface(), Typeface.NORMAL);
                mTxtCast.setTypeface(mTxtCast.getTypeface(), Typeface.NORMAL);
                mTxtReviews.setTypeface(mTxtReviews.getTypeface(), Typeface.BOLD);
                mTxtSimilar.setTypeface(mTxtSimilar.getTypeface(), Typeface.NORMAL);
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

                mTxtSynopsis.setTypeface(mTxtSynopsis.getTypeface(), Typeface.NORMAL);
                mTxtCast.setTypeface(mTxtCast.getTypeface(), Typeface.NORMAL);
                mTxtReviews.setTypeface(mTxtReviews.getTypeface(), Typeface.NORMAL);
                mTxtSimilar.setTypeface(mTxtSimilar.getTypeface(), Typeface.BOLD);
                break;
            default:
                break;
        }
    }


}
