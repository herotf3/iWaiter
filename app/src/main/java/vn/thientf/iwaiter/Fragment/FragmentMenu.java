package vn.thientf.iwaiter.Fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import vn.thientf.iwaiter.GlobalData;
import vn.thientf.iwaiter.Interface.ItemClickListener;
import vn.thientf.iwaiter.Models.Category;
import vn.thientf.iwaiter.Models.CategoryVH;
import vn.thientf.iwaiter.Models.Food;
import vn.thientf.iwaiter.Models.FoodVH;
import vn.thientf.iwaiter.R;

/**
 * Created by ChauHoangLong on 6/21/2018.
 */

public class FragmentMenu  extends Fragment{
    View root;
    private String resId, categoryId="all";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseRecyclerAdapter<Category,CategoryVH> adapterCategories;
    FirebaseRecyclerAdapter<Food,FoodVH> adapterFoods;
    FirebaseRecyclerOptions<Food> foodOptions;

    RecyclerView rclMenu,rclFood;

    boolean exist = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, null);
        initView();
        getData();
        setData();
        return root;
    }

    private void getData() {
        database = FirebaseDatabase.getInstance();
        resId = GlobalData.getInstance().getCurrRes();
        //resId="R001";
    }

    private void setData() {
        //Select * from category where category.resId=resId
        Query query=database.getReference(getString(R.string.CategoriesRef))
                .orderByChild("resId")
                .equalTo(resId);

        FirebaseRecyclerOptions<Category> options=
                new FirebaseRecyclerOptions.Builder<Category>().setQuery(query,Category.class).build();

        adapterCategories=new FirebaseRecyclerAdapter<Category, CategoryVH>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryVH holder, int position, @NonNull Category model) {
                holder.bindData(model);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos) {
                        Category selected=adapterCategories.getItem(pos);
                        Toast.makeText(getActivity(),"Pick  now!"+adapterCategories.getRef(pos).getKey(),Toast.LENGTH_SHORT).show();
                        showFoodInCategory(adapterCategories.getRef(pos).getKey());
                    }
                });

            }

            @NonNull
            @Override
            public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_item,parent,false);
                return new CategoryVH(itemView);
            }
        };

        showFoodInCategory("all");
        rclMenu.setAdapter(adapterCategories);
    }

    //change the data in Food adapter
    private void showFoodInCategory(String selectedId) {
        Query newQuery = null;

        if (selectedId.equals("all")) {
            newQuery = database.getReference(getString(R.string.RestaurantsRef)).child(resId)
                    .child("Menu");

            foodOptions=new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(newQuery,Food.class).build();
            adapterFoods=new FirebaseRecyclerAdapter<Food, FoodVH>(foodOptions) {
                @NonNull
                @Override
                public FoodVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new FoodVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_a_food,parent,false));
                }

                @Override
                protected void onBindViewHolder(@NonNull FoodVH holder, final int position, @NonNull Food model) {
                    holder.bindData(model,getActivity());
                    final Food food=model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            showPopUpPicker(food,adapterFoods.getRef(position).getKey());
                        }
                    });
                }
            };
            rclFood.setAdapter(adapterFoods);

        }
        else {
            newQuery = database.getReference(getString(R.string.RestaurantsRef)).child(resId)
                    .child("Menu")
                    .orderByChild("categoryId")
                    .equalTo(selectedId);
            foodOptions=new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(newQuery,Food.class).build();
            adapterFoods.stopListening();
            adapterFoods=new FirebaseRecyclerAdapter<Food, FoodVH>(foodOptions) {
                @NonNull
                @Override
                public FoodVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new FoodVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_a_food,parent,false));
                }

                @Override
                protected void onBindViewHolder(@NonNull FoodVH holder, final int position, @NonNull Food model) {
                    holder.bindData(model,getActivity());

                    final Food food=model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            showPopUpPicker(food,adapterFoods.getRef(position).getKey());
                        }
                    });
                }
            };
            rclFood.setAdapter(adapterFoods);
            adapterFoods.startListening();
            adapterFoods.notifyDataSetChanged();

        }

    }

    private void showPopUpPicker(final Food model, final String id) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View popUpView=inflater.inflate(R.layout.add_food_popup,null);
        final PopupWindow popupWindow=new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //show
        LinearLayout menuLayout=root.findViewById(R.id.layout_menu);
        popupWindow.showAtLocation(menuLayout, Gravity.CENTER,0,0);
        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }
        ImageView imgv=popUpView.findViewById(R.id.order_food_image);
        Picasso.with(getActivity()).load(model.getImage()).into(imgv);
        ((TextView)popUpView.findViewById(R.id.order_food_name)).setText(model.getName());
        final TextView tvPrice=popUpView.findViewById(R.id.order_food_cost);
        tvPrice.setText(String.valueOf(model.getPrice()));

        popUpView.findViewById(R.id.btn_pick_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });

        final TextView tvQty=popUpView.findViewById(R.id.order_qty);
        //add 1 more
        popUpView.findViewById(R.id.btn_add_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty=Integer.parseInt(tvQty.getText().toString())+1;
                tvQty.setText(String.valueOf(qty));
                tvPrice.setText(String.valueOf(qty*model.getPrice()));
                //Toast.makeText(getActivity(),"click +",Toast.LENGTH_SHORT).show();
            }
        });
        //sub 1
        popUpView.findViewById(R.id.btn_sub_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty=Integer.parseInt(tvQty.getText().toString());
                if (qty>1) {
                    qty--;
                    tvQty.setText(String.valueOf(qty));
                    tvPrice.setText(String.valueOf(qty*model.getPrice()));
                }

                //Toast.makeText(getActivity(),"click -",Toast.LENGTH_SHORT).show();
            }
        });
        popUpView.findViewById(R.id.btn_pick_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty= Integer.parseInt(tvQty.getText().toString());
                //add to global cart
                GlobalData.getInstance().getCurrCart().addItem(id,model,qty);
                popupWindow.dismiss();
                Toast.makeText(getActivity(),"add "+String.valueOf(qty)+"x "+model.getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initView() {
        rclMenu=root.findViewById(R.id.rcl_menu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rclMenu.setLayoutManager(layoutManager);

        rclFood=root.findViewById(R.id.rcl_food);
        layoutManager=new LinearLayoutManager(getActivity());
        rclFood.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterFoods.startListening();
        adapterCategories.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterFoods.stopListening();
        adapterCategories.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        resId=GlobalData.getInstance().getCurrRes();
    }
}
