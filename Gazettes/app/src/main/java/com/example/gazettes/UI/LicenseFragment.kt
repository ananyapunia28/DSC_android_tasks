package com.example.gazettes.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.gazettes.R
import com.example.gazettes.databinding.FragmentAboutBinding
import com.example.gazettes.databinding.FragmentLicenseBinding


class LicenseFragment : Fragment() {
    private lateinit var binding: FragmentLicenseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLicenseBinding.inflate(layoutInflater)
        return binding.root
    }


}