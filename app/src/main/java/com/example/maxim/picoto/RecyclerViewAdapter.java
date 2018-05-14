package com.example.maxim.picoto;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxim.picoto.presenters.RecyclerViewPresenter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerViewFragment recyclerViewFragment;
    private RecyclerViewPresenter recyclerViewPresenter;
    private OnStyleSelected callback;
    private int currentStyle = -1;

    interface OnStyleSelected{
        void onStyleSelected(int position);
    }

    public RecyclerViewAdapter(RecyclerViewFragment recyclerViewFragment,RecyclerViewPresenter recyclerViewPresenter,OnStyleSelected callback) {
        this.recyclerViewFragment = recyclerViewFragment;
        this.recyclerViewPresenter=recyclerViewPresenter;
        this.callback=callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("mytag", String.valueOf(position));
        holder.imageImageView.setImageBitmap(recyclerViewPresenter.getList().get(position).getImage());
        holder.nameTextView.setText(recyclerViewPresenter.getList().get(position).getName());
        if(position != currentStyle) holder.crossImageView.setVisibility(View.GONE);
        else holder.crossImageView.setVisibility(View.VISIBLE);
        holder.crossImageView.setImageBitmap(BitmapFactory.decodeResource(recyclerViewPresenter.getResources(), R.drawable.cross));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStyle = position;
                recyclerViewPresenter.redrawRecyclerView();
                callback.onStyleSelected(recyclerViewPresenter.getList().get(position).getStyleNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewFragment.getRecyclerViewPresenter().getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageImageView;
        ImageView crossImageView;
        TextView nameTextView;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            imageImageView=view.findViewById(R.id.image);
            nameTextView=view.findViewById(R.id.text);
            crossImageView = view.findViewById(R.id.cross);
        }

    }
}
