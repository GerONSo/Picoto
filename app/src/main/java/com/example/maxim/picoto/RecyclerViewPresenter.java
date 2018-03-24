package com.example.maxim.picoto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by maxim on 23.03.18.
 */

@InjectViewState
public class RecyclerViewPresenter extends MvpPresenter<IRecyclerView>{

    private ArrayList<RecyclerViewData> list;
    private Resources resources;
    private Context context;


    public ArrayList<RecyclerViewData> getList() {
        return list;
    }

    public void setList() {
        list=new ArrayList<>();
        ArrayList<String> names=NameQuery.getNames();
        int j=0;
        for(int i=0;i<31;i++) {
            if(i!=8){
                String name="p";
                if(i<10) name+="0";
                name+=String.valueOf(i);
                list.add(getRecyclerViewData(getId(name), names.get(j), i));
                Log.d("mytag",names.get(j));
                j++;
            }

        }
    }

    private RecyclerViewData getRecyclerViewData(int bitmapId,String name,int styleNumber){
        return new RecyclerViewData(BitmapFactory.decodeResource(resources,bitmapId),name,styleNumber);
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
    private int getId(String s){
        return resources.getIdentifier(s,"drawable",context.getPackageName());
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
