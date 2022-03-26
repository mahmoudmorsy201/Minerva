package com.example.minerva.ui.setting

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.minerva.R
import com.example.minerva.databinding.FragmentSettingBinding
import com.example.minerva.usersystem.LoginRegisterActivity
import com.example.minerva.util.AESEncyption
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel: SettingFragmentViewModel by viewModels()
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.logoutConstraintLayout.setOnClickListener() {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(resources.getString(R.string.logout))
            builder.setMessage(resources.getString(R.string.do_you_want_to_log_out))
            builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.setPositiveButton(resources.getString(R.string.logout)) { _, _ ->
                viewModel.clearSharedPreferences()
                requireActivity().finish()
                requireActivity().startActivity(
                    Intent(
                        requireActivity(),
                        LoginRegisterActivity::class.java
                    )
                )
            }
            builder.setCancelable(true)
            builder.show()
        }

        binding.changePasswordConstraintLayout.setOnClickListener() {
            showDialog()
        }

        return root
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext(), android.R.style.ThemeOverlay)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_change_password)

        val text1 = dialog.findViewById(R.id.passwordTextInputEditText) as TextInputEditText
        val text2 =
            dialog.findViewById(R.id.confirmPasswordTextInputEditText) as TextInputEditText
        val yesBtn = dialog.findViewById(R.id.changePasswordButton) as Button
        val noBtn = dialog.findViewById(R.id.cancelButton) as Button
        yesBtn.setOnClickListener {
            if (text1.text!!.isNotEmpty()) {
                if (text2.text!!.isNotEmpty()) {
                    if (text1.text.toString() == text2.text.toString()) {
                        lifecycle.coroutineScope.launch {
                            viewModel.changePassword(AESEncyption.encrypt(text2.text.toString())!!)
                            dialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.password_changed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        (dialog.findViewById(R.id.confirmPasswordTextInputLayout) as TextInputLayout).error =
                            resources.getString(R.string.password_dont_match)
                    }
                } else {
                    (dialog.findViewById(R.id.confirmPasswordTextInputLayout) as TextInputLayout).error =
                        resources.getString(R.string.invalid_password)
                }
            } else {
                (dialog.findViewById(R.id.passwordTextInputLayout) as TextInputLayout).error =
                    resources.getString(R.string.invalid_password)
            }
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}