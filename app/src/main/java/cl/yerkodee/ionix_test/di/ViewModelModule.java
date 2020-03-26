package cl.yerkodee.ionix_test.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import cl.yerkodee.ionix_test.ui.main.MainActivityViewModel;
import cl.yerkodee.ionix_test.ui.main.rut.DialogFragmentRutViewModel;
import cl.yerkodee.ionix_test.viewmodel.IonixViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DialogFragmentRutViewModel.class)
    abstract ViewModel bindDialogFragmentRutViewModel(DialogFragmentRutViewModel dialogFragmentRutViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(IonixViewModelFactory factory);

}
