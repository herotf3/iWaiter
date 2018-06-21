package vn.thientf.iwaiter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.thientf.iwaiter.Fragment.FragmentUser;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        FragmentUser fragmentUser=new FragmentUser();
        replaceFragment(fragmentUser);


    }
    void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container2,fragment)
                .commit();
    }
}
