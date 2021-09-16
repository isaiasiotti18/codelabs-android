/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    
    // Get a reference to the binding object and inflate the fragment views.
    val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
      inflater, R.layout.fragment_sleep_tracker, container, false
    )
    
    val application = requireNotNull(this.activity).application
    
    // Create an instance of the ViewModel Factory.
    val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
    val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
    
    // Get a reference to the ViewModel associated with this fragment.
    val sleepTrackerViewModel = ViewModelProvider(this, viewModelFactory)
      .get(SleepTrackerViewModel::class.java)
    
    val adapter = SleepNightAdapter(SleepNightListener { nightId ->
      sleepTrackerViewModel.onSleepNightClicked(nightId)
    })
    
    binding.sleepList.adapter = adapter

    binding.sleepTrackerViewModel = sleepTrackerViewModel

    binding.lifecycleOwner = this

    sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
      if (it == true) { // Observed state is true.
        Snackbar.make(
          requireActivity().findViewById(android.R.id.content),
          getString(R.string.cleared_message),
          Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()

        sleepTrackerViewModel.doneShowingSnackbar()
      }
    })

    sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
      night?.let {

        // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
        this.findNavController().navigate(
          SleepTrackerFragmentDirections
            .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
        )

        sleepTrackerViewModel.doneNavigating()
      }
    })
    
    sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter.submitList(it)
      }
    })
  
    sleepTrackerViewModel.navigateToSleepDetail.observe(viewLifecycleOwner, Observer { night ->
      night?.let {
        this.findNavController().navigate(
          SleepTrackerFragmentDirections
            .actionSleepTrackerFragmentToSleepDetailFragment(night))
        sleepTrackerViewModel.onSleepDetailNavigated()
      }
    })
    
    return binding.root
  }
}