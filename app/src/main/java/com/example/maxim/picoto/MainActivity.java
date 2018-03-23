package com.example.maxim.picoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements IMainView{

    @InjectPresenter
    MainPresenter presenter;

    RecyclerViewFragment recyclerViewFragment;
    ImageViewFragment imageViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            presenter.createFragment();
        }
    }


    @Override
    public void createFragment() {
        recyclerViewFragment=RecyclerViewFragment.newInstance();
        imageViewFragment=ImageViewFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.image_frame,recyclerViewFragment)
                .add(R.id.recycler_frame,imageViewFragment)
                .commit();


    }
}
