                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             package com.example.nasajm.ui.main

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nasajm.MainActivity
import com.example.nasajm.R
import com.example.nasajm.databinding.MainFragmentBinding
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.ui.bottomNav.BottomNavigationDrawerFragment
import com.example.nasajm.ui.settings.SettingsFragment
import com.example.nasajm.util.setDateInString
import com.example.nasajm.util.visibleOrGone
import com.example.nasajm.viewBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
        const val CHIP_DATE_PATTERN = "dd-MM-yyyy"
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewBinding: MainFragmentBinding by viewBinding(
        MainFragmentBinding::bind
    )

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            application = requireActivity().application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchPictureOfTheDay(0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))


        viewBinding.chipToday.setOnClickListener {
            viewModel.fetchPictureOfTheDay(0)
        }

        viewBinding.chipYesterday.text = setDateInString(1, CHIP_DATE_PATTERN)
        viewBinding.chipYesterday.setOnClickListener {
            viewModel.fetchPictureOfTheDay(1)
        }

        viewBinding.chipBeforeYesterday.text = setDateInString(2, CHIP_DATE_PATTERN)
        viewBinding.chipBeforeYesterday.setOnClickListener {
            viewModel.fetchPictureOfTheDay(2)
        }

        viewBinding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${viewBinding.inputEditText.text.toString()}")
            })
        }
        setBottomAppBar(view)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loading.collect {
                viewBinding.progressBar.visibleOrGone(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.data.collect {
                it.url?.let { url ->
                    Glide.with(viewBinding.imageView)
                        .load(url)
                        .into(viewBinding.imageView)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                toast(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")

            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SettingsFragment()).addToBackStack(null).commit()

            R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(
                        requireActivity().supportFragmentManager,
                        "tag"
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        with(viewBinding) {
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    Handler().postDelayed({
                        bottomAppBar.navigationIcon =
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_hamburger_menu_bottom_bar
                            )
                        bottomAppBar.fabAlignmentMode =
                            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                        fab.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_plus_fab
                            )
                        )
                    }, 300)

                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}

class MainViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(application, NasaRepositoryImp()) as T
}