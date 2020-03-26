package cl.yerkodee.ionix_test.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Collections;

import javax.inject.Inject;

import cl.yerkodee.ionix_test.R;
import cl.yerkodee.ionix_test.api.UrlIterator;
import cl.yerkodee.ionix_test.databinding.ActivityMainBinding;
import cl.yerkodee.ionix_test.utilities.Constans;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject MainNavigationController controller;
    @Inject UrlIterator iterator;
    private MainActivityViewModel mainActivityViewModel;
    private CategoryAdapter categoryAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        categoryAdapter = new CategoryAdapter(this, (category, currentPosition) -> {
            if (currentPosition == 0) {
                controller.navigateToDialogRut();
            }
            Toast.makeText(MainActivity.this, "Click: " + category.getTitle(), Toast.LENGTH_SHORT).show();
        });

        binding.recyclerViewCategory.setAdapter(categoryAdapter);
        initCategoryList(mainActivityViewModel);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void initCategoryList(MainActivityViewModel viewModel) {
        iterator.setCurrentBaseUrl(Constans.CATEGORIES_BASE_URL);
        Log.d("CAT_BASE_URL", "CategoriesURL -> " + iterator.getCurrentBaseUrl());
        viewModel.getCategories().observe(this, listResource -> {
            binding.setCatResource(listResource);
            categoryAdapter.replace(listResource == null ? Collections.emptyList() : listResource.data);
            binding.executePendingBindings();
        });
    }

    public void developerMessage(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_contact);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        (dialog.findViewById(R.id.bt_close)).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
