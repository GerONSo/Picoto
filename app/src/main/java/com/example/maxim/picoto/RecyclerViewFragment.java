package com.example.maxim.picoto;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.example.maxim.picoto.helpers.ServerHelper;
import com.example.maxim.picoto.interfaces.IRecyclerView;
import com.example.maxim.picoto.presenters.MainPresenter;
import com.example.maxim.picoto.presenters.RecyclerViewPresenter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewFragment extends MvpAppCompatFragment implements IRecyclerView {



    @InjectPresenter(type = PresenterType.LOCAL)
    public RecyclerViewPresenter recyclerViewPresenter;

    @BindView(R.id.listView) public RecyclerView listView;
    private RecyclerViewAdapter adapter;
    private MainPresenter mainPresenter;
    private Bitmap styleImage;


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
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState==null)
            recyclerViewPresenter.setMainPresenter(mainPresenter);
        recyclerViewPresenter.setResources(getResources());
        recyclerViewPresenter.setContext(getContext());
        recyclerViewPresenter.setList();
        //recyclerViewPresenter.setMainPresenter(mainPresenter);

        Log.d("mytag", String.valueOf(recyclerViewPresenter.getList().get(0).getName()));

        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        adapter=new RecyclerViewAdapter(this, recyclerViewPresenter, new RecyclerViewAdapter.OnStyleSelected() {
            @Override
            public void onStyleSelected(int position) {
                styleImage=recyclerViewPresenter.getImage();
                if(styleImage == null) {
                    ConstraintLayout layout = view.findViewById(R.id.recycler_view_fragment);
                    Snackbar snackbar = Snackbar.make(layout, "Select or make photo", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    //mainPresenter.onNullPointerExceptionOccured();
                    return;
                }
                mainPresenter.setProgressVisible();
                mainPresenter.setHighOpacity();
                try {
                    ServerHelper.sendImage(styleImage, position, new ServerHelper.OnImageResult() {
                        @Override
                        public void onImageResult(Bitmap image) {
                            mainPresenter.setProgressGone();
                            mainPresenter.setLowOpacity();
                            Log.d("heii","here");
                            mainPresenter.setImage(image);
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setAdapter(adapter);
    }

    public RecyclerViewPresenter getRecyclerViewPresenter() {
        return recyclerViewPresenter;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.mainPresenter.setRecyclerViewPresenter(recyclerViewPresenter);
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void redrawRecyclerView() {
        adapter.notifyDataSetChanged();
    }
}
