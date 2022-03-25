package com.example.minerva.usersystem.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.minerva.R
import com.example.minerva.databinding.FragmentLoginBinding
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

        //Eslam@eslam.com
        //asdasdasda
        binding.signInButton.setOnClickListener() {
            if (isValidData()) {
                lifecycle.coroutineScope.launch {
                    viewModel.checkUserCredentials(
                        binding.emailTextInputEditText.text.toString(),
                        binding.passwordTextInputEditText.text.toString()
                    )
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(context, "UserNotFound", Toast.LENGTH_SHORT).show()
                //todo show no userFound in the database
            } else {
                Toast.makeText(context, "userFound ${it.email}", Toast.LENGTH_SHORT).show()
                //todo navigate to main activity
            }
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