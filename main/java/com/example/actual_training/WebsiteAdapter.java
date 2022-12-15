package com.example.actual_training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WebsiteAdapter extends RecyclerView.Adapter<WebsiteAdapter.WebsiteViewHolder> {
    private Context context;
    private List<WebsiteList> weblist;
    private OnItenClickListener listener;

    public interface OnItenClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItenClickListener listener){
        this.listener=listener;
    }

    public WebsiteAdapter(Context context,List<WebsiteList> weblist){
        this.context=context;
        this.weblist=weblist;
    }

    @Override
    public WebsiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.website_list,parent,false);
        return new WebsiteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteViewHolder holder, int position) {
        WebsiteList websiteList = weblist.get(position);
        String gameforweb = websiteList.getGameforweb();
        String directweb = websiteList.getWebSite();

        holder.wgame.setText(gameforweb);
        holder.webdirect.setText(directweb);
    }

    @Override
    public int getItemCount() {
        return weblist.size();
    }

    public class WebsiteViewHolder extends RecyclerView.ViewHolder{
        public TextView wgame,webdirect;
        public WebsiteViewHolder(@NonNull View itemView) {
            super(itemView);
            wgame = itemView.findViewById(R.id.gameForWeb);
            webdirect = itemView.findViewById(R.id.Website);

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
}
