package com.orlanth23.colisnc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN = 300

    private var mFirebaseUser: FirebaseUser? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAuth = FirebaseAuth.getInstance()
        defineAuthListener()
    }

    override fun onResume() {
        super.onResume()
        if (mFirebaseAuth != null) {
            mAuthStateListener?.let { mFirebaseAuth!!.addAuthStateListener(it) }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mFirebaseAuth != null) {
            if (mAuthStateListener != null) {
                mFirebaseAuth!!.removeAuthStateListener(mAuthStateListener!!)
            }
        }
    }

    private fun getActualiteFromScreen(): ActualiteDto {
        val actualiteDto = ActualiteDto()
        actualiteDto.titre = (editTitre?.text.toString())
        actualiteDto.contenu = (editContenu?.text.toString())
        actualiteDto.isDismissable = (checkboxDismissable?.isChecked!!)
        actualiteDto.isDismissed = (false)
        actualiteDto.date = (DateConverter.nowDto)
        return actualiteDto
    }

    private fun raz() {
        editTitre?.text = null
        editContenu?.text = null
        checkboxDismissable?.isChecked = false
    }

    fun onValidate(view : View) {
        // On enregistre dans FIREBASE
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference.child(KEY_ACTUALITE)
        val newReference = databaseReference.push()
        newReference
                .setValue(getActualiteFromScreen())
                .addOnSuccessListener {
                    Toast.makeText(this, "Insertion réussie", Toast.LENGTH_LONG).show()
                    raz()
                }

    }

    fun sign(view: View) {
        if (mFirebaseAuth?.currentUser !== null) {
            signOut()
        } else {
            if (NetworkReceiver.checkConnection(this)) {
                // User is signed out
                signOut()

                val listProviders = ArrayList<AuthUI.IdpConfig>()
                listProviders.add(AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(listProviders)
                                .build(),
                        RC_SIGN_IN)
            } else {
                Toast.makeText(this, "Une connexion est requise pour se connecter", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signOut() {
        mFirebaseAuth?.signOut()
        mFirebaseUser = null
        profilName.text = null
        buttonSign.text = getString(R.string.login)
    }

    private fun defineAuthListener() {
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            mFirebaseUser = firebaseAuth.currentUser
            if (mFirebaseUser != null) {
                buttonSign.text = getString(R.string.logout)
                profilName.text = mFirebaseUser?.displayName
            } else {
                signOut()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                mFirebaseUser = mFirebaseAuth?.currentUser
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Erreur lors de la connexion", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
