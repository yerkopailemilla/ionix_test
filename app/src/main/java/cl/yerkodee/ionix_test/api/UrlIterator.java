package cl.yerkodee.ionix_test.api;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UrlIterator {

    private String currentBaseUrl;

    @Inject
    public UrlIterator() { }

    public String getCurrentBaseUrl() {
        return currentBaseUrl;
    }

    public void setCurrentBaseUrl(String currentBaseUrl) {
        this.currentBaseUrl = currentBaseUrl;
    }

    public void clear(){
        this.currentBaseUrl = null;
    }

}
