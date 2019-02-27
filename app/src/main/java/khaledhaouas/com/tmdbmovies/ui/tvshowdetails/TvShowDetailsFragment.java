package khaledhaouas.com.tmdbmovies.ui.tvshowdetails;

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
import khaledhaouas.com.tmdbmovies.adapters.ReviewsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.SeasonsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.TvShowsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.adapters.VideosRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.utils.Utils;

import static android.view.View.GONE;

//import com.google.android.youtube.player.YouTubePlayerView;

public class TvShowDetailsFragment extends Fragment {
    private static final String TAG = "TvShowDetailsFragment";

    private TvShowDetailsViewModel mViewModel;

    //UI elements
    private ScrollView mScrollViewMainContent;

    private ImageView mImgTvShowBackground;
    private ImageView mImgTvShowPoster;
    private TextView mTxtTvShowTitle;
    private TextView mTxtTvShowGenres;
    private RatingBar mRtTvShowRating;
    private TextView mTxtTvShowReviewNbrs;
    private TextView mTxtTvShowReleaseDate;
    private TextView mTxtTvShowRunTime;
    private TextView mTxtTvShowLang;
    private TextView mTxtTvShowPlot;

    private TabLayout mTabsTvShowType;

    private LinearLayout mLayoutSynopsis;
    private LinearLayout mLayoutCast;
    private LinearLayout mLayoutReviews;
    private LinearLayout mLayoutSimilar;

    private ImageView mImgEmptyReviews;
    private ImageView mImgEmptySimilar;

    private RecyclerView mRVSeasonsList;
    private RecyclerView mRVVideosList;
    private RecyclerView mRVCreditList;
    private RecyclerView mRVReviewList;
    private RecyclerView mRVSimilarTvShowsList;

    private SeasonsRecyclerViewAdapter mSeasonsRecyclerViewAdapter;


    public static TvShowDetailsFragment newInstance() {
        return new TvShowDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tv_show_details_fragment, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TvShowDetailsViewModel.class);
        // TODO: Use the ViewModel
        if (getArguments() != null) {
            mViewModel.setTvShowId(getArguments().getInt("TvShowId"));
        }

        initUIElements();
        initUIEvents();
        initTabLayout();

        mViewModel.getTvShowDetails(new OnTvShowLoadedCallback() {
            @Override
            public void onSuccess(TvShow tvShow) {

                loadImageFromURL(mImgTvShowBackground, tvShow.getBackgroundImageUrl());
                loadRoundedImageFromURL(mImgTvShowPoster, tvShow.getPosterImageUrl());
                mTxtTvShowTitle.setText(tvShow.getTitle());
                mTxtTvShowGenres.setText(tvShow.getGenres());
                mRtTvShowRating.setRating((float) tvShow.getRating() / 2);
                mTxtTvShowReviewNbrs.setText(tvShow.getReviewNbrs() + "");
                mTxtTvShowPlot.setText(tvShow.getPlot());
                mTxtTvShowRunTime.setText(tvShow.getSeasonNbre() + "");
                mTxtTvShowLang.setText(Utils.getCountryFromCode(tvShow.getLanguage()));
                mTxtTvShowReleaseDate.setText(Utils.formatDate(tvShow.getFirstEpDate()));
                mRVSeasonsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mSeasonsRecyclerViewAdapter = new SeasonsRecyclerViewAdapter(getActivity(), tvShow.getSeasons());
                mRVSeasonsList.setAdapter(mSeasonsRecyclerViewAdapter);

            }

            @Override
            public void onError() {

            }
        });

        mViewModel.getTvShowVideosList(new OnVideoListLoadedCallback() {
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

            mImgTvShowBackground = getActivity().findViewById(R.id.img_movie_background);
            mImgTvShowPoster = getActivity().findViewById(R.id.img_movie_poster);
            mTxtTvShowTitle = getActivity().findViewById(R.id.txt_movie_title);
            mTxtTvShowGenres = getActivity().findViewById(R.id.txt_movie_genres);
            mTxtTvShowReviewNbrs = getActivity().findViewById(R.id.txt_movie_reviews_nbre);
            mTxtTvShowReleaseDate = getActivity().findViewById(R.id.txt_movie_release_date);
            mTxtTvShowRunTime = getActivity().findViewById(R.id.txt_movie_runtime);
            mTxtTvShowLang = getActivity().findViewById(R.id.txt_movie_language);
            mTxtTvShowPlot = getActivity().findViewById(R.id.txt_movie_plot);
            mRtTvShowRating = getActivity().findViewById(R.id.txt_movie_rating);

            mLayoutSynopsis = getActivity().findViewById(R.id.layout_overview);
            mLayoutCast = getActivity().findViewById(R.id.layout_cast);
            mLayoutSimilar = getActivity().findViewById(R.id.layout_similar);
            mLayoutReviews = getActivity().findViewById(R.id.layout_reviews);

            mRVSeasonsList = getActivity().findViewById(R.id.rv_seasons);
            mRVVideosList = getActivity().findViewById(R.id.rv_videos);
            mRVCreditList = getActivity().findViewById(R.id.rv_cast);
            mRVReviewList = getActivity().findViewById(R.id.rv_reviews);
            mRVSimilarTvShowsList = getActivity().findViewById(R.id.rv_similar_movies);
            mImgEmptyReviews = getActivity().findViewById(R.id.img_empty_review);
            mImgEmptySimilar = getActivity().findViewById(R.id.img_empty_similar);
            Utils.initRatingBar(getActivity(), mRtTvShowRating);

            mTabsTvShowType = getActivity().findViewById(R.id.tabLayout);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUIEvents() {

        mTabsTvShowType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSelectedSection(1);
                        break;
                    case 1:
                        if (mViewModel.getCredits().isEmpty()) {
                            mViewModel.getTvShowCreditList(new OnCreditListLoadedCallback() {
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
                            mViewModel.getTvShowReviewList(new OnReviewListLoadedCallback() {
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
                        if (mViewModel.getmSimilarTvShows().isEmpty()) {
                            mViewModel.getSimilarTvShowsList(new OnTvShowListLoadedCallback() {
                                @Override
                                public void onSuccess(final ArrayList<TvShow> tvShows, int totalPages) {
                                    switchSelectedSection(4);

                                    if (tvShows.isEmpty()) {
                                        mImgEmptySimilar.setVisibility(View.VISIBLE);
                                    } else {
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                        mRVSimilarTvShowsList.setLayoutManager(layoutManager);
                                        final TvShowsRecyclerViewAdapter tvShowsRecyclerViewAdapter = new TvShowsRecyclerViewAdapter(getActivity(), mViewModel.getmSimilarTvShows());
                                        tvShowsRecyclerViewAdapter.setClickListener(new TvShowsRecyclerViewAdapter.ItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                Log.e(TAG, "onItemClick: " + tvShows.get(position).getTitle());
                                                TvShowDetailsFragment tvShowDetailsFragment = TvShowDetailsFragment.newInstance();
                                                Bundle args = new Bundle();
                                                args.putInt("TvShowId", tvShows.get(position).getId());
                                                tvShowDetailsFragment.setArguments(args);
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.container, tvShowDetailsFragment)
                                                        .addToBackStack(null)
                                                        .commit();
                                            }
                                        });
                                        mRVSimilarTvShowsList.setAdapter(tvShowsRecyclerViewAdapter);
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
        mTabsTvShowType.addTab(mTabsTvShowType.newTab().setText("Plot"));
        mTabsTvShowType.addTab(mTabsTvShowType.newTab().setText("Cast"));
        mTabsTvShowType.addTab(mTabsTvShowType.newTab().setText("Reviews"));
        mTabsTvShowType.addTab(mTabsTvShowType.newTab().setText("Similar"));
    }


}
