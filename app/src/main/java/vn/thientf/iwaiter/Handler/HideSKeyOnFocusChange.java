package vn.thientf.iwaiter.Handler;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by ASUS on 20/06/2018.
 */

public class HideSKeyOnFocusChange implements View.OnFocusChangeListener {
    Context baseContext;

    public HideSKeyOnFocusChange(Context baseContext) {
        this.baseContext = baseContext;
    }

    private void hideKeyboard(View view, Context baseContext) {
        InputMethodManager inputMethodManager =(InputMethodManager)baseContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View view, boolean hasForcus) {
        if (!hasForcus){
            hideKeyboard(view,baseContext);
        }
    }
}
