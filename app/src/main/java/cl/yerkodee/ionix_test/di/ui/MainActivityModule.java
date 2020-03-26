package cl.yerkodee.ionix_test.di.ui;

import cl.yerkodee.ionix_test.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = MainFragmentBuilderModule.class)
public abstract class MainActivityModule {
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
