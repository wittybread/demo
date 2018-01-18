package com.example.demo.presenter;

import android.util.Log;


import com.example.demo.IView;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.LoginBean;
import com.example.demo.bean.RegistBean;
import com.example.demo.model.MyModel;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by SDC on 2017/12/21.
 */

public class MyPresenter implements BasePresenter{
    private IView iv;
    private DisposableSubscriber<RegistBean> disposableSubscriber;
    private DisposableSubscriber<LoginBean> subscriber;

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
        if(!subscriber.isDisposed()){
            subscriber.dispose();
        }
    }


    @Override
    public void getData(String url,String login,String mobile,String password) {
        MyModel myModel = new MyModel(this);
        myModel.getData(url,login,mobile,password);
    }
    public void getMessage(Flowable<RegistBean> flowable) {
        disposableSubscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<RegistBean>() {
                    @Override
                    public void onNext(RegistBean loginBean) {
                        if (loginBean != null) {
                            iv.onSuccess(loginBean);
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
    public void getLoginMessage(Flowable<LoginBean> flowable) {
        subscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean != null) {
                            iv.onSuccess(loginBean);
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
    //公共方法
    public void getGoodsMessage(Flowable<AddBean> flowable3) {
        flowable3.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<AddBean>() {
                    @Override
                    public void onNext(AddBean loginBean) {
                        //给v层传数据
                        if (loginBean != null) {
                            iv.success(loginBean);
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
