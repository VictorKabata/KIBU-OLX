package com.ifixhubke.kibu_olx.ui.fragments.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ifixhubke.kibu_olx.databinding.FragmentSellTwoBinding

class SellFragmentTwo : Fragment() {
    var binding: FragmentSellTwoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellTwoBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.root
    }
}