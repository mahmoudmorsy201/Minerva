package com.example.minerva.usersystem.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.minerva.R
import com.example.minerva.databinding.FragmentLoginBinding
import com.example.minvera.util.isValidEmail
import com.example.minvera.util.isValidPassword

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signInButton.setOnClickListener() {
            isValidData()
        }

        binding.signUpTextView.setOnClickListener() {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_signup)
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
        if (isValidEmail(binding.emailTextInputEditText.text.toString())) {
            if (isValidPassword(binding.passwordTextInputEditText.text.toString())) {
                result = true
            } else {
                binding.passwordTextInputLayout.error =
                    resources.getString(R.string.invalid_password)
            }
        } else {
            binding.emailTextInputLayout.error = resources.getString(R.string.invalid_email)
        }
        return result
    }


}