package com.fangs.apar_app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fangs.apar_app.R
import com.fangs.apar_app.databinding.ActivityAddNewItemBinding

class AddNewItemActivity : BaseActivity() {

    private lateinit var binding : ActivityAddNewItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNewItemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        populateSpinner()

        //add to database
        binding.btnAddToDatabase.setOnClickListener {
            hideKeyboard(currentFocus ?: View(this))
            validateProduct()
        }


        //back key
        binding.navBack.setNavigationOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }
        
    }




    private fun validateProduct() : Boolean{

        //TODO PRIORITY LEVEL : TOP
        val newProductName = binding.etNewProductName.text.toString().trim()
        val newProductPrice = binding.etNewProductPrice.text.toString().trim()
        val newProductCategory = binding.spNewProductCategory.selectedItem.toString()

        return when{
            TextUtils.isEmpty(newProductName) -> {
                showErrorSnackBar(binding.root, "Product name cannot be empty.", true)
                false

            }
            TextUtils.isEmpty(newProductPrice) -> {
                showErrorSnackBar(binding.root, "Price cannot be empty.", true)
                false
            }

            //make sure the first element is not a valid category
            newProductCategory == binding.spNewProductCategory.getItemAtPosition(0) -> {
                showErrorSnackBar(binding.root, "Please select a valid category.", true)
                false
            }

            else -> {
                showErrorSnackBar(binding.root, "A new item was added to database.", false)
                true
            }

        }





    }

    private fun populateSpinner() {

        ArrayAdapter.createFromResource(
            this, R.array.products_category,
            R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)


            binding.spNewProductCategory.adapter = adapter

        }
    }
}