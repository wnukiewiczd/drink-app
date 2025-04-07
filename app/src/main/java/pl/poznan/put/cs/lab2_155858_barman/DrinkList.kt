package pl.poznan.put.cs.lab2_155858_barman

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import pl.poznan.put.cs.lab2_155858_barman.models.api.Drink

class DrinkList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drink_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val apiRequestQueue: RequestQueue = Volley.newRequestQueue(this)
        val apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            apiUrl,
            null, // No body for GET request
            { response ->
                val drinksArray: JSONArray? = response.optJSONArray("drinks")

                if (drinksArray != null && drinksArray.length() > 0) {
                    val drinksList = mutableListOf<Drink>()
                    val drinkNames = mutableListOf<String>()
                    for (i in 0 until drinksArray.length()) {
                        val drinkObject: JSONObject = drinksArray.getJSONObject(i)
                        val drink = Drink(
                            idDrink = drinkObject.optString("idDrink"),
                            strDrink = drinkObject.optString("strDrink"),
                            strDrinkAlternate = drinkObject.optString("strDrinkAlternate"),
                            strTags = drinkObject.optString("strTags"),
                            strVideo = drinkObject.optString("strVideo"),
                            strCategory = drinkObject.optString("strCategory"),
                            strIBA = drinkObject.optString("strIBA"),
                            strAlcoholic = drinkObject.optString("strAlcoholic"),
                            strGlass = drinkObject.optString("strGlass"),
                            strInstructions = drinkObject.optString("strInstructions"),
                            strInstructionsES = drinkObject.optString("strInstructionsES"),
                            strInstructionsDE = drinkObject.optString("strInstructionsDE"),
                            strInstructionsFR = drinkObject.optString("strInstructionsFR"),
                            strInstructionsIT = drinkObject.optString("strInstructionsIT"),
                            strInstructionsZH_HANS = drinkObject.optString("strInstructionsZH-HANS"),
                            strInstructionsZH_HANT = drinkObject.optString("strInstructionsZH-HANT"),
                            strDrinkThumb = drinkObject.optString("strDrinkThumb"),
                            strIngredient1 = drinkObject.optString("strIngredient1"),
                            strIngredient2 = drinkObject.optString("strIngredient2"),
                            strIngredient3 = drinkObject.optString("strIngredient3"),
                            strIngredient4 = drinkObject.optString("strIngredient4"),
                            strIngredient5 = drinkObject.optString("strIngredient5"),
                            strIngredient6 = drinkObject.optString("strIngredient6"),
                            strIngredient7 = drinkObject.optString("strIngredient7"),
                            strIngredient8 = drinkObject.optString("strIngredient8"),
                            strIngredient9 = drinkObject.optString("strIngredient9"),
                            strIngredient10 = drinkObject.optString("strIngredient10"),
                            strIngredient11 = drinkObject.optString("strIngredient11"),
                            strIngredient12 = drinkObject.optString("strIngredient12"),
                            strIngredient13 = drinkObject.optString("strIngredient13"),
                            strIngredient14 = drinkObject.optString("strIngredient14"),
                            strIngredient15 = drinkObject.optString("strIngredient15"),
                            strMeasure1 = drinkObject.optString("strMeasure1"),
                            strMeasure2 = drinkObject.optString("strMeasure2"),
                            strMeasure3 = drinkObject.optString("strMeasure3"),
                            strMeasure4 = drinkObject.optString("strMeasure4"),
                            strMeasure5 = drinkObject.optString("strMeasure5"),
                            strMeasure6 = drinkObject.optString("strMeasure6"),
                            strMeasure7 = drinkObject.optString("strMeasure7"),
                            strMeasure8 = drinkObject.optString("strMeasure8"),
                            strMeasure9 = drinkObject.optString("strMeasure9"),
                            strMeasure10 = drinkObject.optString("strMeasure10"),
                            strMeasure11 = drinkObject.optString("strMeasure11"),
                            strMeasure12 = drinkObject.optString("strMeasure12"),
                            strMeasure13 = drinkObject.optString("strMeasure13"),
                            strMeasure14 = drinkObject.optString("strMeasure14"),
                            strMeasure15 = drinkObject.optString("strMeasure15"),
                            strImageSource = drinkObject.optString("strImageSource"),
                            strImageAttribution = drinkObject.optString("strImageAttribution"),
                            strCreativeCommonsConfirmed = drinkObject.optString("strCreativeCommonsConfirmed"),
                            dateModified = drinkObject.optString("dateModified")
                        )
                        drinkNames.add(drink.strDrink)
                        drinksList.add(drink)
                    }

                    // Set up the ListView with the drinks data
                    val listView: ListView = findViewById(R.id.drink_list_view)
                    val adapter =
                        ArrayAdapter(this, android.R.layout.simple_list_item_1, drinkNames)
                    listView.adapter = adapter
                } else {
                    // No drinks found, show a message
                    Toast.makeText(this, "No drinks found", Toast.LENGTH_SHORT).show()
                }
            },
            { error: VolleyError ->
                // Handle error
                Toast.makeText(this, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        )

        apiRequestQueue.add(jsonObjectRequest)
    }
}