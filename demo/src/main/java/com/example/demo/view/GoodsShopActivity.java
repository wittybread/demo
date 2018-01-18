package com.example.demo.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demo.IView;
import com.example.demo.R;
import com.example.demo.adapter.ShopCartExListAdapter;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.CartBean;
import com.example.demo.bean.MessageEvent;
import com.example.demo.bean.PriceAndCountEvent;
import com.example.demo.bean.SomeId;
import com.example.demo.presenter.DeletePresenter;
import com.example.demo.presenter.ShopCartsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//public class GoodsShopActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_goods_shop);
//    }
//}
public class GoodsShopActivity extends AppCompatActivity implements IView {

    private static final String TAG = "MainActivity";
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.all_chekbox)
    CheckBox allChekbox;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_go_to_pay)
    TextView tvGoToPay;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_shar)
    LinearLayout llShar;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    private String url = "http://120.27.23.105/";
    private int pscid=39;
    private int page=1;
    //   http://120.27.23.105/product/getCarts
    private String product ="getCarts";
    private int count = 0;
    private List<CartBean.DataBean> list = new ArrayList<>();
    private ShopCartExListAdapter adapter;
    private boolean flag = false;
    private int totalCount = 0;
    private double totalPrice = 0.00;
    private DeletePresenter deletePresenter;
    private String products ="deleteCart";
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_shop);
        ButterKnife.bind(this);
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void getData() {
        ShopCartsPresenter presenter = new ShopCartsPresenter();
        presenter.attach(this);
        presenter.getData(url,product,"1262");
        deletePresenter = new DeletePresenter();
        deletePresenter.attach(this);
        adapter = new ShopCartExListAdapter(this, list);
        exListView.setAdapter(adapter);
    }




    @Override
    public void onSuccess(Object o) {
        if (o instanceof List) {
            List<CartBean.DataBean> dataBean = (List<CartBean.DataBean>) o;
            list.addAll(dataBean);
            adapter.notifyDataSetChanged();
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                exListView.expandGroup(i);
            }
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    public void success(AddBean loginBean) {
        Toast.makeText(this,loginBean.getMsg(),Toast.LENGTH_SHORT).show();;
    }

    public void delete() {
        for (int i = 0; i < list.size(); i++) {
            List<CartBean.DataBean.ListBean> listbean = list.get(i).getList();
            for (int j = 0; j < listbean.size(); j++) {
                CartBean.DataBean.ListBean listBean =listbean.get(j);
                if (listBean.isChecked()) {
                    listbean.remove(j);
                    j--;
                    if (listbean.size() == 0) {
                        list.remove(i);
                        i--;
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.all_chekbox, R.id.tv_share, R.id.tv_save, R.id.tv_delete, R.id.subtitle,R.id.tv_go_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:
                adapter.changeAllListCbState(allChekbox.isChecked());
                break;
            case R.id.tv_share:
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_save:
                Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                AlertDialog dialog;
                if (totalCount == 0) {
                    Toast.makeText(GoodsShopActivity.this, "请选择要删除的商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new AlertDialog.Builder(GoodsShopActivity.this).create();
                dialog.setTitle("操作提示");
                dialog.setMessage("您确定要将这些商品从购物车中移除吗？");
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        delete();
                        deletePresenter.getData(url,products,"1262",pid);
                    }
                });
                dialog.show();
                break;
            case R.id.subtitle:
                flag = !flag;
                if (flag) {
                    subtitle.setText("完成");
                    llShar.setVisibility(View.VISIBLE);
                    llInfo.setVisibility(View.GONE);
                } else {
                    subtitle.setText("编辑");
                    llShar.setVisibility(View.GONE);
                    llInfo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_go_to_pay:
                AlertDialog alert;
                if (totalCount == 0) {
                    Toast.makeText(this, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(this).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alert.show();
                break;
        }
    }
    //全选
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        allChekbox.setChecked(event.isChecked());
    }
    //删除
    @Subscribe
    public void onMessageEvent(SomeId event) {
        pid = event.getPid();
        deletePresenter.getData(url,products,"1262",pid);
        Log.e("zxz",pid);
    }
    //改变价格
    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        title.setText("购物车(" + event.getCount() + ")");
        tvGoToPay.setText("结算(" + event.getCount() + ")");
        tvTotalPrice.setText("￥" + event.getPrice());
        totalCount = event.getCount();
        totalPrice = event.getPrice();
    }

}
