package com.lolblach333.tutu.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.lolblach333.tutu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("url", it.url)
            startActivity(intent)
        })

        binding.recyclerView.adapter = adapter

        viewModel.articles.observe(this, adapter::submitList)
        viewModel.error.observe(this, ::processError)
        viewModel.loading.observe(this, ::processLoading)

        viewModel.getRandomDogs(this)
    }

    private fun processError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun processLoading(isLoading: Boolean) {
        binding.progressView.isVisible = isLoading
    }
}
