package cl.yerkodee.ionix_test.ui.main.rut;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import cl.yerkodee.ionix_test.model.user.DetailResponse;
import cl.yerkodee.ionix_test.repository.DetailRepository;
import cl.yerkodee.ionix_test.repository.utils.Resource;
import cl.yerkodee.ionix_test.utilities.AbsentLiveData;
import cl.yerkodee.ionix_test.utilities.Constans;

public class DialogFragmentRutViewModel extends ViewModel {

    private final MutableLiveData<String> simpleRut;
    private final LiveData<Resource<DetailResponse>> detail;

    @Inject
    public DialogFragmentRutViewModel(DetailRepository detailRepository) {
        this.simpleRut = new MutableLiveData<>();
        detail = Transformations.switchMap(simpleRut, input -> {
            if (input.isEmpty()) {
                return AbsentLiveData.create();
            } else {
                return detailRepository.loadDetailUser(Constans.DETAIL_BASE_URL, input);
            }
        });
    }

    public LiveData<Resource<DetailResponse>> getDetail() {
        return detail;
    }

    public void retry() {
        String current = simpleRut.getValue();
        if (current != null && !current.isEmpty()) {
            simpleRut.setValue(current);
        }
    }

    public void setSimpleRut(String currentEncryptedRut) {
        String update = currentEncryptedRut;
        if (Objects.equals(simpleRut.getValue(), update)) {
            return;
        }
        simpleRut.setValue(update);
    }

}
