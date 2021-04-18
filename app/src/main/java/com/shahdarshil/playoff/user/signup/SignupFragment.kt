package com.shahdarshil.playoff.user.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.databinding.FragmentSignupBinding
import com.shahdarshil.playoff.user.isEmail

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.generateOtpPressed.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(validateInputs()) {
                    viewModel.callOtpGeneration(binding.emailEdittext.text.toString().trim())
                    binding.otpEdittext.isEnabled = true
                    binding.registerButton.isEnabled = true
                    binding.otpButton.isEnabled = false
                    binding.otpButton.text = getString(R.string.resend_text)
                    viewModel.onOtpGenerated()
                    Snackbar.make((view)!!, "OTP sent to " + binding.emailEdittext.text.toString(), Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.registerButton)
                        .show()
                }
            }
        })

        viewModel.signupPressed.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(validateInputs() && binding.otpEdittext.text.toString().length == 6 && !it) {
                    viewModel.onSignupDone()

                    Snackbar.make((view)!!, "Registered successfully", Snackbar.LENGTH_LONG)
                        .setAnchorView(binding.registerButton)
                        .show()
                    findNavController().popBackStack()
                } else {
                    viewModel.callRegisterUser(
                        binding.firstnameEdittext.text.toString().trim() + " " + binding.lastnameEdittext.text.toString().trim(),
                        binding.passwordEdittext.text.toString().trim(),
                        binding.emailEdittext.text.toString().trim(),
                        binding.otpEdittext.text.toString().trim()
                    )

                    viewModel.onSignupDone()
                    binding.otpButton.isEnabled = true
                }
            }
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().popBackStack()
                viewModel.onDoneNavigating()
            }
        })

        return binding.root
    }

    private fun validateInputs(): Boolean {

        var valid = false

        if(binding.firstnameEdittext.text.toString().trim().isNotEmpty() && !(binding.firstnameEdittext.text.toString().trim().contains(" "))) {
            valid = true
        } else {
            binding.firstnameEdittext.error = "Invalid First Name"
            return false
        }

        if(binding.lastnameEdittext.text.toString().trim().isNotEmpty() && !(binding.lastnameEdittext.text.toString().trim().contains(" "))) {
            valid = true
        } else {
            binding.lastnameEdittext.error = "Invalid Last Name"
            return false
        }

        if(binding.emailEdittext.text.toString().trim().isNotEmpty() && binding.emailEdittext.text.toString().trim().isEmail()) {
            valid = true
        } else {
            binding.emailEdittext.error = "Invalid E-Mail address"
            return false
        }

        if(binding.passwordEdittext.text.toString().trim().length >= 8) {
            valid = true
        } else {
            binding.textInputLayout4.error = "Password should at least be 8 characters long"
            return false
        }

        if(binding.passwordEdittext.text.toString().trim() == binding.confirmPasswordEdittext.text.toString().trim()) {
            valid = true
        } else {
            binding.textInputLayout5.error = "Password does not match"
            return false
        }

        return valid
    }
}