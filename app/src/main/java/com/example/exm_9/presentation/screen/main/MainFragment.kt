package com.example.exm_9.presentation.screen.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.Images
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
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var imageLauncher: ActivityResultLauncher<Intent>

    private val viewModel: MainViewModel by viewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var bottomSheetBinding: BottomSheetBinding

    init {
        imageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::sendEvent)
    }

    private fun sendEvent(result: ActivityResult) = with(result) {
        if (resultCode == Activity.RESULT_OK) {
            val bitmap = data?.data?.let {
                toBitmap(it)
            } ?: data?.let {
                toBitmap(it)
            }

            viewModel.onEvent(ImageEvent.SetImage(compressBitmap(bitmap)))
        }
    }

    private fun compressBitmap(bitmap: Bitmap?): Bitmap? {
        return with(ByteArrayOutputStream()) {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, this)
            BitmapFactory.decodeStream(ByteArrayInputStream(this.toByteArray()))
        }
    }

    private fun toBitmap(uri: Uri) = if (Build.VERSION.SDK_INT >= 29)
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(requireContext().contentResolver, uri)
        )
    else
        Images.Media.getBitmap(requireContext().contentResolver, uri)

    private fun toBitmap(data: Intent) = if (Build.VERSION.SDK_INT >= 33)
        data.getParcelableExtra("data", Bitmap::class.java)
    else
        data.getParcelableExtra("data")


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
                Intent(Intent.ACTION_PICK, Images.Media.INTERNAL_CONTENT_URI)
            )
        }

        ibtnCamera.setOnClickListener {
            bottomSheetDialog.dismiss()

            imageLauncher.launch(
                Intent(ACTION_IMAGE_CAPTURE)
            )
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageState.collect { state ->
                    state.data.let {
                        binding.image.setImageBitmap(it)
                    }
                }
            }
        }
    }
}