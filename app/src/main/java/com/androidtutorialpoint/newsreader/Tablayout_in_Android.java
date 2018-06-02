package com.androidtutorialpoint.newsreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.assad.newsreader.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import models.DataModel;

public class Tablayout_in_Android extends Fragment  {

    CustomAdapter adapter;
    AlertDialog dialog;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
        ArrayList<DataModel> dataList= new ArrayList<>();
        TextView t;
        ViewPagerAdapter mViewPagerAdapter;
        public Tablayout_in_Android() {
            // Required empty public constructor
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_tablayout_in__android, container, false);
            return view;
        }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        recyclerView = view.findViewById(R.id.my_recycler_view_news);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ((MainActivity) getActivity()).setDataPasser(new OnDataPass() {
            @Override
            public void onDataPass(Integer position) {
                //t.setText("position is "+position);
                if(position!=null){
                    dataList.clear();
                    adapter.notifyDataSetChanged();
                    displayList(adapter,recyclerView,position);
                }
                }
        });
        adapter = new CustomAdapter(getActivity(),dataList);
        adapter.onItemClickListener(new onItemClickListener() {
            @Override
            public void onItem(int position) {
                String url=dataList.get(position).getUrl();
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
        displayList(adapter,recyclerView,0);
    }
    private void displayList(final CustomAdapter adapter, final RecyclerView recyclerView, int position) {

        Connection c= new Connection();
        final String Url="https://newsapi.org/v2/top-headlines?country="+SPUser.getValue(getActivity(),SPUser.COUNTREY_NAME)+"&category="+getResources().getStringArray(R.array.categories_array)[position]+"&apiKey=355fd15df796476db6f2222b103b3005";
        System.out.println("URL is "+Url);
        c.connectToServer(Url,"",getActivity(),Request.Method.GET);
        c.setCustomEventListener(new OnJSONObjectResponseListener() {
            @Override
            public void onResponse(JSONObject response, String errorMessage)  {
                if(response!=null){
                    try {
                        Log.v("hdfgjhd",response.toString());
                        if (!response.optString("totalResults").equals("0")){
                            JSONArray articles=response.getJSONArray("articles");
                            for(int a=0;a<articles.length();a++){
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                JSONObject obj=articles.getJSONObject(a);
                                DataModel model= new DataModel();

                                model.setAuthor(obj.getString("author"));
                                model.setTitle(obj.getString("title"));
                                model.setUrl(obj.getString("url"));

                                model.setImageUrl(obj.getString("urlToImage"));
                                model.setPublishedAt(obj.getString("publishedAt"));
                                dataList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            dialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Country not supported");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getActivity(),CountreySelection.class);
                                    startActivity(intent);
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.getLocalizedMessage());
                    }
                }
                else{
                    Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
