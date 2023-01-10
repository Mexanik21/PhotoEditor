package com.example.photoeditor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import com.example.photoeditor.MainActivity.Companion.imgUrl
import com.example.photoeditor.databinding.ActivityFinalBinding


class FinalActivity : AppCompatActivity() {
    lateinit var binding:ActivityFinalBinding
    lateinit var inputImageUri:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dsPhotoEditorIntent = Intent(this, DsPhotoEditorActivity::class.java)
        dsPhotoEditorIntent.data = imgUrl
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "My Editor");

//        val toolsToHide = arrayListOf(DsPhotoEditorActivity.TOOL_ORIENTATION,
//            DsPhotoEditorActivity.TOOL_CROP)
//
//        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,toolsToHide);


        startActivityForResult(dsPhotoEditorIntent, 200);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var outputUri:Uri?
            outputUri = Uri.EMPTY
            when (requestCode) {
                200 -> outputUri = data!!.data!!
            }
            binding.imgview.setImageURI(outputUri)
            binding.btnShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                val uri = Uri.parse(outputUri.toString())
                shareIntent.type = "image/jpeg"
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(shareIntent, "Share image using"))
            }
        }
    }

}