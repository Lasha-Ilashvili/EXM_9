package com.example.exm_9.presentation.screen.main

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exm_9.databinding.BottomSheetBinding
import com.example.exm_9.databinding.FragmentMainBinding
import com.example.exm_9.presentation.base.BaseFragment
import com.example.exm_9.presentation.event.home.ImageEvent
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var imageLauncher: ActivityResultLauncher<Intent>

    private val viewModel: MainViewModel by viewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var bottomSheetBinding: BottomSheetBinding

    init {
        imageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::sendEvent)
    }

    private fun sendEvent(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.onEvent(ImageEvent.SetImage(result.data?.data))
        }
    }

    override fun setUp() {
        setBottomSheet()
    }

    private fun setBottomSheet() {
        bottomSheetBinding = BottomSheetBinding.inflate(layoutInflater)

        bottomSheetDialog = BottomSheetDialog(requireContext()).apply {
            setContentView(bottomSheetBinding.root)
        }
    }

    override fun setListeners() {
        binding.btnAddImage.setOnClickListener {
            bottomSheetDialog.show()
        }

        setBottomSheetListeners()
    }

    private fun setBottomSheetListeners() = with(bottomSheetBinding) {
        ibtnGallery.setOnClickListener {
            bottomSheetDialog.dismiss()

            imageLauncher.launch(
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageState.collect { state ->
                    state.data?.let {
                        binding.image.setImageURI(it)
                    }
                }
            }
        }
    }
}