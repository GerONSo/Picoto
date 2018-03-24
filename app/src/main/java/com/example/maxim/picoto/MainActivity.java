package com.example.maxim.picoto;

import android.content.Intent;
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
        imageViewFragment=ImageViewFragment.newInstance();
        recyclerViewFragment=RecyclerViewFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.image_frame,imageViewFragment)
                .add(R.id.recycler_frame,recyclerViewFragment)
                .commit();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
