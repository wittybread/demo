package com.example.demo.presenter;

import android.util.Log;


import com.example.demo.IView;
import com.example.demo.bean.GoodsBean;
import com.example.demo.bean.MessageBean;
import com.example.demo.model.MessageModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class MessagePresenter implements IPresenter{
    private IView iv;
    private DisposableSubscriber<MessageBean> disposableSubscriber;
    private DisposableSubscriber<GoodsBean> disposableSubscriber1;

    public void attach(IView iv){
        this.iv=iv;
    }
    public void detach(){
        if (iv!=null){
            iv=null;
        }
        if(!disposableSubscriber.isDisposed()){
            disposableSubscriber.dispose();
        }
        if(!disposableSubscriber1.isDisposed()){
            disposableSubscriber1.dispose();
        }
    }

//    https://www.zhaoapi.cn/product/getProducts?pscid=39&page=1
    @Override
    public void getData(String url,String products,String pid,int pscid,int page ) {
        MessageModel messageModel = new MessageModel(this);
        messageModel.getData(url,products,pid,pscid,page);
    }


    public void getMessage(Flowable<MessageBean> flowable) {
        disposableSubscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean>() {
                    @Override
                    public void onNext(MessageBean loginBean) {
                        if (loginBean != null) {
                            List<MessageBean.DataBean> data = loginBean.getData();
                            if(data!=null){
                                iv.onSuccess(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("zxz",t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //
    public void getNews(Flowable<GoodsBean> flowable2) {
        disposableSubscriber1 = flowable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<GoodsBean>() {
                    @Override
                    public void onNext(GoodsBean loginBean) {
                        if (loginBean != null) {
                            GoodsBean.DataBean data = loginBean.getData();
                            if (data != null) {
                                iv.onSuccess(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("zxz", t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
