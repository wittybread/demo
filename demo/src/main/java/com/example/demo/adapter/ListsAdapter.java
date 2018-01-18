package com.example.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.bean.MessageBean;
import com.example.demo.view.GoodsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by SDC on 2017/12/21.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
    private List<MessageBean.DataBean> list;
    private Context context;


    public ListsAdapter(Context context, List<MessageBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = View.inflate(context, R.layout.item, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String images = list.get(position).getImages();
        if(images!=null){
            String[] split = images.split("\\|");
            viewHolder.item_img.setImageURI(split[0]);

        }
        viewHolder.tvPrice.setText("￥"+list.get(position).getPrice());
        viewHolder.tvPrice.setText(list.get(position).getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int pid = list.get(position).getPid();

                Intent intent = new Intent(context,GoodsActivity.class);
                intent.putExtra("pid",pid+"");
                context.startActivity(intent);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvPrice;

        private SimpleDraweeView item_img;
        public ViewHolder(View view) {
            super(view);
            tvTitle =  view.findViewById(R.id.tv_title);
            tvPrice =  view.findViewById(R.id.tv_title);

            item_img = view.findViewById(R.id.lis_img);
        }
    }
}