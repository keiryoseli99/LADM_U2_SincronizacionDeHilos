package mx.tecnm.tepic.ladm_u2_ejercicio7_sincronizaciondehilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hilo1 = HiloJugador1(this)
        val hilo2 = HiloJugador2(this)
        var r1 = 0
        var r2 = 0

        button.setOnClickListener {
            try {
                hilo1.start()
                hilo2.start()

                while (hilo1.partida == true && hilo2.partida == false) {
                    hilo1.tiro1()
                    hilo1.partida = false
                    hilo2.partida = true
                }
                while (hilo1.partida == false && hilo2.partida == true) {
                    hilo2.tiro1()
                    hilo1.partida = true
                    hilo2.partida = false
                }
                while (hilo1.partida == true && hilo2.partida == false) {
                    hilo1.tiro2()
                    hilo1.partida = false
                    hilo2.partida = true
                }
                while (hilo1.partida == false && hilo2.partida == true) {
                    hilo2.tiro2()
                    hilo1.partida = true
                    hilo2.partida = false
                }

                r1 = hilo1.dado1 + hilo1.dado2
                r2 = hilo2.dado1 + hilo2.dado2

                if (r1>r2){
                    textView5.text = "EL GANADOR ES: Juegador1"
                }else {
                    textView5.text = "EL GANADOR ES: Juegador2"
                }

            }catch (io: Exception){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
            }
        }
    }
}

class HiloJugador1(p: MainActivity): Thread(){
    val puntero = p
    var dado1 = 0
    var dado2 = 0
    var partida = true
    var pausa = false

    fun tiro1() {
        dado1 = (1..6).random()
        puntero.textView2.text = "REGISTRO: Tiro1: (${dado1})"
    }
    fun tiro2() {
        dado2 = (1..6).random()
        puntero.textView2.text = "REGISTRO: Tiro1: (${dado1}) Tiro2: (${dado2})"
    }

    override fun run() {
        super.run()
        puntero.runOnUiThread {
        }
        sleep(1000)
    }
}

class HiloJugador2(p: MainActivity): Thread(){
    val puntero = p
    var dado1 = 0
    var dado2 = 0
    var partida = false
    var pausa = true

    fun tiro1() {
        dado1 = (1..6).random()
        puntero.textView4.text = "REGISTRO: Tiro1: (${dado1})"
    }
    fun tiro2() {
        dado2 = (1..6).random()
        puntero.textView4.text = "REGISTRO: Tiro1: (${dado1}) Tiro2: (${dado2})"
    }

    override fun run() {
        super.run()
        puntero.runOnUiThread {
        }
        sleep(1000)
    }
}