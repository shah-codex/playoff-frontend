package com.shahdarshil.playoff.user.forgotpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shahdarshil.playoff.databinding.FragmentForgotPasswordBinding
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val args: ForgotPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater)

        val viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

        val email = args.email

        viewModel.sendOtp(email)

        binding.viewModel = viewModel

        viewModel.cancelButtonClicked.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().popBackStack()
                viewModel.onDoneClicking()
            }
        })

        viewModel.changeButtonClicked.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(validateInputs()) {
                    viewModel.updatePassword(
                        email,
                        binding.passwordEditTextForgotPassword.text.toString(),
                        binding.otpEditTextForgotPassword.text.toString()
                    )
                }
            }
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            it?.let {
                val message = if(it) {
                    "Successfully changed the password"
                } else {
                    "Failed to change the password"
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
                viewModel.onDoneClicking()
            }
        })

        return binding.root
    }

    private fun validateInputs(): Boolean {

        var valid = false

        if(binding.passwordEditTextForgotPassword.text.toString().trim().length >= 8) {
            valid = true
        } else {
            binding.passwordTextInputForgotPassword.error = "Password should at least be 8 characters long"
            return false
        }

        if(binding.passwordEditTextForgotPassword.text.toString().trim() == binding.confirmPasswordEditTextForgotPassword.text.toString().trim()) {
            valid = true
        } else {
            binding.confirmPasswordTextInputForgotPassword.error = "Password does not match"
            return false
        }

        return valid
    }
}