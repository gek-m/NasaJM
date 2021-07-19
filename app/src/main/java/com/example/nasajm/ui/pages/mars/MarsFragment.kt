package com.example.nasajm.ui.pages.mars

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.example.nasajm.R
import com.example.nasajm.databinding.FragmentMarsBinding
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.viewBinding
import kotlinx.coroutines.flow.collect

class MarsFragment : Fragment(R.layout.fragment_mars) {

    var isClicked = false

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

        viewBinding.marsImage.setOnClickListener {
            isClicked = !isClicked
            TransitionManager.beginDelayedTransition(
                viewBinding.root, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            viewBinding.marsImage.apply {
                scaleType =
                    if (isClicked) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
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