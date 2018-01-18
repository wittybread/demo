package com.example.demo.presenter;

import android.util.Log;


import com.example.demo.IView;
import com.example.demo.bean.AddBean;
import com.example.demo.model.DeleteModel;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class DeletePresenter implements DelPresenter{
    private IView iv;
    private DisposableSubscriber<AddBean> disposableSubscriber;

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
//    http://120.27.23.105/product/deleteCart?uid=71&pid=1
    @Override
    public void getData(String url,String products,String uid ,String pid) {
        DeleteModel messageModel = new DeleteModel(this);
        messageModel.getData(url,products,uid,pid);
    }


    public void getMessage(Flowable<AddBean> flowable) {
        disposableSubscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<AddBean>() {
                    @Override
                    public void onNext(AddBean bean) {
                        if (bean != null) {
                                iv.success(bean);



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
