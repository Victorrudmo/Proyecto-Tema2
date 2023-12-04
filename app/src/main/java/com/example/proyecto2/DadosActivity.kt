package com.example.proyecto2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.proyecto2.databinding.ActivityDadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DadosActivity : AppCompatActivity() {

    private lateinit var bindingDados: ActivityDadosBinding
    private var sum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDados = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(bindingDados.root)
        initEvent()
    }

    private fun initEvent() {
        bindingDados.txtResultado.visibility = View.INVISIBLE
        bindingDados.imageButton.setOnClickListener {
            bindingDados.txtResultado.visibility = View.VISIBLE
            game()
        }
    }

    private fun game() {
        scheduleRun()
    }

    private fun scheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000

        for (i in 1..5) {
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS
            )
        }

        schedulerExecutor.schedule(
            {
                viewResult()
            },
            msc * 7.toLong(), TimeUnit.MILLISECONDS
        )

        schedulerExecutor.shutdown()
    }

    private fun throwDadoInTime() {
        val numDados = Array(2) { Random.nextInt(1, 5) }
        val imageViews: Array<ImageView> = arrayOf(
            bindingDados.imagviewDado1,
            bindingDados.imagviewDado2
        )

        sum = numDados.sum()

        for (i in 0..2) {
            selectView(imageViews[i], numDados[i])
        }
    }

    private fun selectView(imgV: ImageView, v: Int) {
        when (v) {
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
        }
    }


    private fun viewResult() {
        runOnUiThread {
            val resultadoImageView = bindingDados.resultadoImageView
            val resultadoTexto = sum.toString()

            when (resultadoTexto) {
                "2" -> Glide.with(this).load(R.drawable.carta2).into(resultadoImageView)
                "3" -> Glide.with(this).load(R.drawable.carta3).into(resultadoImageView)
                "4" -> Glide.with(this).load(R.drawable.carta4).into(resultadoImageView)
                "5" -> Glide.with(this).load(R.drawable.carta5).into(resultadoImageView)
                "6" -> Glide.with(this).load(R.drawable.carta6).into(resultadoImageView)
                "7" -> Glide.with(this).load(R.drawable.carta7).into(resultadoImageView)
                "8" -> Glide.with(this).load(R.drawable.carta8).into(resultadoImageView)
                "9" -> Glide.with(this).load(R.drawable.carta9).into(resultadoImageView)
                "10" -> Glide.with(this).load(R.drawable.carta10).into(resultadoImageView)
                else -> Glide.with(this).load(R.drawable.carta2).into(resultadoImageView)
            }

            bindingDados.txtResultado.text = resultadoTexto
        }
    }


}
