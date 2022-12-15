package com.example.actual_training;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebsiteActivity extends AppCompatActivity implements WebsiteAdapter.OnItenClickListener {
    private GameList gameList;
    private String name;
    private RecyclerView wrView;
    private WebsiteAdapter wAdapter;
    private List<WebsiteList> wList;
    private RequestQueue reQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website_show);

        Toolbar toolbar = findViewById(R.id.appbarForWeb);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        gameList =(GameList) intent.getParcelableExtra("selectedGameList");
        getSupportActionBar().setTitle(gameList.getName());
        name = gameList.getName();

        wrView = findViewById(R.id.recycler_view_for_website);
        wrView.setHasFixedSize(true);
        wrView.setLayoutManager(new LinearLayoutManager(WebsiteActivity.this));

        wList = new ArrayList<>();

        reQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        String url = "https://axquestion.github.io/CollegeWorks/gameList.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jarray = response.getJSONArray("data");

                            for(int i=0;i<jarray.length();i++){
                                JSONObject data = jarray.getJSONObject(i);
                                String game = data.getString("game");
                                String website = data.getString("website");
                                if(game.equals(name)){
                                    wList.add(new WebsiteList(game,website));
                                }
                            }
                            wAdapter = new WebsiteAdapter(WebsiteActivity.this,wList);
                            wrView.setAdapter(wAdapter);
                            wAdapter.setOnItemClickListener(WebsiteActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        reQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent in = new Intent(this,SQLConnectActivity.class);
        in.putExtra("game",wList.get(position).getGameforweb());
        in.putExtra("website",wList.get(position).getWebSite());
        startActivity(in);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
