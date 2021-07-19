package com.example.nasajm.ui.pages.earth

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
import com.example.nasajm.databinding.FragmentEarthBinding
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.viewBinding
import kotlinx.coroutines.flow.collect

class EarthFragment : Fragment(R.layout.fragment_earth) {

    private val viewBinding: FragmentEarthBinding by viewBinding(
        FragmentEarthBinding::bind
    )

    var isClicked = false

    private val viewModel: EarthViewModel by viewModels {
        EarthFragmentFactory(
            application = requireActivity().application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchEarthPhotoUrl()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.url.collect {
                Glide.with(viewBinding.earthImage)
                    .load(it)
                    .into(viewBinding.earthImage)
            }
        }

        viewBinding.earthImage.setOnClickListener {
            isClicked = !isClicked
            TransitionManager.beginDelayedTransition(
                viewBinding.earthFragment, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = viewBinding.earthImage.layoutParams
            //params.height = if (isClicked) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            viewBinding.earthImage.apply {
                layoutParams = params
                scaleType =
                    if (isClicked) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }
    }
}

class EarthFragmentFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        EarthViewModel(application, NasaRepositoryImp()) as T
}