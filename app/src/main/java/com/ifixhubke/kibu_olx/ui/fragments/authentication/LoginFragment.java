package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentLoginBinding;
import com.ifixhubke.kibu_olx.utils.CheckInternet;
import com.ifixhubke.kibu_olx.utils.Utils;

import timber.log.Timber;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        binding.linearLayoutResetPassword.setOnClickListener(v -> {
            PasswordResetDialog resetFragment = new PasswordResetDialog();
            resetFragment.show(getFragmentManager(), "dialog_password_reset");
        });

        binding.linearLayoutCreateAccount.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.login_to_register));

        binding.buttonLogin.setOnClickListener(v -> {

            Utils.hideSoftKeyboard(requireActivity());

            String mail = binding.editTextEmail.getEditText().getText().toString().trim();
            String password = binding.editTextPassword.getEditText().getText().toString().trim();

            if (TextUtils.isEmpty(binding.editTextEmail.getEditText().getText().toString())) {
                binding.editTextEmail.setError("Field can't be empty!!");
                return;
            } else if (TextUtils.isEmpty(binding.editTextPassword.getEditText().getText().toString())) {
                binding.editTextPassword.setError("Field can't be empty!!");
                return;
            } else if (!(CheckInternet.isConnected(requireContext()))) {
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                Timber.d("No Internet");
            } else {
                binding.progressBarLogin.setVisibility(View.VISIBLE);
                binding.editTextEmail.setEnabled(false);
                binding.editTextPassword.setEnabled(false);
                binding.buttonLogin.setEnabled(false);
                binding.buttonLogin.setAlpha(.5f);
            }
            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                            Navigation.findNavController(v).navigate(R.id.login_to_home);
                            Timber.d("signInWithEmailAndPassword: success");
                            binding.progressBarLogin.setVisibility(View.INVISIBLE);
                        } else {
                            binding.progressBarLogin.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Verify you email first", Toast.LENGTH_SHORT).show();
                            binding.editTextEmail.getEditText().setText("");
                            binding.editTextPassword.getEditText().setText("");
                        }
                    } else {
                        Toast.makeText(getContext(), "Something Went Wrong.\n please check your details and try again", Toast.LENGTH_SHORT).show();
                        Timber.d("signInWithEmailAndPassword: Failed");
                        binding.progressBarLogin.setVisibility(View.INVISIBLE);
                        binding.editTextEmail.setEnabled(true);
                        binding.editTextPassword.setEnabled(true);
                        binding.buttonLogin.setEnabled(true);
                        binding.buttonLogin.setAlpha(1f);
                    }
                }
            });

        });

        return view;
    }
}