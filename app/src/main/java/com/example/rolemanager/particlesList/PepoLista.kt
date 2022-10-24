package com.example.rolemanager.particlesList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rolemanager.databinding.ActivityPepoListaBinding

class PepoLista : AppCompatActivity() {

    private lateinit var binding: ActivityPepoListaBinding

    private val particles = listOf("Quark Up",
        "Quark Charm",
        "Quark Top",
        "Quark Down",
        "Quark Strange",
        "Quark Bottom",
        "Electron",
        "Muon",
        "Tau",
        "Electron neutrino",
        "Muon neutrino",
        "Tau neutrino",
        "Gluon",
        "Photon",
        "Z boson",
        "W boson",
        "Higgs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPepoListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.particlesRecyclerViews.adapter = ParticlesRecyclerViewAdapter(particles)
    }
}