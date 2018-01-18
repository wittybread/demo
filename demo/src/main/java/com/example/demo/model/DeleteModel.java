package com.example.demo.model;


import com.example.demo.bean.AddBean;
import com.example.demo.presenter.DeletePresenter;
import com.example.demo.utils.RetrofitUtils;

import io.reactivex.Flowable;


public class DeleteModel {
    private DeletePresenter presenter;

    public DeleteModel(DeletePresenter presenter) {
        this.presenter = presenter;
    }
    public void getData(String url, String roducts,String uid,String pid){

                Flowable<AddBean> flowable = RetrofitUtils
                        .getInstance(url)
                        .getApiService()
                        .del(roducts ,uid,pid);
                presenter.getMessage(flowable);




    }
}
