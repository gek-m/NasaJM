package com.example.nasajm.ui.pages.mars

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nasajm.R
import com.example.nasajm.databinding.FragmentMarsBinding
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.viewBinding
import kotlinx.coroutines.flow.collect

class MarsFragment : Fragment(R.layout.fragment_mars) {

    private val viewBinding: FragmentMarsBinding by viewBinding(
        FragmentMarsBinding::bind
    )

    private val viewModel: MarsViewModel by viewModels {
        MarsFragmentFactory(
            application = requireActivity().application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchMarsPhotoUrl()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.url.collect {
                Glide.with(viewBinding.marsImage)
                    .load(it)
                    .into(viewBinding.marsImage)
            }
        }
    }
}

class MarsFragmentFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MarsViewModel(application, NasaRepositoryImp()) as T
}