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
import com.google.gson.Gson


class DrinkList : AppCompatActivity() {

    private val gson = Gson()

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
                        val drink = gson.fromJson(drinkObject.toString(), Drink::class.java)
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