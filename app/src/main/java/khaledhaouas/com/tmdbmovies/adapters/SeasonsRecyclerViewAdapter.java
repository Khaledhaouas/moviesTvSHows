package khaledhaouas.com.tmdbmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.models.entities.Season;
import khaledhaouas.com.tmdbmovies.utils.Utils;

public class SeasonsRecyclerViewAdapter extends RecyclerView.Adapter<SeasonsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Season> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public SeasonsRecyclerViewAdapter(Context context, ArrayList<Season> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_season, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(mData.get(position).getPosterImage())
                .apply(new RequestOptions().placeholder(R.drawable.movie_background_placeholder))
                .into(holder.mImgSeasonPoster);

        holder.mTxtSeasonName.setText(mData.get(position).getName());
        holder.mTxtSeasonAirDate.setText(Utils.formatDate(mData.get(position).getAirDate()));
        holder.mTxtSeasonEpCount.setText(mData.get(position).getEpisodeCount() + " Episodes");

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //     convenience method for getting data at click position
    public Season getItem(int id) {
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
        ImageView mImgSeasonPoster;
        TextView mTxtSeasonName;
        TextView mTxtSeasonEpCount;
        TextView mTxtSeasonAirDate;

        ViewHolder(View itemView) {
            super(itemView);
            mImgSeasonPoster = itemView.findViewById(R.id.img_season_poster);
            mTxtSeasonName = itemView.findViewById(R.id.txt_season_name);
            mTxtSeasonEpCount = itemView.findViewById(R.id.txt_season_ep_count);
            mTxtSeasonAirDate = itemView.findViewById(R.id.txt_season_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
