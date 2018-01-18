package com.example.demo.model;


import com.example.demo.bean.AddBean;
import com.example.demo.bean.LoginBean;
import com.example.demo.bean.RegistBean;
import com.example.demo.presenter.MyPresenter;
import com.example.demo.utils.RetrofitUtils;

import io.reactivex.Flowable;

public class MyModel {
    private MyPresenter presenter;

    public MyModel(MyPresenter presenter) {
        this.presenter = presenter;
    }
    public void getData(String url,String login ,String mobile,String password){
        if(login.equals("reg")){
            Flowable<RegistBean> flowable = RetrofitUtils.getInstance(url).getApiService().getMessage(login,mobile, password);
            presenter.getMessage(flowable);
        } else if (login.equals("login")) {
            Flowable<LoginBean> flowable2 = RetrofitUtils.getInstance(url).getApiService().getLoginMessage(login,mobile, password);
            presenter.getLoginMessage(flowable2);
        }else{
            Flowable<AddBean> flowable3 = RetrofitUtils.getInstance(url).getApiService().getGoodsMessage(login,mobile, password);
            presenter.getGoodsMessage(flowable3);
        }



    }
}
