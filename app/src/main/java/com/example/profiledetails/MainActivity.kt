package com.example.profiledetails

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.model.User
import com.example.profiledetails.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDefaultProfileData()
        setListener()
    }

    private fun setDefaultProfileData() {
        binding.apply {
            nameTxtView.text = getString(R.string.default_name)
            lastNameTxtView.text = getString(R.string.default_lastname)
            emailTxtView.text = getString(R.string.default_mail)
            bornYearTxtView.text = getString(R.string.default_year)
            genderTxtView.text = getString(R.string.default_gender)
        }
    }

    private fun setListener() {
        binding.btnView.setOnClickListener {
            navigateToDetailsScreenForResult()
        }
    }

    private fun navigateToDetailsScreenForResult() {
        val user = getDefaultUser()
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_USER, user)
        }
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user = result.data?.getParcelableExtra<User>(DetailActivity.KEY_EDITED_USER)
                setDataFromResult(user)
            }
        }

    private fun setDataFromResult(user: User?) {
        binding.apply {
            nameTxtView.text = user?.name
            lastNameTxtView.text = user?.lastName
            emailTxtView.text = user?.mail
            bornYearTxtView.text = user?.bornYear.toString()
            genderTxtView.text = user?.gender
        }
    }

    private fun getDefaultUser() = User(
        name = getString(R.string.default_name),
        lastName = getString(R.string.default_lastname),
        mail = getString(R.string.default_mail),
        bornYear = getString(R.string.default_year).toInt(),
        gender = getString(R.string.default_gender)
    )

    companion object {
        const val KEY_USER = "USER"
    }
}