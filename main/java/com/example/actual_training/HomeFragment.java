package com.example.actual_training;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<GameList> mGameList;
    private View view;
    private RecyclerView mRecyclerView;
    private GameListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    int[] imageView = {R.drawable.genshin,R.drawable.tower_of_saviors,R.drawable.monster_strike,R.drawable.arena_of_valor,R.drawable.lol,
            R.drawable.fate_grand_order,R.drawable.minecraft,R.drawable.pretty_derby,R.drawable.honkai_impact_3rd,
            R.drawable.pokemon_series,R.drawable.monster_hunter,R.drawable.battle_cats,R.drawable.god_of_war,R.drawable.hearthstone,R.drawable.kartriderrush,
            R.drawable.valorant,R.drawable.granblue_fantasy,R.drawable.elden_ring,R.drawable.warcraft,R.drawable.guardian_tales,
            R.drawable.maplestory,R.drawable.three_kingdoms_strategy_edition,R.drawable.world_flipper,R.drawable.path_of_exile,
            R.drawable.afk_arena,R.drawable.pokemon_go,R.drawable.lineage_m,R.drawable.shadowverse,R.drawable.white_cat_project,R.drawable.princess_connnect,
            R.drawable.nikke,R.drawable.apex,R.drawable.tower_of_fantasy,R.drawable.epic_seven,R.drawable.arknights,R.drawable.cyberpunk};


    String[] game = {"原神","神魔之塔","怪物彈珠","傳說對決","英雄聯盟","FGO", "Minecraft","賽馬娘",
            "崩壞3rd","神奇寶貝系列","魔物獵人","貓咪大戰爭","戰神","爐石戰記","跑跑卡丁車","特戰英豪",
            "碧藍航線","艾爾登法環","魔獸爭霸","守望傳說","新楓之谷", "三國志戰略版","彈射世界","流亡暗道",
            "劍與遠征","Pokemon Go","天堂 Mobile","闇影詩章","白貓 Project", "超異域公主連結","Nikke",
            "APEX" ,"幻塔","第七史詩","明日方舟","電馭叛客"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_home,container,false);

        setHasOptionsMenu(true);
        mGameList =new ArrayList<>();
        for(int i =0;i<imageView.length;i++){
            int mImageView=imageView[i];
            String gameTitle=game[i];
            mGameList.add(new GameList(gameTitle,mImageView));
        }
        mRecyclerView =view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter =new GameListAdapter(mGameList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GameListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                Intent intent =new Intent(getActivity(),WebsiteActivity.class);
                intent.putExtra("selectedGameList",mGameList.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }
}