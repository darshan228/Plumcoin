package com.example.plumcoin.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.plumcoin.R
import com.example.plumcoin.databinding.ActivityLoginBinding
import com.example.plumcoin.ui.main.MainActivity
import com.example.plumcoin.utils.shortToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginNavigator {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initCompat()
    }

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Initialize components
     */
    private fun initCompat() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.loginSheetLayout.bottomSheet)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        viewModel.navigator = this

        binding.btnStart.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.loginSheetLayout.bottomSheet.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.loginSheetLayout.clApple.setOnClickListener {
            shortToast(getString(R.string.error_no_apple_login))
        }

        binding.loginSheetLayout.clLogin.setOnClickListener {
        }

        binding.loginSheetLayout.clGoogle.setOnClickListener {
//            if (isUserSignedIn()) signOut()
//            signIn()
            openMainActivity()
        }
    }

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Check user is logged in
     */
    private fun isUserSignedIn(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null

    }

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Start google signing intent
     */
    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleSignInResult(task)
        }
    }


    /**
     * Created by Darshi on 9/20/2022.
     * Desc: Logout google logged in user
     */
    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
            }
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Navigate user to main activity
     */
    override fun openMainActivity() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Show short toast message
     */
    override fun showToastMessage(message: String) {
        shortToast(message)
    }

    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val RC_SIGN_IN = 100
        const val TAG = "LoginActivity"
    }
}