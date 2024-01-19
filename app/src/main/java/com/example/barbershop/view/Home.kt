package com.example.barbershop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.barbershop.R

import com.example.barbershop.adapter.ServicosAdapter
import com.example.barbershop.databinding.ActivityHomeBinding

import com.example.barbershop.model.Servicos

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.extras?.getString("nome")

        binding.txtNomeUsuario.text = "Bem-Vindo(a), $nome"

        val recyclerViewServicos = binding.recyclerViewServico
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()
    }

    private fun getServicos() {

        val servicos1 = Servicos(R.drawable.img, "Corte de cabelo")
        listaServicos.add(servicos1)

        val servicos2 = Servicos(R.drawable.img2, "Corte de barba")
        listaServicos.add(servicos2)

        val servicos3 = Servicos(R.drawable.img3, "Lavagem de cabelo")
        listaServicos.add(servicos3)

        val servicos4 = Servicos(R.drawable.img4, "Tratamento de cabelo")
        listaServicos.add(servicos4)
    }
}