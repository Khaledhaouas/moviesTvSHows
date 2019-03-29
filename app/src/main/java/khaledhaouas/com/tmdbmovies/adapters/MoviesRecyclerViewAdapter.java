package khaledhaouas.com.tmdbmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.utils.Utils;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MoviesRecyclerViewAdapter(Context context, ArrayList<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_movie, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Utils.initRatingBar(mContext, holder.mRtMovieRating);

        Glide.with(mContext)
                .load(mData.get(position).getPosterImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.movie_poster_placeholder))
                .into(holder.mImgMoviePoster);

        holder.mTxtMovieTitle.setText(mData.get(position).getTitle());
//        holder.mTxtMovieGenres.setText(mData.get(position).getGenres());
        holder.mRtMovieRating.setRating((float) mData.get(position).getRating() / 2);
        holder.mTxtMovieReviewNbrs.setText(mData.get(position).getReviewNbrs() + "");
        holder.mTxtMovieReleaseDate.setText(Utils.formatDate(mData.get(position).getReleaseDate()));

        setAnimation(holder.mMovieLayout);


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
    }

    //     convenience method for getting data at click position
    public Movie getItem(int id) {
        return mData.get(id);
    }

    //     allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //     parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImgMoviePoster;
        TextView mTxtMovieTitle;
        //        TextView mTxtMovieGenres;
        RatingBar mRtMovieRating;
        TextView mTxtMovieReviewNbrs;
        TextView mTxtMovieReleaseDate;
        RelativeLayout mMovieLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mImgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            mTxtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
//            mTxtMovieGenres = itemView.findViewById(R.id.txt_movie_genres);
            mTxtMovieReviewNbrs = itemView.findViewById(R.id.txt_movie_reviews_nbre);
            mTxtMovieReleaseDate = itemView.findViewById(R.id.txt_movie_release_date);
            mRtMovieRating = itemView.findViewById(R.id.txt_movie_rating);
            mMovieLayout = itemView.findViewById(R.id.layout_movie_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
