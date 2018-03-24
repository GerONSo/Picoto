package com.example.maxim.picoto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


public class RecyclerViewFragment extends MvpAppCompatFragment implements IRecyclerView {



    @InjectPresenter
    public RecyclerViewPresenter recyclerViewPresenter;

    private RecyclerView listView;
    private RecyclerViewAdapter adapter;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance(){
        return new RecyclerViewFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerViewPresenter.setResources(getResources());
        recyclerViewPresenter.setContext(getContext());
        recyclerViewPresenter.setList();
        Log.d("mytag", String.valueOf(recyclerViewPresenter.getList().get(0).getName()));
        listView=view.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter=new RecyclerViewAdapter(this,recyclerViewPresenter);
        listView.setAdapter(adapter);
    }

    public RecyclerViewPresenter getRecyclerViewPresenter() {
        return recyclerViewPresenter;
    }
}
