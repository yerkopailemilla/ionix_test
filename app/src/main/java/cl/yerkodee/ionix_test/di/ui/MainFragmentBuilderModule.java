package cl.yerkodee.ionix_test.di.ui;

import cl.yerkodee.ionix_test.ui.main.rut.DialogFragmentRut;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract DialogFragmentRut contributeDialogFragmentRut();
}
