package com.example.actual_training;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> implements Filterable {
    private List<GameList> aGameList;
    private List<GameList> mGameListFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.gameTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public GameListAdapter(List<GameList> gamelist){
        aGameList=gamelist;
        mGameListFull = new ArrayList<>(gamelist);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
        ViewHolder vh=new ViewHolder(v,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameList gameList = aGameList.get(position);

        holder.imageView.setImageResource(gameList.getImageId());
        holder.textView.setText(gameList.getName());
    }

    @Override
    public int getItemCount() {
        return aGameList.size();
    }

    @Override
    public Filter getFilter(){
        return gameFilter;
    }

    private Filter gameFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<GameList> filteredList = new ArrayList<>();

            if(charSequence ==null||charSequence.length()==0){
                filteredList.addAll(mGameListFull);
            }else{
                String filterPattern = charSequence.toString().trim();
                for(GameList item:mGameListFull){
                    if(item.getName().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            aGameList.clear();
            aGameList = (List) results.values;
            notifyDataSetChanged();
        }
    };

}
