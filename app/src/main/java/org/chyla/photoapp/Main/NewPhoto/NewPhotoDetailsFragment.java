package org.chyla.photoapp.Main.NewPhoto;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.chyla.photoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPhotoDetailsFragment extends Fragment {

    private NewPhotoCallback newPhotoCallback;

    @BindView(R.id.edittext_title)
    EditText titleEditText;

    @BindView(R.id.edittext_description)
    EditText descriptionEditText;

    public NewPhotoDetailsFragment() {
    }

    public void setNewPhotoCallback(NewPhotoCallback callback) {
        newPhotoCallback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_photo_details, container, false);

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

    @OnClick(R.id.button_save)
    void onSaveButton() {
        if (titleEditText.getText().toString().length() > 0) {
            newPhotoCallback.onCreateNewPhotoCallback(titleEditText.getText().toString(), descriptionEditText.getText().toString());
        }
        else {
            titleEditText.setError("Can't be empty.");
        }
    }

}
