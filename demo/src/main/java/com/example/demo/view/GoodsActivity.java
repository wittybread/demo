package com.example.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.IView;
import com.example.demo.R;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.GoodsBean;
import com.example.demo.presenter.MessagePresenter;
import com.example.demo.presenter.MyPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.img)
    SimpleDraweeView mImg;
    @BindView(R.id.goods_title)
    TextView mGoodsTitle;
    @BindView(R.id.goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_into)
    Button mBtnInto;
    private String pid;
//    https://www.zhaoapi.cn/product/getProductDetail?pid=71&source=android
    private String url = "https://www.zhaoapi.cn/";
    private int pscid=39;
    private int page=1;
//    http://120.27.23.105/product/addCart?uid=3027&pid=1&source=android
    private String  products="getProductDetail";
    private String product ="addCart";
    private MessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        presenter = new MessagePresenter();
        presenter.attach(this);
        presenter.getData(url,products,pid,1,2);

    }

    @OnClick({R.id.btn_add, R.id.btn_into})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_add:
                MyPresenter presenter = new MyPresenter();
                presenter.attach(this);
                presenter.getData(url,product,"1262",pid);
                break;
            case R.id.btn_into:
                Intent intent = new Intent(this,GoodsShopActivity.class);
                startActivity(intent);
                break;
        }
    }
//    @BindView(R.id.img)
//    SimpleDraweeView mImg;
//    @BindView(R.id.goods_title)
//    TextView mGoodsTitle;
//    @BindView(R.id.goods_price)
//    TextView mGoodsPrice;
//    @BindView(R.id.btn_add)
//    Button mBtnAdd;
//    @BindView(R.id.btn_into)
//    Button mBtnInto;
    //设置属性
    @Override
    public void onSuccess(Object o) {
        GoodsBean.DataBean bean = (GoodsBean.DataBean)o;
        if(bean!=null){
            double bargainPrice = bean.getBargainPrice();
            String images = bean.getImages();
            String[] split = images.split("\\|");
            String title = bean.getTitle();
            mGoodsTitle.setText(title);
            mGoodsPrice.setText("￥"+bargainPrice);
            mImg.setImageURI(split[0]);
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    public void success(AddBean bean) {
        if(bean!=null){
            String msg = bean.getMsg();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }


}
