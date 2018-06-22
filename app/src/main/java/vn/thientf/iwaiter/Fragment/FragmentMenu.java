package vn.thientf.iwaiter.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.thientf.iwaiter.GlobalData;
import vn.thientf.iwaiter.R;

/**
 * Created by ChauHoangLong on 6/21/2018.
 */

public class FragmentMenu  extends Fragment{
    View root;
    private String resId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    RecyclerView rclMenu;
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

    private void setData() {
        database = FirebaseDatabase.getInstance();
        resId = "R111";

    }

    private void getData() {
        resId = GlobalData.getInstance().getCurrRes();
    }

    private void initView() {
        rclMenu=root.findViewById(R.id.rcl_menu);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rclMenu.setLayoutManager(layoutManager);
    }


}
