package com.adrian.inspiration_aquoteeveryhour

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adrian.inspiration_aquoteeveryhour.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuote()


        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, binding.txtQuote.text.toString())
            startActivity(Intent.createChooser(intent, "Share to:"))
        }



    }

    private fun getQuote() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val call = getRetrofit().create(APIService::class.java)
                    .getQuote("inspiration/")
                val response = call.body()
                runOnUiThread {
                    if (call.isSuccessful) {

                        response?.let {
                            val quote = it.quote.toString()
                            val author = it.author.toString()
                            binding.txtQuote.text = "“ $quote ”\n\n · $author"
                        }

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error while trying to get the quote",
                            Toast.LENGTH_SHORT
                        ).show()
                        // showError()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Error while trying to get the quote",
                        Toast.LENGTH_SHORT
                    ).show()
                    System.out.println(e.message)
                }

            }
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.goprogram.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}