package cl.yerkodee.ionix_test.binding;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.lang.reflect.Field;

import cl.yerkodee.ionix_test.R;

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showOrHide(View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setIcon")
    public static void setIconResource(TextView textView, String icon){
        try {
            //Get the ID
            Field resourceField = R.string.class.getDeclaredField("fa_" + icon.replace("-", "_"));
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            int resourceId = resourceField.getInt(resourceField);
            //Here you can use it as usual
            textView.setText(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
