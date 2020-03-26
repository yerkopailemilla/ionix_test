package cl.yerkodee.ionix_test.repository.utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Objects;

import cl.yerkodee.ionix_test.AppThreadExecutor;
import cl.yerkodee.ionix_test.api.IonixApiInterceptor;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppThreadExecutor appThreadExecutor;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppThreadExecutor appThreadExecutor) {
        this.appThreadExecutor = appThreadExecutor;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (NetworkBoundResource.this.shouldFetch(data)) {
                NetworkBoundResource.this.fetchFromNetwork(dbSource);
                Log.d("NETWORK_BOUND_RESOURCE", "fetchFromNetwork: " + "true");
            } else {
                Log.d("NETWORK_BOUND_RESOURCE", "fetchFromNetwork: " + "false");
                result.addSource(dbSource, (ResultType newData) -> {
                    NetworkBoundResource.this.setValue(Resource.success(newData));
                });
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<IonixApiInterceptor<RequestType>> service = createCall();
        result.addSource(dbSource, newData -> NetworkBoundResource.this.setValue(Resource.loading(newData)));
        result.addSource(service, response -> {
            result.removeSource(service);
            result.removeSource(dbSource);
            if (response.code == 200 && response.isSuccessful()) {
                Log.d("NETWORK_BOUND_RESOURCE", "successResponse: code-> " + response.code);
                appThreadExecutor.diskIO().execute(() -> {
                    NetworkBoundResource.this.saveCallResult(NetworkBoundResource.this.processResponse(response));
                    appThreadExecutor.mainThread().execute(() -> {
                        result.addSource(NetworkBoundResource.this.loadFromDb(),
                                newData -> NetworkBoundResource.this.setValue(Resource.success(newData)));
                    });
                });
            } else {
                Log.d("NETWORK_BOUND_RESOURCE", "failedResponse: code-> " + response.code);
                Log.d("NETWORK_BOUND_RESOURCE", "failedResponse: error-> " + response.errorMessage);
                Log.d("NETWORK_BOUND_RESOURCE", "failedResponse: body-> " + response.body);
                onFetchFailed();
                result.addSource(dbSource, newData -> setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(IonixApiInterceptor<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    @MainThread
    protected abstract LiveData<IonixApiInterceptor<RequestType>> createCall();

}
