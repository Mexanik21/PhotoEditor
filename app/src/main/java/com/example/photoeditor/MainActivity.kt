package com.example.photoeditor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.photoeditor.databinding.ActivityMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Objects

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var gso : GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var dataBaseReference: DatabaseReference
    companion object{
        lateinit var imgUrl:Uri
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        dataBaseReference = FirebaseDatabase.getInstance().reference

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this,gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null){
            var name = account.displayName
            var email = account.email
            val map = HashMap<String,Any>()
            map.put("name",name!!)
            map.put("email",email!!)
            dataBaseReference.child("Users").child(name).setValue(map)
        }

        binding.button.setOnClickListener{
            ImagePicker.Companion.with(this)
                .crop()
                .start()
        }

        binding.longOut.setOnClickListener{
            longOut()
        }

    }

    private fun longOut() {
        gsc.signOut().addOnCompleteListener{
            finish()
            startActivity(Intent(applicationContext,Sp::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imgUrl = data!!.data!!
            if (imgUrl.equals("")){

            }
            startActivity(Intent(this,FinalActivity::class.java))
        } else{
            Toast.makeText(this,"Something is wrong", Toast.LENGTH_SHORT).show()
        }
    }
}