package com.example.rolemanager.particlesList

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.R
import com.example.rolemanager.databinding.ItemParticleBinding


class ParticlesRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<ParticlesRecyclerViewAdapter.ParticleVH>() {

    private var particles: List<Particle> = arrayListOf()

    inner class ParticleVH(binding: ItemParticleBinding) :
        RecyclerView.ViewHolder(binding.root){
        val name = binding.particleName
        val image = binding.particleImage
        val card = binding.particleCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticleVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemParticleBinding.inflate(layoutInflater)
        return ParticleVH(binding)
    }

    override fun onBindViewHolder(holder: ParticleVH, position: Int) {
        val particle = particles[position]
        holder.name.text = particle.name

        val colorId = when(particle.family){
            Particle.Family.QUARK -> R.color.quarks
            Particle.Family.LEPTON -> R.color.leptons
            Particle.Family.SCALAR_BOSON -> R.color.higgs
            Particle.Family.GAUGE_BOSON ->  R.color.gauge_bosons
        }

        holder.image.setColorFilter(context.getColor(colorId))
        holder.card.setOnClickListener {
            showRenameDialog(particle, position)
            true
        }
    }

    private fun showRenameDialog(particle: Particle, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Particle name")

        val editText = EditText(context)
        editText.setText(particle.name)
        builder.setView(editText)

        builder.setPositiveButton("Ok"){ _, _ ->
            particle.name =  editText.text.toString()
            notifyItemChanged(position)
            Toast.makeText(context, "Se ha apretado Ok", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel"){_, _ ->
            Toast.makeText(context, "Se ha apretado Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    override fun getItemCount(): Int {
        return particles.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateParticlesList(particles: List<Particle>){
        this.particles = particles
        notifyDataSetChanged()
    }

}
