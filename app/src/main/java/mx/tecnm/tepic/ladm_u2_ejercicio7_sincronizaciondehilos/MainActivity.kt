package mx.tecnm.tepic.ladm_u2_ejercicio7_sincronizaciondehilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val hilo1 = HiloJugador1(this)
    val hilo2 = HiloJugador2(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var start = true

        button.setOnClickListener {
            button.visibility = View.INVISIBLE
            try {
                hilo1.start()
                hilo2.start()
                Join(1)
                fin()
            }catch (io: Exception){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
            }

        }

    }
    fun Join(tiempo:Int){
        hilo1.join(tiempo.toLong())
        hilo2.join(tiempo.toLong())
    }
    fun fin(){
        if (hilo1.jugando == false && hilo2.jugando == false) {
            if (hilo1.r1>hilo2.r2){
                textView5.text = "EL GANADOR ES: Juegador1"
            }
            if (hilo1.r1<hilo2.r2){
                textView5.text = "EL GANADOR ES: Juegador2"
            }
            if (hilo1.r1==hilo2.r2){
                textView5.text = "EMPATE!!!"
            }
            button.visibility = View.VISIBLE
        }
    }
}

class HiloJugador1(p: MainActivity): Thread(){
    val puntero = p
    var dado1 = 0
    var dado2 = 0
    var jugando = true
    var contador = 0
    var r1 = 0

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
        while (jugando){
            puntero.runOnUiThread {
                puntero.textView6.text = "Contador: ${contador}"
                if (contador == 1) {
                    tiro1()
                }
                if (contador == 3) {
                    tiro2()
                }
                if (contador == 5) {
                    jugando = false
                }
            }
            contador++
            sleep(1000)
        }
        r1 = dado1 + dado2
    }
}

class HiloJugador2(p: MainActivity): Thread(){
    val puntero = p
    var dado1 = 0
    var dado2 = 0
    var jugando = true
    var contador = 0
    var r2 = 0

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
        while (jugando){
            puntero.runOnUiThread {
                puntero.textView7.text = "Contador: ${contador}"
                if (contador == 2) {
                    tiro1()
                }
                if (contador == 4) {
                    tiro2()
                }
                if (contador == 5) {
                    jugando = false
                }
            }
            contador++
            sleep(1000)
        }
        r2 = dado1 + dado2
    }
}