package com.prusakova.ragweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.model.Location;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocationAdapter  extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

private List<Location> locationList;
private Context context;
private RecyclerViewClickListener mListener;

public LocationAdapter(List<Location> locations, Context context,RecyclerViewClickListener listener) {
        this.locationList = locations;
        this.context = context;
        this.mListener = listener;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loc, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.locName.setText(locationList.get(position).getLoc_name());
       holder.locType.setText(locationList.get(position).getLoc_point());
       holder.locDate.setText(locationList.get(position).getLoc_date());
        Picasso.with(context)
                .load(locationList.get(position).getLoc_photo())
                .into(holder.locImage);
    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView locName;
        private TextView  locType;
        private TextView locDate;
        private CircleImageView locImage;
        private RelativeLayout mRowContainer;
        private RecyclerViewClickListener mListener;
        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            locName = itemView.findViewById(R.id.name_loc);
            locType = itemView.findViewById(R.id.type_loc);
            locImage = itemView.findViewById(R.id.picture_loc);
            locDate = itemView.findViewById(R.id.date_loc);
            mRowContainer = itemView.findViewById(R.id.loc_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loc_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }


    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);

    }
}
