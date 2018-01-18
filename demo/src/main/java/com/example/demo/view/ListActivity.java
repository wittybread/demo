package com.example.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.demo.IView;
import com.example.demo.R;
import com.example.demo.adapter.ListsAdapter;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.MessageBean;
import com.example.demo.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements IView {

    @BindView(R.id.rcy)
    RecyclerView mRcy;
    private ListsAdapter adapter;
    private String url = "https://www.zhaoapi.cn/";
    private int pscid = 39;
    private int page = 1;
    private String products = "getProducts";
    private List<MessageBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //适配器
        adapter = new ListsAdapter(this, list);
        //调用P层
        MessagePresenter presenter = new MessagePresenter();
        presenter.attach(this);
        presenter.getData(url, products, "", pscid, page);
        mRcy.setLayoutManager(manager);
        mRcy.setAdapter(adapter);
    }

    @Override
    public void onSuccess(Object o) {
        //回调
        List<MessageBean.DataBean> data = (List<MessageBean.DataBean>) o;
        if (data != null) {
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    public void success(AddBean loginBean) {

    }

}
