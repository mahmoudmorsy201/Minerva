package com.example.minerva.usersystem.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.minerva.R
import com.example.minerva.data.model.User
import com.example.minerva.databinding.FragmentSignUpBinding
import com.example.minerva.util.AESEncyption
import com.example.minvera.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signInButton.setOnClickListener {
            if (isValidData()) {

                lifecycleScope.launch {
                    viewModel.addNewUser(
                        User(
                            binding.emailTextInputEditText.text.toString(),
                            binding.userNameTextInputEditText.text.toString(),
                            binding.mobileTextInputEditText.text.toString(),
                            AESEncyption.encrypt(binding.passwordTextInputEditText.text.toString())!!
                        )
                    )
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidData(): Boolean {
        var result = false
        binding.emailTextInputLayout.error = ""
        binding.passwordTextInputLayout.error = ""
        binding.mobileTextInputLayout.error = ""
        binding.usernameTextInputLayout.error = ""

        if (isValidUserName(binding.userNameTextInputEditText.text.toString())) {
            if (isValidEmail(binding.emailTextInputEditText.text.toString())) {
                if (isValidMobileNumber(binding.mobileTextInputEditText.text.toString())) {
                    if (isValidPassword(binding.passwordTextInputEditText.text.toString())) {
                        result = true
                    } else {
                        binding.passwordTextInputLayout.error =
                            resources.getString(R.string.invalid_password)
                    }
                } else {
                    binding.mobileTextInputLayout.error =
                        resources.getString(R.string.invalid_mobile_number)
                }
            } else {
                binding.emailTextInputLayout.error = resources.getString(R.string.invalid_email)
            }
        } else {
            binding.usernameTextInputLayout.error = resources.getString(R.string.invalid_user_name)
        }

        return result
    }


}