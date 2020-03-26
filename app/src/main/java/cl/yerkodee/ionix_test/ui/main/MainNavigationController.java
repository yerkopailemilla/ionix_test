package cl.yerkodee.ionix_test.ui.main;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import cl.yerkodee.ionix_test.ui.main.rut.DialogFragmentRut;

public class MainNavigationController {

    private final FragmentManager fragmentManager;

    @Inject
    public MainNavigationController(MainActivity mainActivity) {
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToDialogRut() {
        DialogFragmentRut newFragment = new DialogFragmentRut();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit();
    }

}
