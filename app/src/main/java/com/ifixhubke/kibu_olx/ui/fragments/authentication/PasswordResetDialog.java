package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ifixhubke.kibu_olx.databinding.DialogPasswordResetBinding;
import com.ifixhubke.kibu_olx.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PasswordResetDialog extends DialogFragment {

    DialogPasswordResetBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DialogPasswordResetBinding.inflate(inflater, container, false);

        //TODO: Set the background as transparent
        //this.getDialog().getWindow().setBackgroundDrawableResource();

        View view = binding.getRoot();
        binding.buttonReset.setOnClickListener(v -> {

            Utils.hideSoftKeyboard(requireActivity());

            if (TextUtils.isEmpty(Objects.requireNonNull(binding.editTextEmail.getEditText()).getText().toString())) {
                binding.editTextEmail.setError("Required");
                return;

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.getEditText().getText().toString()).matches()) {
                binding.editTextEmail.setError("Invalid email format");
                return;

            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(binding.editTextEmail.getEditText().getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show());
        });

        binding.textViewCancel.setOnClickListener(v -> {
            this.dismiss();
        });

        return view;
    }
}