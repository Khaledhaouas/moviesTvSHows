package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
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

    private RecyclerView mRVCreditList;

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
        mViewModel.getMovieDetails(483906, new OnMovieLoadedCallback() {
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

            mRVCreditList = getActivity().findViewById(R.id.rv_cast);

            initRatingBar(mRtMovieRating);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {
        mTxtCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getMovieCreditList(483906, new OnCreditListLoadedCallback() {
                    @Override
                    public void onSuccess(ArrayList<Credit> credits) {
                        mLayoutSynopsis.setVisibility(GONE);
                        GridLayoutManager m = new GridLayoutManager(getActivity(), Utils.calculateNoOfColumns(getActivity()) + 1);
                        mRVCreditList.setLayoutManager(m);
                        mRVCreditList.setAdapter(new CreditsRecyclerViewAdapter(getActivity(), credits));
                    }

                    @Override
                    public void onError() {

                    }
                });
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


}
