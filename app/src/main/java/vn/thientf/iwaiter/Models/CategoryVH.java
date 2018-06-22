package vn.thientf.iwaiter.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.thientf.iwaiter.Interface.ItemClickListener;
import vn.thientf.iwaiter.R;

/**
 * Created by ASUS on 22/06/2018.
 */

public class CategoryVH extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView tvName;
    private View itemView;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryVH(View itemView) {
        super(itemView);
        this.itemView=itemView;
        initView();
    }

    private void initView() {
        tvName=itemView.findViewById(R.id.tvCategory);
        itemView.setOnClickListener(this);
    }

    public void bindData(Category category){
        tvName.setText(category.getName());
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,this.getAdapterPosition());
    }
}
