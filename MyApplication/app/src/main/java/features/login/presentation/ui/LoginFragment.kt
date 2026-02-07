package features.login.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import core.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import features.login.databinding.FragmentLoginBinding
import features.login.presentation.state.LoginUiState
import features.login.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUi()
        viewModel.loadLoginList()
    }

    private fun observeUi() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorGroup.visibility = View.GONE
                    binding.contentGroup.visibility = View.GONE
                }
                is LoginUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorGroup.visibility = View.GONE
                    binding.contentGroup.visibility = View.VISIBLE
                    // TODO: update UI with state.items
                }
                is LoginUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorGroup.visibility = View.VISIBLE
                    binding.contentGroup.visibility = View.GONE
                    binding.errorText.text = state.message
                }
                LoginUiState.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorGroup.visibility = View.GONE
                    binding.contentGroup.visibility = View.VISIBLE
                    // TODO: show empty state
                }
            }
        }
    }
}