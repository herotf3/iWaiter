package vn.thientf.iwaiter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import vn.thientf.iwaiter.Adapter.CartAdapter;
import vn.thientf.iwaiter.Interface.DeleteCartItem;
import vn.thientf.iwaiter.Models.Cart;
import vn.thientf.iwaiter.Models.Item;
import vn.thientf.iwaiter.Models.Request;

public class ActivityCart extends AppCompatActivity implements DeleteCartItem {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView tvTotal;
    Button btnPlaceOrder;

    FirebaseDatabase db;
    DatabaseReference requestRef;

    Cart orders;
    CartAdapter adapter;
    List<Item> listItemCart;

    Locale locale;
    NumberFormat fmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //firebase
        db=FirebaseDatabase.getInstance();
        String resId=GlobalData.getInstance().getCurrRes();
        resId="R001";
        requestRef=db.getReference(getString(R.string.RestaurantsRef)).child(resId).child("Requests");

        //Init view
        recyclerView=findViewById(R.id.rcl_list_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        tvTotal=findViewById(R.id.tvTotal);
        btnPlaceOrder=findViewById(R.id.btn_placeOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //yeu cau nhap dia chi
                AlertDialog.Builder builder=new AlertDialog.Builder(ActivityCart.this);
                builder.setTitle("Do you have any note?");
                builder.setMessage("Enter something you want");
                final EditText edtAddress=new EditText(ActivityCart.this);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                edtAddress.setLayoutParams(lp);

                builder.setView(edtAddress);
                builder.setIcon(R.drawable.ic_menu_send);
                final String tableId=GlobalData.getInstance().getCurrTable();
                if (tableId==null)
                    Toast.makeText(getBaseContext(),"Null",Toast.LENGTH_SHORT).show();
                else
                    builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            String desciption = edtAddress.getText().toString();
                            Request request = new Request(userId, tableId, orders, desciption);
                            //add to firebase
                            requestRef.child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(request);

                            //delete cart
                            GlobalData.getInstance().getCurrCart().clear();
                            Toast.makeText(getApplicationContext(),"Cảm ơn bạn đã đặt món <3",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();


            }
        });
        loadListRequest();

    }

    private void loadListRequest() {
        orders =GlobalData.getInstance().getCurrCart();
        listItemCart = new ArrayList<Item>(orders.items.values());
        adapter = new CartAdapter(listItemCart, this, this);
        recyclerView.setAdapter(adapter);

        //tinh tong tien
        int total=orders.getSubTotal();

        //format currency
        locale = new Locale("vi", "VN");
        fmt = NumberFormat.getCurrencyInstance(locale);
        tvTotal.setText(fmt.format(total));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void deleteItem(int pos) {
        Item removeItem = listItemCart.get(pos);
        orders.items.values().remove(removeItem);
        listItemCart.remove(pos);
        adapter.notifyDataSetChanged();
        tvTotal.setText(fmt.format(orders.getSubTotal()));
    }
}

