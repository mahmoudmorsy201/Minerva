package com.example.minerva.usersystem.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.minerva.MainActivity
import com.example.minerva.R
import com.example.minerva.databinding.FragmentLoginBinding
import com.example.minerva.util.AESEncyption
import com.example.minvera.util.isValidEmail
import com.example.minvera.util.isValidPassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (viewModel.checkIfUserExits()) {
            navigateToMainScreen()
        }

        binding.signInButton.setOnClickListener() {
            if (isValidData()) {
                lifecycle.coroutineScope.launch {
                    viewModel.checkUserCredentials(
                        binding.emailTextInputEditText.text.toString(),
                        AESEncyption.encrypt(binding.passwordTextInputEditText.text.toString())!!
                    )
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(context, "UserNotFound", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "userFound ${it.email}", Toast.LENGTH_SHORT).show()
                if (binding.rememberMeCheckbox.isChecked) {
                    viewModel.saveUserDataInSharedPreferences(
                        binding.emailTextInputEditText.text.toString(),
                        AESEncyption.encrypt(binding.passwordTextInputEditText.text.toString())!!
                    )
                    navigateToMainScreen()
                }
            }
        }

        binding.signUpTextView.setOnClickListener() {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_signup)
        }

        return root
    }

    fun navigateToMainScreen() {
        requireActivity().startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().finish()
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