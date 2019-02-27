package khaledhaouas.com.tmdbmovies.ui.tvshowlist;

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
import khaledhaouas.com.tmdbmovies.adapters.TvShowsRecyclerViewAdapter;
import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowListLoadedCallback;
import khaledhaouas.com.tmdbmovies.ui.tvshowdetails.TvShowDetailsFragment;
import khaledhaouas.com.tmdbmovies.utils.Utils;

public class TvShowListFragment extends Fragment {
    private static final String TAG = "TvShowListFragment";

    private TvShowListViewModel mViewModel;

    //UI Elements
    private RecyclerView mRVTvShowsList;
    private TabLayout mTabsTvShowListType;
    private EditText mEdtSearchTvShows;
    private RelativeLayout mLayoutSearch;
    private ViewGroup mLayoutSearchParent;
    private ImageView mImgSearch;
    private ImageView mImgHideSearch;
    private boolean isUp = false;
    private LinearLayoutManager layoutManager;
    private TvShowsRecyclerViewAdapter tvShowsRecyclerViewAdapter;

    public static TvShowListFragment newInstance() {
        return new TvShowListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TvShowListViewModel.class);

        initUIElements();
        initUIEvents();
        initTabLayout();

    }

    private void initUIElements() {
        try {

            ((TextView)getActivity().findViewById(R.id.txt_section_name)).setText("Tv Shows");

            mRVTvShowsList = getActivity().findViewById(R.id.rv_similar_movies);
            mTabsTvShowListType = getActivity().findViewById(R.id.tabLayout);
            mEdtSearchTvShows = getActivity().findViewById(R.id.edt_search_text);
            mLayoutSearch = getActivity().findViewById(R.id.layout_search);
            mImgSearch = getActivity().findViewById(R.id.btn_search);
            mLayoutSearchParent = (ViewGroup) mLayoutSearch.getParent();
            mImgHideSearch = getActivity().findViewById(R.id.img_hide_search);

            //Recycler View Setup
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRVTvShowsList.setLayoutManager(layoutManager);
            mRVTvShowsList.setItemAnimator(new DefaultItemAnimator());

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

                switchTabs(mTabsTvShowListType.getSelectedTabPosition());

                slideDown(mLayoutSearch);
                Utils.hideKeyboard(getActivity());
                isUp = false;
            }
        });

        mTabsTvShowListType.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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

        mEdtSearchTvShows.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    final CharSequence finalS = s;
                    mViewModel.getSearchResultTvShowsList(s.toString(), new OnTvShowListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<TvShow> TvShows, int totalPages) {
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            mRVTvShowsList.setLayoutManager(layoutManager);
                            mRVTvShowsList.setItemAnimator(new DefaultItemAnimator());
                            final TvShowsRecyclerViewAdapter TvShowsRecyclerViewAdapter = new TvShowsRecyclerViewAdapter(getActivity(), mViewModel.getSearchedTvShows());
                            TvShowsRecyclerViewAdapter.setClickListener(new TvShowsRecyclerViewAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    TvShowDetailsFragment tvShowDetailsFragment = TvShowDetailsFragment.newInstance();
                                    Bundle args = new Bundle();
                                    args.putInt("TvShowId", TvShows.get(position).getId());
                                    tvShowDetailsFragment.setArguments(args);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, tvShowDetailsFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                            mRVTvShowsList.setAdapter(TvShowsRecyclerViewAdapter);
                            mRVTvShowsList.clearOnScrollListeners();
                            mRVTvShowsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                    if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentSearchedTvShowsPage() != mViewModel.getTotalSearchedTvShowsPage()) {
                                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                                && firstVisibleItemPosition >= 0
                                                && totalItemCount >= 20 * mViewModel.getCurrentUpcomingTvShowsPage()) {

                                            mViewModel.getNextPageSearchedTvShowsList(finalS.toString(), new OnTvShowListLoadedCallback() {
                                                @Override
                                                public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                                                    TvShowsRecyclerViewAdapter.notifyDataSetChanged();
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
        mTabsTvShowListType.addTab(mTabsTvShowListType.newTab().setText("Popular"));
//        mTabsTvShowListType.addTab(mTabsTvShowListType.newTab().setText("Latest"));
        mTabsTvShowListType.addTab(mTabsTvShowListType.newTab().setText("Top Rated"));
    }

    private void switchTabs(int tabPos) {
        switch (tabPos) {
            case 0:
                tvShowsRecyclerViewAdapter = new TvShowsRecyclerViewAdapter(getActivity(), mViewModel.getPopularTvShows());
                tvShowsRecyclerViewAdapter.setClickListener(new TvShowsRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TvShowDetailsFragment tvShowDetailsFragment = TvShowDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("TvShowId", mViewModel.getPopularTvShows().get(position).getId());
                        tvShowDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, tvShowDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                mRVTvShowsList.setAdapter(tvShowsRecyclerViewAdapter);
                mRVTvShowsList.clearOnScrollListeners();
                mRVTvShowsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentPopularTvShowsPage() != mViewModel.getTotalPopularTvShowsPage()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0
                                    && totalItemCount >= 20 * mViewModel.getCurrentPopularTvShowsPage()) {

                                mViewModel.getNextPagePopularTvShowsList(new OnTvShowListLoadedCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                                        tvShowsRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        }
                    }
                });

                if (mViewModel.getPopularTvShows().isEmpty()) {
                    mViewModel.getPopularTvShowsList(new OnTvShowListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<TvShow> TvShows, int totalPages) {
                            tvShowsRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                break;
            case 1:
                tvShowsRecyclerViewAdapter = new TvShowsRecyclerViewAdapter(getActivity(), mViewModel.getTopRatedTvShows());
                tvShowsRecyclerViewAdapter.setClickListener(new TvShowsRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TvShowDetailsFragment tvShowDetailsFragment = TvShowDetailsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putInt("TvShowId", mViewModel.getTopRatedTvShows().get(position).getId());
                        tvShowDetailsFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, tvShowDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                mRVTvShowsList.setAdapter(tvShowsRecyclerViewAdapter);
                mRVTvShowsList.clearOnScrollListeners();
                mRVTvShowsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        if (!mViewModel.isNextPageLoading() && mViewModel.getCurrentTopRatedTvShowsPage() != mViewModel.getTotalTopRatedTvShowsPage()) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0
                                    && totalItemCount >= 20 * mViewModel.getCurrentTopRatedTvShowsPage()) {

                                mViewModel.getNextPageTopRatedTvShowsList(new OnTvShowListLoadedCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                                        tvShowsRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        }
                    }
                });
                if (mViewModel.getTopRatedTvShows().isEmpty()) {
                    mViewModel.getTopRatedTvShowsList(new OnTvShowListLoadedCallback() {
                        @Override
                        public void onSuccess(final ArrayList<TvShow> TvShows, int totalPages) {
                            tvShowsRecyclerViewAdapter.notifyDataSetChanged();
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
