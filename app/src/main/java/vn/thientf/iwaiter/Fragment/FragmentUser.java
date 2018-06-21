package vn.thientf.iwaiter.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.thientf.iwaiter.R;

/**
 * Created by ChauHoangLong on 6/21/2018.
 */

public class FragmentUser extends android.app.Fragment{
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user,null);
        //---

        //-------------
        return rootView;
    }
}
