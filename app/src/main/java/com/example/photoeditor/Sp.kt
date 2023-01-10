package com.example.photoeditor

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.photoeditor.databinding.ActivitySpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class Sp : AppCompatActivity() {


    lateinit var bd: ActivitySpBinding
//    lateinit var handler: Handler
    lateinit var gso : GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bd = ActivitySpBinding.inflate(layoutInflater)
        setContentView(bd.root)
        initViews()
    }

    private fun initViews() {

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this,gso)

        bd.btnGoogle.setOnClickListener {
            singIn()
        }

//        handler = Handler()
//        handler.postDelayed(
//            Runnable {
//                kotlin.run {
//                    startActivity(Intent(this,MainActivity::class.java))
//                }
//            }, 3000
//        )
    }

    private fun singIn() {
        startActivityForResult(Intent(gsc.signInIntent),100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(this,"in come", Toast.LENGTH_SHORT).show()
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                sendMainActivity()

            } catch (e: ApiException){
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this,"kirmadi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMainActivity() {
        finish()
        startActivity(Intent(this,MainActivity::class.java))
    }
}