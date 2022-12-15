package com.example.actual_training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SQLConnectAdapter extends RecyclerView.Adapter<SQLConnectAdapter.SQLContextViewHolder>  {
    private List<SQLContextList> list;
    private List<SQLContextList> listAll;
    Context context;
    private OnItemClickListener mListener;



    public Filter getFilter() {
        return listFilter;
    }
    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SQLContextList> filteredList = new ArrayList<>();
            if(constraint ==null|| constraint.length()==0){
                filteredList.addAll(listAll);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(SQLContextList item:listAll){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public SQLConnectAdapter(List<SQLContextList> list, Context context) {
        this.list = list;
        this.context = context;
        this.listAll = new ArrayList<>(list);
    }

    public class SQLContextViewHolder extends RecyclerView.ViewHolder {
        TextView info,infoURL;
        public SQLContextViewHolder(View itemView){
            super(itemView);

            info = itemView.findViewById(R.id.info);
            infoURL = itemView.findViewById(R.id.infourl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    @NonNull
    @Override
    public SQLContextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_for_mysql,viewGroup,false);
        return new SQLContextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SQLContextViewHolder holder, int position) {
        SQLContextList sqlContextList = list.get(position);
        holder.info.setText(sqlContextList.getTitle());
        holder.infoURL.setText(sqlContextList.getUrl());
    }




    @Override
    public int getItemCount() {
        return list.size();
    }
}
