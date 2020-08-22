package com.example.thirdmovie.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.thirdmovie.R
import com.example.thirdmovie.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = MovieGridAdapter(MovieGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}