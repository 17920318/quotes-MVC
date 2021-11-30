package dev.cardoso.quotesmvvm.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.cardoso.quotesmvvm.data.model.QuoteModel
import dev.cardoso.quotesmvvm.databinding.ActivityEditQuoteBinding
import dev.cardoso.quotesmvvm.domain.UserPreferencesRepository
import dev.cardoso.quotesmvvm.presentation.viewmodel.EditQuoteViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class EditQuoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditQuoteBinding
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    private val editQuoteViewModel: EditQuoteViewModel by viewModels()

    private var token=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditQuoteBinding.inflate(layoutInflater)
        userPreferencesRepository = UserPreferencesRepository(this@EditQuoteActivity)
        setContentView(binding.root)
        getToken()
        binding.btEdit.setOnClickListener {
            val id= binding.etIdQuotes.text.toString().toInt()
            val quote = binding.etQuote.text.toString()
            val author = binding.etAuthor.text.toString()
            val quoteModel = QuoteModel(id = id, quote = quote, author = author)

            lifecycleScope.launch(Dispatchers.IO) {
                Log.e("TOKEN", token)
                editQuoteViewModel.editQuote(quoteModel, "Bearer $token")
            }
        }
        editQuoteViewModel.quoteResponse.let {  }
        observer()
    }


    private fun getToken() {
        lifecycleScope.launch(Dispatchers.IO) {
            userPreferencesRepository.token.collect { token = it }
        }
    }

    private  fun observer() {
        lifecycleScope.launch(Dispatchers.IO) {
            editQuoteViewModel.quoteResponse.collect {
                binding.tvMessage.text= it.message
            }
        }
    }

}