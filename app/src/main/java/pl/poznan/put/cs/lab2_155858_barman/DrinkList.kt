package pl.poznan.put.cs.lab2_155858_barman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import pl.poznan.put.cs.lab2_155858_barman.models.Drink
import pl.poznan.put.cs.lab2_155858_barman.models.DrinkViewModel

class DrinkList : AppCompatActivity() {

    private lateinit var viewModel: DrinkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drink_list)
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

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val apiRequestQueue: RequestQueue = Volley.newRequestQueue(this)
        val apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=a"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            apiUrl,
            null,
            { response ->
                val drinksArray: JSONArray? = response.optJSONArray("drinks")

                if (drinksArray != null && drinksArray.length() > 0) {
                    val drinksList = mutableListOf<Drink>()
                    val drinkNames = mutableListOf<String>()
                    val gson = Gson()

                    for (i in 0 until drinksArray.length()) {
                        val drinkObject: JSONObject = drinksArray.getJSONObject(i)
                        val drink = gson.fromJson(drinkObject.toString(), Drink::class.java)
                        drinksList.add(drink)
                        drinkNames.add(drink.strDrink)
                    }

                    Log.d("DrinkList", "Pobrano ${drinksArray.length()} drinków")

                    // Zapisz listę drinków w ViewModelu
                    viewModel.setDrinks(drinksList)

                    // Ustaw adapter dla ListView
                    val listView: ListView = findViewById(R.id.drink_list_view)
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, drinkNames)
                    listView.adapter = adapter

                    // Dodaj obsługę kliknięcia
                    listView.setOnItemClickListener { _, _, position, _ ->
                        val selectedDrink = drinksList[position]
                        // Przejdź do aktywności szczegółów
                        val intent = Intent(this, DrinkDetails::class.java)
                        intent.putExtra("DRINK_ID", selectedDrink.idDrink)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "No drinks found", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.d("DrinkList", "Error: ${error.localizedMessage}")
                Toast.makeText(this, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        )

        apiRequestQueue.add(jsonObjectRequest)
    }
}
