package vn.thientf.iwaiter.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;

import vn.thientf.iwaiter.R;

/**
 * Created by ChauHoangLong on 6/21/2018.
 */

public class FragmentMenu  extends Fragment{
    View root;
    private String tableId,resId;
    FirebaseDatabase database;

    RecyclerView rclMenu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root;
        root=inflater.inflate(R.layout.fragment_menu,null);
        initView();
        getData();
        setData();
        return root;
    }

    private void setData() {
    }

    private void getData() {
        Bundle bundle=getArguments();
        resId=bundle.getString("resId");
        tableId=bundle.getString("tableId");
    }

    private void initView() {
        rclMenu=root.findViewById(R.id.rcl_menu);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rclMenu.setLayoutManager(layoutManager);
    }
}
