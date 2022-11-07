package com.example.findyoursherutleumi.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.findyoursherutleumi.R;

public class SendEmailFragment extends Fragment {

    EditText coordinatorEmail;
    EditText subject;
    EditText message;
    Button sendBtn;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
                // TODO: get to back fragment

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public static SendEmailFragment newInstance() {
        return new SendEmailFragment();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);

        coordinatorEmail = view.findViewById(R.id.send_email_address_coordinator);
        subject = view.findViewById(R.id.subject_email_service_name);
        message = view.findViewById(R.id.send_email_message);
        sendBtn = view.findViewById(R.id.send_email_btn);

        assert getArguments() != null;
        coordinatorEmail.setText(getArguments().getString("coordinator_email"));
        subject.setText(requireContext().getResources().getString(R.string.service) + ": " + getArguments().getString("service_title"));

        sendBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));  // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{coordinatorEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
            startActivity(Intent.createChooser(intent, requireContext().getResources().getString(R.string.open_with)));
        });

        return view;
    }


}