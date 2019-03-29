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

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.models.entities.Episode;
import khaledhaouas.com.tmdbmovies.utils.Utils;

public class EpisodesRecyclerViewAdapter extends RecyclerView.Adapter<EpisodesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Episode> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public EpisodesRecyclerViewAdapter(Context context, ArrayList<Episode> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_episode, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Utils.initRatingBar(mContext, holder.mRtEpisodeRating);

        Glide.with(mContext)
                .load(mData.get(position).getPosterImage())
                .apply(new RequestOptions().placeholder(R.drawable.movie_poster_placeholder))
                .into(holder.mImgEpisodePoster);

        holder.mTxtEpisodeName.setText(mData.get(position).getNumber() + ". " + mData.get(position).getName());
        holder.mTxtEpisodeAirDate.setText(Utils.formatDate(mData.get(position).getAirDate()));

        holder.mRtEpisodeRating.setRating((float) mData.get(position).getRating() / 2);
        holder.mTxtEpisodeReviewNbrs.setText(mData.get(position).getVoteCount() + "");
//        setAnimation(holder.mEpisodeLayout);

    }

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //     convenience method for getting data at click position
    public Episode getItem(int id) {
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
        ImageView mImgEpisodePoster;
        TextView mTxtEpisodeName;
        //        TextView mTxtEpisodeEpCount;
        TextView mTxtEpisodeAirDate;
        RatingBar mRtEpisodeRating;
        TextView mTxtEpisodeReviewNbrs;
        RelativeLayout mEpisodeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mImgEpisodePoster = itemView.findViewById(R.id.img_episode_poster);
            mTxtEpisodeName = itemView.findViewById(R.id.txt_episode_title);
//            mTxtEpisodeEpCount = itemView.findViewById(R.id.txt_season_ep_count);
            mTxtEpisodeAirDate = itemView.findViewById(R.id.txt_episode_release_date);
            mRtEpisodeRating = itemView.findViewById(R.id.txt_episode_rating);
            mEpisodeLayout = itemView.findViewById(R.id.layout_episode_item);
            mTxtEpisodeReviewNbrs = itemView.findViewById(R.id.txt_episode_reviews_nbre);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
