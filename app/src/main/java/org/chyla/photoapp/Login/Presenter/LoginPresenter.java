package org.chyla.photoapp.Login.Presenter;

import org.chyla.photoapp.Login.Model.Event.CancelledEvent;
import org.chyla.photoapp.Login.Model.Event.ErrorEvent;
import org.chyla.photoapp.Login.Model.Event.SuccessEvent;

public interface LoginPresenter {
    void onStart();
    void onStop();

    void authenticate(String username, String password);

    void onSuccessEvent(SuccessEvent event);
    void onErrorEvent(ErrorEvent event);
    void onCancellEvent(CancelledEvent event);
}
