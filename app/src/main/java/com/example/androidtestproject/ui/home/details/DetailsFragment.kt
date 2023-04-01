package com.example.androidtestproject.ui.home.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentDetailsBinding
import com.example.androidtestproject.databinding.FragmentHomeBinding
import com.example.androidtestproject.model.UserModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.userData
        data?.let {
            updateUI(data)
        }
    }

    private fun updateUI(userModel: UserModel) {
        binding.tvFirstNameValue.text = userModel.first_name
        binding.tvLastNameValue.text = userModel.last_name
        binding.tvEmailValue.text = userModel.email

        binding.ivUserImage.load(
            userModel.avatar
        ) {
            transformations(RoundedCornersTransformation(8f))
        }
    }

}