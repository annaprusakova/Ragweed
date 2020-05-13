package com.prusakova.ragweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.prusakova.ragweed.model.Medicine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MedicineAdapter  extends RecyclerView.Adapter<MedicineAdapter.ViewHolder>{

    private List<Medicine> medicines;
    private Context context;
    private MedicineAdapter.RecyclerViewClickListener mListener;

    public MedicineAdapter(List<Medicine> medicines, Context context, MedicineAdapter.RecyclerViewClickListener listener) {
        this.medicines = medicines;
        this.context = context;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_medicine, parent, false);
        return new MedicineAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        holder.medName.setText(medicines.get(position).getMed_name());
        Picasso.with(context)
                .load(medicines.get(position).getPhoto_med())
                .into(holder.medImage);
    }


    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView medName;

        private ImageView medImage;
        private FrameLayout mRowContainer;
        private MedicineAdapter.RecyclerViewClickListener mListener;
        public ViewHolder(View itemView, MedicineAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            medName = itemView.findViewById(R.id.med_name);
            medImage = itemView.findViewById(R.id.image_med);
            mRowContainer = itemView.findViewById(R.id.medicine_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.medicine_container:
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
