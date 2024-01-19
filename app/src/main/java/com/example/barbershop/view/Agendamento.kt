package com.example.barbershop.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi

import com.example.barbershop.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar


private lateinit var binding: ActivityAgendamentoBinding

private val calendar: Calendar = Calendar.getInstance()
private var data: String = ""
private var hora: String = ""

class Agendamento : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.extras?.getString("nome").toString()
        val datePicker = binding.datePicket
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var dia = dayOfMonth.toString()
            val mes: String

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10) {
                mes = (monthOfYear + 1).toString()
            } else {
                mes = (monthOfYear + 1).toString()
            }
            data = "$dia / $mes / $year"
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->

            val minuto: String

            if (minute < 10) {
                minuto = "0$minute"
            } else {
                minuto = minute.toString()
            }

            hora = "$hourOfDay:$minuto"
        }
        binding.timePicker.setIs24HourView(true)

        binding.btAgendar.setOnClickListener {

            val barbeario1 = binding.barbeiro1
            val barbeario2 = binding.barbeiro2
            val barbeario3 = binding.barbeiro3

            when {
                hora.isEmpty() -> {
                    mensagem(it, "Preencha o horário", "#FF0000")
                }

                hora < "8:00" && hora > "17:00" -> {
                    mensagem(
                        it,
                        "Barber Shop está fechado - horário de atendimento das 08 Horas : 00 minutos ás 17 Horas : 00 minutos",
                        "#FF0000"
                    )
                }

                data.isEmpty() -> {
                    mensagem(it, "Coloque uma data", "#FF0000")
                }

                barbeario1.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, nome,"Alex Fonseca", data, hora)
                }

                barbeario2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, nome,"Pedro Paulo", data, hora)
                }

                barbeario3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, nome,"Junior Pressato", data, hora)
                }

                else -> {
                    mensagem(it, "Escolha um barbeiro", "#FF0000")

                }
            }

        }
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("FFFFFF"))
        snackbar.show()
    }

    private fun salvarAgendamento(view: View, cliente:String, barbearia: String, data: String, hora: String){

        val db = FirebaseFirestore.getInstance()
        val dadosUsuario = hashMapOf(
            "cliente" to cliente,
            "barbearia" to barbearia,
            "data" to data,
            "hora" to hora
        )
        db.collection("Agendamento").document(cliente).set(dadosUsuario).adOnCompleteListener{
            mensagem(view, "Agendamento realizado com sucesso !", "#FF03DAC5")
        }.addOnFailureListener{
            mensagem(view, "Erro no servidor !", "#FF0000")
        }
    }
}