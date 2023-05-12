package com.example.rolemanager.fragments

import android.app.Dialog
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rolemanager.R
import com.example.rolemanager.model.LocalUser
import com.example.rolemanager.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_posts.view.*
import kotlinx.android.synthetic.main.layout_custom_dialog.*

class AddAdapter (val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<AddAdapter.ViewHolder>() {

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        firestoreDb = FirebaseFirestore.getInstance()

        val view = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false)

        view.botonet.setOnClickListener{
            val dialog: Dialog = Dialog(context)
            dialog.setContentView(R.layout.layout_custom_dialog)
            dialog.show()
            dialog.button.setOnClickListener{
                Toast.makeText(this.context, "You have applied for this job", Toast.LENGTH_LONG).show()
                //Delete the post
                firestoreDb.collection("posts").document(view.tvEmail.text.toString() + "_" + view.tvId.text).delete()
                dialog.cancel()
            }
            dialog.button3.setOnClickListener{
                dialog.cancel()
            }
        }
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.tvId.text = post.id.toString()
            itemView.tvEmail.text = post.emailUser
            itemView.tvUsername.text = post.user?.username
            itemView.tvDescription.text = post.description
            itemView.tvLocation.text = post.location
            Glide.with(context).load(post.imageUrl).into(itemView.ivPost)
            itemView.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
        }
    }
}