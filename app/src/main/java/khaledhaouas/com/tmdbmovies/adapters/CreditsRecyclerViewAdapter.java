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
import khaledhaouas.com.tmdbmovies.models.entities.Credit;

public class CreditsRecyclerViewAdapter extends RecyclerView.Adapter<CreditsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Credit> mData;
    private LayoutInflater mInflater;
    private Context mContext;
//    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CreditsRecyclerViewAdapter(Context context, ArrayList<Credit> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_cast, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtActorName.setText(mData.get(position).getActorName());
        holder.txtCharacterName.setText(mData.get(position).getCharacterName());
        Glide.with(mContext)
                .load(mData.get(position).getActorProfilePicture())
                .apply(new RequestOptions().placeholder(R.drawable.movie_background_placeholder))
                .into(holder.imgActorProfile);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgActorProfile;
        TextView txtActorName;
        TextView txtCharacterName;

        ViewHolder(View itemView) {
            super(itemView);
            imgActorProfile = itemView.findViewById(R.id.img_actor_profile);
            txtActorName = itemView.findViewById(R.id.txt_actor_name);
            txtCharacterName = itemView.findViewById(R.id.txt_character_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData[id];
//    }

    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }

    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
}
