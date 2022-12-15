package com.example.actual_training;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SQLConnectActivity extends AppCompatActivity implements SQLConnectAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<SQLContextList> arrayList;
    private SQLConnectAdapter madapter;
    private RequestQueue requestQueue;
    private String name,site;
    private ArrayList<String> websiteType=new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;

    String url = "https://axquestion.github.io/CollegeWorks/games.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_connect);

        Toolbar toolbar = findViewById(R.id.appbarForSql);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        name = intent.getStringExtra("game");
        site = intent.getStringExtra("website");

        shimmerFrameLayout = findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();

        recyclerView = findViewById(R.id.recycler_view_for_sql);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SQLConnectActivity.this));

        arrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        parseJSON();

    }


    private void parseJSON(){
        String url ="https://axquestion.github.io/CollegeWorks/games.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");


                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject data = jsonArray.getJSONObject(i);
                                String website = data.getString("website");
                                String game = data.getString("game");
                                String title = data.getString("title");
                                String url = data.getString("url");

                                if(game.toLowerCase().equals(name.toLowerCase())&&website.equals(site)) {
                                    arrayList.add(new SQLContextList(title, url));
                                }
                            }
                            madapter = new SQLConnectAdapter(arrayList, SQLConnectActivity.this);
                            recyclerView.setAdapter(madapter);
                            madapter.setOnItemClickListener(SQLConnectActivity.this);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

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
        requestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        String url=arrayList.get(position).getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar_for_mysql,menu);
        MenuItem item =menu.findItem(R.id.search_for_sql);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                madapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
