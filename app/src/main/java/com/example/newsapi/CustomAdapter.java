package com.example.newsapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<Model> list;
    Context context;
    private ItemClickListener mItemClickListener;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final TextView textView;
        private final TextView textView, textView2;
        private final ImageView imageView;
        ItemClickListener itemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            textView = (TextView) view.findViewById(R.id.title);
            textView2 = (TextView) view.findViewById(R.id.date);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            // Define click listener for the ViewHolder's View
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemCLick(getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemCLick(int position);
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param list String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(ArrayList<Model> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.mItemClickListener = itemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item, viewGroup, false);

        return new ViewHolder(view, mItemClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        if(list.get(position).getImageUrl().equalsIgnoreCase("null")){
            Glide.with(viewHolder.itemView.getContext()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSADPzrYm_hQg2XMNc_9KTr9Axmn35s0DbsIQ&usqp=CAU").into(viewHolder.imageView);
        }
        else{
            Glide.with(viewHolder.itemView.getContext()).load(list.get(position).getImageUrl()).into(viewHolder.imageView);
        }

        viewHolder.textView.setText(list.get(position).getTitle());
        viewHolder.textView2.setText(list.get(position).getDate());




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
