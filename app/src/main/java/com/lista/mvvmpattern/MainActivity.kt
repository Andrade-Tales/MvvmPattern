package com.lista.mvvmpattern

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lista.mvvmpattern.adapters.MainAdapter
import com.lista.mvvmpattern.databinding.ActivityMainBinding
import com.lista.mvvmpattern.repositories.MainRepository
import com.lista.mvvmpattern.rest.RetrofitService
import com.lista.mvvmpattern.viewmodel.main.MainViewModel
import com.lista.mvvmpattern.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter { live ->
        openLink(live.link)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        binding.recyclerview.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setLiveList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllLives()

    }

    private fun openLink(link: String) {

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)

    }

}