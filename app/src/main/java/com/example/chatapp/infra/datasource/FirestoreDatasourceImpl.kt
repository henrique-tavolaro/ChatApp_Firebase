package com.example.chatapp.infra.datasource

import android.util.Log
import androidx.lifecycle.Transformations.map
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.infra.repositories.USERS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreDatasource {

    override suspend fun addUser(user: UserModel) {
        firestore.collection(USERS)
            .document(user.id!!)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("TAG", "success")
            }

    }

    @ExperimentalCoroutinesApi
    override fun getAllUsers() = callbackFlow{
//        val list = mutableListOf<UserModel>()
        val collection = firestore.collection(USERS)

        val snapshotListener = collection.addSnapshotListener() { snapshot, e ->
            this.trySend(snapshot).isSuccess

//                e?.let {
//                    return@addSnapshotListener
//                }
//                snapshot?.let {
//                    for (i in it) {

//                        i.toObject(UserModel::class.java)
//                        Log.d("TAG2", result.toString())
//                        list.add(result)
//                    }
//                }
            }

        awaitClose {
            snapshotListener.remove()
        }
//            }
//           .get()
//            .addOnSuccessListener {
//                for(i in it){
//                    val result = i.toObject(UserModel::class.java)
//                    Log.d("TAG2", result.toString())
//                    list.add(result)
//                }
//            }

//            .await()
//        return list
    }


}