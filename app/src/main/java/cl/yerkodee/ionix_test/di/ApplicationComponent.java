package cl.yerkodee.ionix_test.di;

import android.app.Application;

import javax.inject.Singleton;

import cl.yerkodee.ionix_test.IonixApplication;
import cl.yerkodee.ionix_test.di.ui.MainActivityModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        ApplicationModule.class,
        RetrofitModule.class,
        MainActivityModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(IonixApplication ionixApplication);
}
