package com.example.exm_9.presentation.screen.main

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.exm_9.databinding.FragmentMainBinding
import com.example.exm_9.presentation.base.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun setUp() {

        val imageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    binding.image.setImageURI(it.data?.data)
                }
            }

        binding.btnGallery.setOnClickListener {
            imageLauncher.launch(
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }
    }
}