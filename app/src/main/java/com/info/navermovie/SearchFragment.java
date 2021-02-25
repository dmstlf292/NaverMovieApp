package com.info.navermovie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    MovieAdapter adapter;
    RecyclerView recyclerView;
    static RequestQueue requestQueue;

    String clientId = "";
    String clientSecret = "";
    String uriString = "content://com.info.navermovie";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        adapter = new MovieAdapter();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        // queryPerson();

        final SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clearItems();
                makeRequest(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        return rootView;
    }

    private void makeRequest(String query) {
        String apiURL = "https://openapi.naver.com/v1/search/movie.json?query="+query;
        StringRequest request = new StringRequest(Request.Method. GET , apiURL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("makeRequest", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-Naver-Client-Id",clientId);
                params.put("X-Naver-Client-Secret",clientSecret);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void processResponse(String response){
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);
        // clearMovie(); //초기화
        // insertMovie(movieList); //내용제공자 insert
        for(int i = 0; i < movieList.items.size(); i++){
            Movie movie = movieList.items.get(i);
            adapter.addItem(movie);
        }
        adapter.notifyDataSetChanged();
    }




} //END
