package com.example.whatsapp_clone.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.load.ImageHeaderParser.ImageType
import com.example.whatsapp_clone.MainActivity
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.databinding.ActivityProfileBinding
import com.example.whatsapp_clone.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImage: Uri
    private lateinit var dialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply{
                type = "image/*"
            }
            startActivityForResult(intent,1)
        }

        binding.continueBtn.setOnClickListener {
            if(binding.userName.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_LONG).show()
            }
            else if(selectedImage == null){
                Toast.makeText(this,"Please update your profile Image",Toast.LENGTH_LONG).show()
            }else{
                uploadData()
            }
        }

    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().toString())
        reference.putFile(selectedImage).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {
                    task -> uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(auth.uid.toString(),binding.userName.text.toString(),
            auth.currentUser?.phoneNumber.toString(),imgUrl)

        database.reference.child("Users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data inserted", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!= null) {

            if(data.data != null){

                selectedImage = data.data!!
                binding.userImage.setImageURI(selectedImage)

            }
        }

    }
}