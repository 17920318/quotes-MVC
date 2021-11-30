package dev.cardoso.quotesmvvm.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.cardoso.quotesmvvm.databinding.ActivityLoginBinding
import dev.cardoso.quotesmvvm.presentation.viewmodel.UserViewModel
import androidx.lifecycle.lifecycleScope
import dev.cardoso.quotesmvvm.R
import dev.cardoso.quotesmvvm.data.model.LoginRequest
import dev.cardoso.quotesmvvm.domain.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity (): AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    private val userViewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?){
        print(this.applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            val account = binding.etAccount.text.toString()
            val password =binding.etPassword.text.toString()
            userViewModel.loginRequest(LoginRequest(account, password))
        }
    userPreferencesRepository = UserPreferencesRepository(this@LoginActivity)
        observer()
    }

    private fun observer(){
        lifecycleScope.launch{
            userViewModel.loginResponse.collect{
                if(it.success){
                    val token = it.data
                    Toast.makeText(baseContext, "El usuario se ha autenticado corrctamente", Toast.LENGTH_LONG).show()
                    saveToken(token)

                }else{
                    if (it.message!=""){
                        Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun saveToken(token: String){
        lifecycleScope.launch(Dispatchers.IO){
            userPreferencesRepository.saveTokenToDataStore(token)
        }
    }

    /*
    fun newView(view: View){
        val agregar = findViewById<Button>(R.id.btnAgregar)
        val intent = Intent(this, Activity2Quotes::class.java)
        startActivity(intent)
    }
     */


}