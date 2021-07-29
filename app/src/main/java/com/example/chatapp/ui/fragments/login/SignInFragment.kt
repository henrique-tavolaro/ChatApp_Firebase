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
import android.os.Handler
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.composables.GoogleButton
import com.example.chatapp.ui.fragments.home_fragment.HomeViewModel
import com.example.chatapp.ui.theme.ChatAppTheme
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
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
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
                ChatAppTheme() {
                    val state = remember { mutableStateOf(false)}

                    val handler = Handler()
                    handler.postDelayed({
                        val account = GoogleSignIn.getLastSignedInAccount(context)
                        if (account != null) {
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment(
                                id = account.id!!,
                                name = account.displayName!!,
                                photoUrl = account.photoUrl!!.toString(),
                                email = account.email!!
                            ))
                        } else {
                            state.value = true
                        }
                    }, 3000)


                    Column(
                        modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                        Text("ChatApp", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.fillMaxHeight(0.4f))
                        AnimatedVisibility(visible = state.value) {
                            GoogleButton(
                                onClick = {
                                    val signInIntent: Intent = mGoogleSignInClient.signInIntent
                                    startActivityForResult(signInIntent, RC_SIGN_IN)
                                }
                            )

                        }

                    }
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