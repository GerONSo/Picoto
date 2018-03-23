package com.example.maxim.picoto;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView>{

    public void createFragment(){
        getViewState().createFragment();
    }


}
