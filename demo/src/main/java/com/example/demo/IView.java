package com.example.demo;


import com.example.demo.bean.AddBean;

/**
 * Created by SDC on 2017/12/21.
 */

public interface IView {
    void onSuccess(Object o);
    void onFailed(Exception e);

    void success(AddBean loginBean);
}
