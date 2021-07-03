package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.models.User;
import com.ifixhubke.kibu_olx.databinding.FragmentRegisterBinding;
import com.ifixhubke.kibu_olx.utils.CheckInternet;
import com.ifixhubke.kibu_olx.utils.Utils;

import java.util.Objects;

import timber.log.Timber;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userID;
    String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String validPassword = "^" + //# start-of-string
            "(?=.*[0-9])" +      //# a digit must occur at least once
            "(?=.*[a-z])" +      //# a lower case letter must occur at least once
            "(?=.*[A-Z])" +      //# an upper case letter must occur at least once
            "(?=\\S+$)" +         //# no whitespace allowed in the entire string
            ".{8,}" +          //# anything, at least eight places though
            "$";                 //# end-of-string;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.linearLayoutLogin.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.register_to_login));

        /*binding.termsCondition.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_termsConditionFragment);
        });*/

        binding.buttonCreateAccount.setOnClickListener(v -> {

            Utils.hideSoftKeyboard(requireActivity());

            String mail = Objects.requireNonNull(binding.editTextEmail.getEditText()).getText().toString().trim();
            String pass = Objects.requireNonNull(binding.editTextPassword.getEditText()).getText().toString().trim();
            String first_Name = Objects.requireNonNull(binding.editTextFirstName.getEditText()).getText().toString().trim();
            String last_Name = Objects.requireNonNull(binding.editTextLastName.getEditText()).getText().toString().trim();
            String phone_Number = Objects.requireNonNull(binding.editTextPhoneNumber.getEditText()).getText().toString().trim();
            //Boolean agree_with_rules = binding.materialCheckBox.callOnClick();


            if (binding.editTextEmail.getEditText().getText().toString().isEmpty()) {
                binding.editTextEmail.setError("This field can't be empty!");
                return;
            } else if (!binding.editTextEmail.getEditText().getText().toString().matches(validEmail)) {
                binding.editTextEmail.setError("Invalid email!");
                return;
            } else if (binding.editTextPassword.getEditText().getText().toString().isEmpty()) {
                binding.editTextPassword.setError("This field can't be empty!");
                return;
            } else if (!(binding.editTextPassword.getEditText().getText().toString()).matches(validPassword)) {
                binding.editTextPassword.setError("Password is too weak.!"
                        + "\n Must contain" +
                        "\nat least 8 characters." + "\nAt least one digit"
                        + "\nAt least one lowercase letter and one uppercase letter");
                return;
            } else if (binding.editTextFirstName.getEditText().getText().toString().isEmpty()) {
                binding.editTextFirstName.setError("This field can't be empty!");
                return;
            } else if (binding.editTextFirstName.getEditText().getText().toString().length() >= 15) {
                binding.editTextFirstName.setError("Name is too long!");
                return;
            } else if (binding.editTextLastName.getEditText().getText().toString().isEmpty()) {
                binding.editTextLastName.setError("This field can't be empty!");
                return;
            } else if (binding.editTextLastName.getEditText().getText().toString().length() >= 15) {
                binding.editTextLastName.setError("Name is too long!");
                return;
            } else if (Objects.requireNonNull(binding.editTextPhoneNumber.getEditText().getText()).toString().isEmpty()) {
                binding.editTextPhoneNumber.setError("This field can't be empty!");
                return;
            } else {
                binding.registerProgressBar.setVisibility(View.VISIBLE);
            }
            //binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhoneNumber);
            //String phone_Number = binding.countryCodePicker.getFullNumberWithPlus();
            //Timber.d("%s", phone_Number);

            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(requireActivity(), task -> {
                if (task.isSuccessful()) {
                    //sending verification email
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    firebaseUser.sendEmailVerification().addOnSuccessListener(aVoid -> {
                        if (task.isSuccessful()) {
                            userID = firebaseAuth.getUid();
                            saveUserDetails(mail, first_Name, last_Name, phone_Number);
                            Navigation.findNavController(v).navigate(R.id.register_to_login);
                            Toast.makeText(requireContext(), "We have sent an email verification link to " + mail, Toast.LENGTH_LONG).show();
                            Timber.d("createUserWithEmailAndPassword: Success");
                            binding.registerProgressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(e -> Timber.d("Not created"));

                } else {
                    if (!(CheckInternet.isConnected(requireContext()))) {
                        Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        Timber.d("No Internet");
                        binding.registerProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        binding.registerProgressBar.setVisibility(View.INVISIBLE);
                        Timber.d("createUserWithEmailAndPassword: Failed");
                    }
                }
            });
        });

        return view;
    }

    public void saveUserDetails(String mail, String editTextFirstName, String lastName, String phoneNumber) {
        User user = new User(mail, editTextFirstName, lastName, phoneNumber);
        databaseReference.child(userID).setValue(user);
    }
}