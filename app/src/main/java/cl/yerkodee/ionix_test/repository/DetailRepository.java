package cl.yerkodee.ionix_test.repository;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

import cl.yerkodee.ionix_test.AppThreadExecutor;
import cl.yerkodee.ionix_test.api.IonixApiInterceptor;
import cl.yerkodee.ionix_test.api.IonixService;
import cl.yerkodee.ionix_test.database.DetailDAO;
import cl.yerkodee.ionix_test.model.user.DetailResponse;
import cl.yerkodee.ionix_test.model.user.ItemResponse;
import cl.yerkodee.ionix_test.model.user.ValidateRutResponse;
import cl.yerkodee.ionix_test.repository.utils.NetworkBoundResource;
import cl.yerkodee.ionix_test.repository.utils.Resource;

@Singleton
public class DetailRepository  {

    private static final String KEY_IONIX = "ionix123456";
    private static final String ENCRYPTION_METHOD_DES = "DES";

    private final DetailDAO detailDAO;
    private final IonixService ionixService;
    private final AppThreadExecutor appThreadExecutor;

    @Inject
    public DetailRepository(DetailDAO detailDAO, IonixService ionixService,
                              AppThreadExecutor appThreadExecutor) {
        this.detailDAO = detailDAO;
        this.ionixService = ionixService;
        this.appThreadExecutor = appThreadExecutor;
    }

    public LiveData<Resource<DetailResponse>> loadDetailUser(String url, String simpleRut){
        return new NetworkBoundResource<DetailResponse, ValidateRutResponse>(appThreadExecutor){
            @Override
            protected boolean shouldFetch(DetailResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<DetailResponse> loadFromDb() {
                return detailDAO.findDetailUser();
            }

            @Override
            protected void saveCallResult(ValidateRutResponse response) {
                for (ItemResponse itemResponse : response.getResult().getItems()) {
                    detailDAO.insert(itemResponse.getDetail());
                }
            }

            @Override
            protected LiveData<IonixApiInterceptor<ValidateRutResponse>> createCall() {
                try {
                    DESKeySpec keySpec = new DESKeySpec(KEY_IONIX.getBytes(StandardCharsets.UTF_8));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_METHOD_DES);
                    SecretKey secretKey = keyFactory.generateSecret(keySpec);
                    byte[] byteText = simpleRut.getBytes(StandardCharsets.UTF_8);
                    Cipher cipher = Cipher.getInstance(ENCRYPTION_METHOD_DES);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    String cryptoRut = Base64.encodeToString(cipher.doFinal(byteText), Base64.DEFAULT);
                    cryptoRut = cryptoRut.replaceAll("\n", "");
                    Log.d("VALIDATE_RUT", "Encryption -> " + cryptoRut);
                    String completeUrl = url + cryptoRut;
                    return ionixService.validateRut(completeUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("VALIDATE_RUT", "Encryption error -> " + e.getMessage());
                    return ionixService.validateRut(null);
                }
            }

        }.asLiveData();
    }

}
