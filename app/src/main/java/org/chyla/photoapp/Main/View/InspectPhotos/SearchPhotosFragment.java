package org.chyla.photoapp.Main.View.InspectPhotos;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchPhotosFragment extends Fragment {

    public final static String TAG = "SearchPhotosFragment";

    private MainPresenter presenter;

    @BindView(R.id.button_search)
    Button searchButton;

    @BindView(R.id.edittext_tags)
    EditText tagsEditText;

    public SearchPhotosFragment() {
    }

    public void setPresenter(final MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspect_photos, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.button_search)
    void searchPhotos() {
        Log.d(TAG, "Search button clicked.");
        presenter.inspectPhotos(tagsEditText.getText().toString());
    }

}
