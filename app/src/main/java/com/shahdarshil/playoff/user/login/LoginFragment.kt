package com.shahdarshil.playoff.user.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shahdarshil.playoff.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLoginBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing here.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing here.
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkEmail(s.toString())
            }
        })

        viewModel.navigateToSignup.observe(viewLifecycleOwner, Observer<Boolean> {
            if(it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
                viewModel.onDoneNavigating()
            }
        })

        viewModel.emailIsValid.observe(viewLifecycleOwner, Observer<Boolean> {
            if(!it) {
                binding.emailTextfieldLayout.error = "Please enter a valid email address"
            } else {
                binding.emailTextfieldLayout.isErrorEnabled = false
            }
        })

        viewModel.login.observe(viewLifecycleOwner, Observer<Boolean> {
            val email = binding.emailEditText.text?.trim().toString()
            val password = binding.passwordEditText.text?.trim().toString()

            if(it && email.isNotEmpty() && password.isNotEmpty() && viewModel.emailIsValid.value!!) {
                viewModel.validateUserDetails(email, password)
                viewModel.revokeLoginButton()
            }
        })


        viewModel.loginSuccessful.observe(viewLifecycleOwner, Observer<Boolean?> {
            it?.let {
                if (it) {
                    val directions = LoginFragmentDirections.actionLoginFragmentToDetailActivity()
                    findNavController().navigate(directions)
                } else {
                    binding.passwordEditText.setText("")
                    Snackbar.make((view)!!, "Invalid username or password", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.signupButton)
                        .show()
                }
            }
        })

        return binding.root
    }
}