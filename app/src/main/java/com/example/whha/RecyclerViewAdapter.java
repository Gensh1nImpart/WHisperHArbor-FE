package com.example.whha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whha.model.PublicPost;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<PublicPost> data;

    public RecyclerViewAdapter(List<PublicPost> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.RecyclerViewHolder holder, int position) {

        holder.Author.setText(data.get(position).getAuthor());
        holder.Content.setText(data.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Author, Content;
        public RecyclerViewHolder(View v) {
            super(v);
            Author = v.findViewById(R.id.items_author);
            Content = v.findViewById(R.id.items_content);
        }
    }
}