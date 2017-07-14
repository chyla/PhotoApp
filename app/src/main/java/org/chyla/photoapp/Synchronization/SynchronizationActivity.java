package org.chyla.photoapp.Synchronization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.chyla.photoapp.Main.MainActivity;
import org.chyla.photoapp.R;
import org.chyla.photoapp.Synchronization.DependencyInjection.DaggerSynchronizationComponent;
import org.chyla.photoapp.Synchronization.DependencyInjection.SynchronizationComponent;
import org.chyla.photoapp.Synchronization.DependencyInjection.SynchronizationModule;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenter;

public class SynchronizationActivity extends AppCompatActivity implements View {

    private SynchronizationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);

        setupInjection();

        presenter.synchronize();
    }

    private void setupInjection() {
        presenter = getPresenter();
    }

    public SynchronizationPresenter getPresenter() {
        SynchronizationComponent component = DaggerSynchronizationComponent
                .builder()
                .synchronizationModule(new SynchronizationModule(this, this))
                .build();

        return component.getPresenter();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

}
