package com.example.rolemanager.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rolemanager.databinding.FragmentMenuBinding
import com.example.rolemanager.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private const val TAG = "MenuFragment"
class MenuFragment: Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: AddAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)

        // Create the layout file wich represents one post
        // Create data source
        posts = mutableListOf()
        // Create the adapter
        adapter = AddAdapter(requireContext(), posts)
        // Bind the adapter and layout manager to the RV
        binding.rvPosts.adapter = adapter
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())

        firestoreDb = FirebaseFirestore.getInstance()
        val postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener{ snapshot, exception ->
            if (exception != null || snapshot == null){
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList){
                Log.e(TAG, "Post ${post}")
            }
        }

        return binding.root
    }

}