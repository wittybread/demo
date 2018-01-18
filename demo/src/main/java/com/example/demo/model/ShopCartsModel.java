package com.example.demo.model;


import com.example.demo.bean.CartBean;
import com.example.demo.presenter.ShopCartsPresenter;
import com.example.demo.utils.RetrofitUtils;

import io.reactivex.Flowable;

/**
 * Created by SDC on 2017/12/21.
 */

public class ShopCartsModel {
    private ShopCartsPresenter presenter;

    public ShopCartsModel(ShopCartsPresenter presenter) {
        this.presenter = presenter;
    }
    public void getData(String url, String roducts,String uid){

                Flowable<CartBean> flowable = RetrofitUtils
                        .getInstance(url)
                        .getApiService()
                        .getShoppingCarts(roducts ,uid);
                presenter.getMessage(flowable);




    }
}
