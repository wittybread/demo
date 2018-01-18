package com.example.demo.presenter;

import android.util.Log;


import com.example.demo.IView;
import com.example.demo.bean.CartBean;
import com.example.demo.model.ShopCartsModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by SDC on 2017/12/21.
 */

public class ShopCartsPresenter implements ShoppingPresenter{
    private IView iv;
    private DisposableSubscriber<CartBean> disposableSubscriber;

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
    }

    @Override
    public void getData(String url,String products,String uid ) {
        ShopCartsModel messageModel = new ShopCartsModel(this);
        messageModel.getData(url,products,uid);
    }


    public void getMessage(Flowable<CartBean> flowable) {
        disposableSubscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CartBean>() {
                    @Override
                    public void onNext(CartBean bean) {
                        if (bean != null) {
                             List<CartBean.DataBean> data = bean.getData();
                            if(data!=null){
                                iv.onSuccess(data);
                                Log.i("zzzz", "onSuccess: "+ data.toString());
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

}
