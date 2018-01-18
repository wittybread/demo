package com.example.demo.model;


import com.example.demo.bean.GoodsBean;
import com.example.demo.bean.MessageBean;
import com.example.demo.presenter.MessagePresenter;
import com.example.demo.utils.RetrofitUtils;

import io.reactivex.Flowable;

/**
 * Created by SDC on 2017/12/21.
 */

public class MessageModel {
    private MessagePresenter presenter;

    public MessageModel(MessagePresenter presenter) {
        this.presenter = presenter;
    }
    public void getData(String url, String roducts,String pid, int pscid,int page){
            if(pid.equals("")){
                Flowable<MessageBean> flowable = RetrofitUtils.getInstance(url).getApiService().getListGoodes(roducts,pscid, page);
                presenter.getMessage(flowable);
            }else{
                Flowable<GoodsBean> flowable2 = RetrofitUtils.getInstance(url).getApiService().getGoods(roducts,pid );
                presenter.getNews(flowable2);
            }





    }
}
