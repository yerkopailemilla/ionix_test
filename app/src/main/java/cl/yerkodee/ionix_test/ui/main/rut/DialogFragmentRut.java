package cl.yerkodee.ionix_test.ui.main.rut;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import cl.yerkodee.ionix_test.R;
import cl.yerkodee.ionix_test.api.UrlIterator;
import cl.yerkodee.ionix_test.binding.AutoClearedValue;
import cl.yerkodee.ionix_test.databinding.DialogFragmentRutBinding;
import cl.yerkodee.ionix_test.di.Injectable;

public class DialogFragmentRut extends DialogFragment implements Injectable {

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject UrlIterator iterator;
    private DialogFragmentRutViewModel dialogFragmentRutViewModel;
    private AutoClearedValue<DialogFragmentRutBinding> binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this dialog fragment
        DialogFragmentRutBinding dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_fragment_rut, container, false);

        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFragmentRutViewModel = ViewModelProviders.of(this, viewModelFactory).get(DialogFragmentRutViewModel.class);

        binding.get().btnSearch.setOnClickListener(v -> {
            String rut = binding.get().etSearch.getText().toString();
            if (!rut.isEmpty()) {
                Log.d("VALIDATE_RUT", "No encryption -> " + rut);
                dialogFragmentRutViewModel.setSimpleRut(rut);
            } else {
                Toast.makeText(getContext(), "Debes ingresar un rut", Toast.LENGTH_LONG).show();
            }

        });

        binding.get().fab.setOnClickListener(v -> dismiss());

        showUserInfo(dialogFragmentRutViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void showUserInfo(DialogFragmentRutViewModel viewModel) {
        iterator.setCurrentBaseUrl("");
        Log.d("DET_BASE_URL", "DetailURL -> " + iterator.getCurrentBaseUrl());
        viewModel.getDetail().observe(this, detailResponseResource -> {
            binding.get().setDetail(detailResponseResource == null ? null : detailResponseResource.data);
            binding.get().executePendingBindings();
        });
    }

}
