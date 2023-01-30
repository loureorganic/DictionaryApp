package com.example.dictionaryapp.screens.home.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelHome: ViewModelHome


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)


       // convertJsonAndSendData()
    }


    private fun convertJsonAndSendData() {
        val jsonData = applicationContext.resources.openRawResource(
            applicationContext.resources.getIdentifier(
                "output",
                "raw",
                applicationContext.packageName
            )
        ).bufferedReader().use { it.readText() }
        val result = convertStringToArray(jsonData)

        result.mapIndexed { index, s ->
            viewModelHome.addList(Word(s, index.toLong()))
        }

    }

    private fun convertStringToArray(s: String): Array<String> {
        var varString = s
        varString = varString.replace("\\[".toRegex(), "")
        varString = varString.replace("]".toRegex(), "")

        return varString.replace("\"", "").split(",").toTypedArray()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}