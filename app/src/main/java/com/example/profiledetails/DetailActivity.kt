package com.example.profiledetails

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import com.example.extensions.checkEmail
import com.example.model.User
import com.example.profiledetails.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setListener()
    }

    private fun setData() {
        val user = intent.getParcelableExtra<User>(MainActivity.KEY_USER)
        binding.apply {
            nameEditTxtView.setText(user?.name)
            lastNameEditTxtView.setText(user?.lastName)
            mailEditTxtView.setText(user?.mail)
            yearEditTxtView.setText(user?.bornYear.toString())
            genderEditTxtView.setText(user?.gender)
        }
    }

    private fun setListener() {
        binding.saveBtnView.setOnClickListener {
            if (checkValidityOfInputs())
                saveAndGoToProfile()
        }
        binding.mailEditTxtView.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 < 3 || !text.toString().checkEmail())
                binding.mailEditTxtView.error = getString(R.string.email_not_valid)
        }

        binding.yearEditTxtView.doOnTextChanged { text, _, _, _ ->
            if (text?.isDigitsOnly() == false)
                binding.yearEditTxtView.error = getString(R.string.year_not_integer)
        }

        binding.nameEditTxtView.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 < 3)
                binding.nameEditTxtView.error = getString(R.string.username_length)
        }

        binding.lastNameEditTxtView.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 < 3)
                binding.nameEditTxtView.error = getString(R.string.lastname_length)
        }
    }

    private fun saveAndGoToProfile() {
        val user = User(
            name = binding.nameEditTxtView.text.toString(),
            lastName = binding.lastNameEditTxtView.text.toString(),
            mail = binding.mailEditTxtView.text.toString(),
            bornYear = binding.yearEditTxtView.text.toString().toInt(),
            gender = binding.genderEditTxtView.text.toString()
        )
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_EDITED_USER, user)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun checkValidityOfInputs(): Boolean {
        when {
            binding.mailEditTxtView.text.isNullOrEmpty() ||
                    binding.nameEditTxtView.text.isNullOrEmpty() ||
                    binding.lastNameEditTxtView.text.isNullOrEmpty() ||
                    binding.yearEditTxtView.text.isNullOrEmpty() -> {
                toast(
                    getString(R.string.fill_every_fields)
                )
                return false
            }
            !binding.mailEditTxtView.text.toString()
                .checkEmail() -> {
                toast(getString(R.string.email_not_valid))
                return false
            }
            binding.nameEditTxtView.text?.length?.let { it < 3 } == true -> {
                toast(getString(R.string.username_length))
                return false
            }
            binding.lastNameEditTxtView.text?.length?.let { it < 3 } == true -> {
                toast(getString(R.string.lastname_length))
                return false
            }
            !binding.yearEditTxtView.text.toString()
                .isDigitsOnly() -> {
                toast(getString(R.string.year_not_integer))
                return false
            }
            else -> {
                toast(getString(R.string.filled_fields))
                return true
            }
        }
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    companion object {
        const val KEY_EDITED_USER = "USER_EDITED"
    }

}