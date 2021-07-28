package com.example.chatapp.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.fragments.home_fragment.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore


const val RC_SIGN_IN = 123

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val firebase = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

            setContent {
                Button(onClick = {
                    val signInIntent: Intent = mGoogleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)

                }) {
                    Text(text = "zeca")
                }

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val user = UserModel(
                account.id!!,
                account.displayName!!,
                account.photoUrl!!.toString(),
                account.email!!
            )


            viewModel.addUser(user)
            // Signed in successfully, show authenticated UI.
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment(
                id = account.id!!,
                name = account.displayName!!,
                photoUrl = account.photoUrl!!.toString(),
                email = account.email!!
            ))
        } catch (e: ApiException) {
            Toast.makeText(context, "Sign in failed", Toast.LENGTH_LONG).show()
        }
    }

}