package com.example.helloworld

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld.model.UserObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class CreateAccountActivity : AppCompatActivity() {
    private var etUsername: EditText?=null
    private var etId: EditText?=null
    private var etEmail: EditText?=null
    private var etPassword: EditText?=null
    private var etConfirm: EditText?=null
    private var btnCreate: Button?=null
    private var mProgress:ProgressDialog?=null
    private var valid: Boolean?=null
    private var mAuth: FirebaseAuth?=null
    private var db: FirebaseFirestore?=null

    private val TAG= "CreateAccountActivity"

    private var username: String?=null
    private var id: String?=null
    private var email: String?=null
    private var pass: String?=null
    private var confirm: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()
    }

    private fun initialise(){
        etUsername= findViewById(R.id.et_username) as EditText
        etEmail= findViewById(R.id.et_email) as EditText
        etId= findViewById(R.id.et_id) as EditText
        etPassword =findViewById(R.id.et_password) as EditText
        etConfirm= findViewById(R.id.et_confirmPassword) as EditText
        btnCreate= findViewById<Button>(R.id.btn_create)
        mProgress= ProgressDialog(this)

        db=FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db!!.firestoreSettings = settings
        mAuth= FirebaseAuth.getInstance()

        btnCreate!!.setOnClickListener { createNewAccount() }
    }
    private fun setupCacheSize() {
        // [START fs_setup_cache]
        val settings = FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        db!!.firestoreSettings = settings
        // [END fs_setup_cache]
    }

    private fun createNewAccount() {
        username = etUsername?.text.toString()
        email = etEmail?.text.toString()
        id = etId?.text.toString()
        pass = etPassword?.text.toString()
        confirm = etConfirm?.text.toString()
        if (TextUtils.isEmpty(username) ) {
            etUsername?.setError("type your name")
            valid=false
        }
            else if(TextUtils.isEmpty(email)){
            etEmail?.setError("type your email")
            valid=false
        }
            else if(TextUtils.isEmpty(id)){
            etId?.setError("type your id")
            valid=false
        }
            else if(TextUtils.isEmpty(pass)){
            etPassword?.setError("type yoru password")
            valid=false
        }
            else if (TextUtils.isEmpty(confirm) ){
            etConfirm?.setError("confirm your password")
            valid=true
        }
            else if(!TextUtils.equals(pass,confirm)){
                Toast.makeText(this, "password doesn't confirmed",Toast.LENGTH_SHORT).show()
                etPassword?.setText("")
                etConfirm?.setText("")
                valid=false
        }
            else if(!TextUtils.isEmpty(pass) && pass?.length!! <7){
            Toast.makeText(this, "password too short!",Toast.LENGTH_SHORT).show()
            valid= false
        }
        else{

            Toast.makeText(this, "informations recived",Toast.LENGTH_SHORT).show()
            valid=true
        }
        mProgress!!.setMessage("Registering user...")
        mProgress!!.show()
        if(valid==true){
        mAuth!!
            .createUserWithEmailAndPassword(email!!,pass!!).addOnCompleteListener(this){
                task ->
                mProgress!!.hide()
                if(task.isSuccessful){
                    Log.d(TAG,"CreateUserWithEmail:Sucess")
                    val userId= mAuth!!.currentUser!!.uid
                     verifyEmail()
                    val testUser= UserObject(username=username,email = email,id = id,authId = userId)
                    // Add a new document with a generated ID
                    db!!.collection("user").document(userId)
                        .set(testUser)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added ")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                    updateUserUI()
                } else{
                    Log.w(TAG, "Create user fail",task.exception)
                    Toast.makeText(this@CreateAccountActivity,"Auth failed",Toast.LENGTH_SHORT).show()
                }

            }}

    }
    private fun updateUserUI(){
        val intent = Intent(this@CreateAccountActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    private fun verifyEmail(){
        val mUser= mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this){
            task ->
            if(task.isSuccessful){
                Toast.makeText(this@CreateAccountActivity,"Verification Email Send to"+mUser.getEmail(),Toast.LENGTH_SHORT).show()
            }
            else{
                Log.e(TAG,"Send Email Verification ",task.exception)
                Toast.makeText(this@CreateAccountActivity,"Failed to send email verification",Toast.LENGTH_SHORT).show()
            }
        }
    }


}