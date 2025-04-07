package pl.poznan.put.cs.lab2_155858_barman

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pl.poznan.put.cs.lab2_155858_barman.models.DrinkViewModel

class DrinkDetails : AppCompatActivity() {

    private lateinit var viewModel: DrinkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drink_details) // Zmień na nowy layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicjalizacja ViewModelu
        viewModel = ViewModelProvider(
            (application as BarmanApplication),
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DrinkViewModel::class.java]

        // Pobierz ID drinka z intentu
        val drinkId = intent.getStringExtra("DRINK_ID")

        if (drinkId != null) {
            // Pobierz dane drinka z ViewModelu
            val drink = viewModel.getDrinkById(drinkId)

            if (drink != null) {
                // Wyświetl dane drinka
                val nameTextView: TextView = findViewById(R.id.drink_name)
                val imageView: ImageView = findViewById(R.id.drink_image)

                nameTextView.text = drink.strDrink

                // Załaduj obraz używając biblioteki Glide
                Glide.with(this)
                    .load(drink.strDrinkThumb)
                    .into(imageView)

                // Możesz dodać więcej widoków do wyświetlania innych informacji o drinku
            } else {
                Toast.makeText(this, "Drink not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Invalid drink ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
