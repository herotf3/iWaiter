package vn.thientf.iwaiter.Models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.thientf.iwaiter.Interface.ItemClickListener;
import vn.thientf.iwaiter.R;

/**
 * Created by ASUS on 22/06/2018.
 */

public class FoodVH extends RecyclerView.ViewHolder implements View.OnClickListener{
    private View itemView;
    private TextView tvName, tvPrice;
    private ImageView imgFood,iconStatus;

    ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    public FoodVH(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View view) {
        itemView=view;
        tvName=itemView.findViewById(R.id.item_food_name);
        tvPrice=itemView.findViewById(R.id.item_food_cost);
        imgFood=itemView.findViewById(R.id.item_food_img);
        iconStatus=itemView.findViewById(R.id.item_food_status);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,this.getAdapterPosition());
    }

    public void bindData(Food food, Context baseContext){
        tvName.setText(food.getName());
        tvPrice.setText(String.valueOf(food.getPrice()));
        if (!food.getImage().isEmpty()) {
            String url=food.getImage();
            Picasso.with(baseContext).load(food.getImage()).into(imgFood);
        }
    }
}
