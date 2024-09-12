package com.example.bodymassindex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var textViewBMI: TextView
    private lateinit var imageViewBodyType: ImageView
    private lateinit var textViewRecommendations: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        textViewBMI = findViewById(R.id.textViewBMI)
        imageViewBodyType = findViewById(R.id.imageViewBodyType)
        textViewRecommendations = findViewById(R.id.textViewRecommendations)

        val height = intent.getDoubleExtra("height", 0.0)
        val weight = intent.getDoubleExtra("weight", 0.0)
        val bmi = calculateBMI(height, weight)

        val (bodyTypeResId, recommendations) = getBodyTypeAndRecommendations(bmi)

        textViewBMI.text = "Ваш BMI: %.2f".format(bmi) + ": ${recommendations.second}"

        imageViewBodyType.setImageResource(bodyTypeResId)
        textViewRecommendations.text = recommendations.first

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun calculateBMI(height: Double, weight: Double): Double {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

    private fun getBodyTypeAndRecommendations(bmi: Double): Pair<Int, Pair<String, String>> {
        return when {
            bmi < 18.5 -> {
                R.drawable.underweight to (underweightRec to "Ниже нормы")
            }
            bmi in 18.5..24.9 -> {
                R.drawable.normalweight to (normalweightRec to "Норма")
            }
            bmi in 25.0..29.9 -> {
                R.drawable.overweight to (overweightRec to "Выше нормы")
            }
            else -> {
                R.drawable.obese to (obeseRec to "Значительно выше нормы")
            }
        }
    }
}