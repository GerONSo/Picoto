package com.example.maxim.picoto;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerViewFragment recyclerViewFragment;
    private RecyclerViewPresenter recyclerViewPresenter;

    public RecyclerViewAdapter(RecyclerViewFragment recyclerViewFragment,RecyclerViewPresenter recyclerViewPresenter) {
        this.recyclerViewFragment = recyclerViewFragment;
        this.recyclerViewPresenter=recyclerViewPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("mytag", String.valueOf(position));
        holder.imageImageView.setImageBitmap(recyclerViewPresenter.getList().get(position).getImage());
        holder.nameTextView.setText(recyclerViewPresenter.getList().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recyclerViewFragment.getRecyclerViewPresenter().getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageImageView;
        TextView nameTextView;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            imageImageView=view.findViewById(R.id.image);
            nameTextView=view.findViewById(R.id.text);
        }

    }
}
