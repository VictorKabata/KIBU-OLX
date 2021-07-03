package com.ifixhubke.kibu_olx.ui.fragments.sell

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ifixhubke.kibu_olx.R
import com.ifixhubke.kibu_olx.data.Sell
import com.ifixhubke.kibu_olx.databinding.FragmentSellOneBinding
import java.util.*

class SellFragmentOne : Fragment(), OnItemSelectedListener {
    private val imagesArrayList = ArrayList<Uri?>()
    var imageURI1: Uri? = null
    var imageURI2: Uri? = null
    var imageURI3: Uri? = null
    var binding: FragmentSellOneBinding? = null
    private var category: String? = null
    private var location: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellOneBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        //categorySpinner  and locationSpinner
        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category,
            R.layout.spinner_row
        )
        val arrayAdapter1 = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.location,
            R.layout.spinner_row
        )
        binding!!.categorySpinner.adapter = arrayAdapter
        binding!!.locationSpinner.adapter = arrayAdapter1
        binding!!.categorySpinner.onItemSelectedListener = this
        binding!!.locationSpinner.onItemSelectedListener = this
        binding!!.imagePick1.setOnClickListener { v: View? ->
            openFileChooser(IMAGE_REQUEST1)
            binding!!.imagePick1.visibility = View.GONE
        }
        binding!!.imagePick2.setOnClickListener { v: View? ->
            openFileChooser(IMAGE_REQUEST2)
            binding!!.imagePick2.visibility = View.GONE
        }
        binding!!.imagePick3.setOnClickListener { v: View? ->
            openFileChooser(IMAGE_REQUEST3)
            binding!!.imagePick3.visibility = View.GONE
        }
        binding!!.imageRemove1.setOnClickListener { v: View? ->
            binding!!.imageView1.setImageURI(null)
            imagesArrayList.removeAt(0)
            openFileChooser(IMAGE_REQUEST1)
        }
        binding!!.imageRemove2.setOnClickListener { v: View? ->
            binding!!.imageView2.setImageURI(null)
            imagesArrayList.removeAt(1)
            openFileChooser(IMAGE_REQUEST2)
        }
        binding!!.imageRemove3.setOnClickListener { v: View? ->
            binding!!.imageView3.setImageURI(null)
            imagesArrayList.removeAt(2)
            openFileChooser(IMAGE_REQUEST3)
        }
        binding!!.nextButton.setOnClickListener { v: View? ->
            //Passing data to sell2
            if (binding!!.categorySpinner.selectedItem.toString()
                    .trim { it <= ' ' } == "Choose Category"
            ) {
                Toast.makeText(requireContext(), "Please select category", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding!!.locationSpinner.selectedItem.toString()
                    .trim { it <= ' ' } == "Choose Lgit addocation"
            ) {
                Toast.makeText(requireContext(), "Please select location", Toast.LENGTH_SHORT)
                    .show()
            } else if (imageURI1 == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload your first image",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (imageURI2 == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload your second image",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (imageURI3 == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload your Third image",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val sell = Sell(category, location, imagesArrayList)
                val action: NavDirections =
                    SellFragmentOneDirections.sellOneToSellTwo(sell)
                Navigation.findNavController(v!!).navigate(action)
            }
        }
        return view
    }

    //choosing image
    fun openFileChooser(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageURI1 = data.data
            imagesArrayList.add(imageURI1)
            binding!!.imageRemove1.visibility = View.VISIBLE
            binding!!.imageView1.setImageURI(imageURI1)
            binding!!.imagePick1.visibility = View.INVISIBLE
        } else if (requestCode == IMAGE_REQUEST2 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageURI2 = data.data
            imagesArrayList.add(imageURI2)
            binding!!.imageView2.setImageURI(imageURI2)
            binding!!.imageRemove2.visibility = View.VISIBLE
            binding!!.imagePick2.visibility = View.INVISIBLE
        } else if (requestCode == IMAGE_REQUEST3 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageURI3 = data.data
            imagesArrayList.add(imageURI3)
            binding!!.imageView3.setImageURI(imageURI3)
            binding!!.imageRemove3.visibility = View.VISIBLE
            binding!!.imagePick3.visibility = View.INVISIBLE
        } else if (requestCode == IMAGE_REQUEST1 && data == null) {
            binding!!.imagePick1.visibility = View.VISIBLE
            binding!!.imageRemove1.visibility = View.INVISIBLE
        } else if (requestCode == IMAGE_REQUEST2 && data == null) {
            binding!!.imagePick2.visibility = View.VISIBLE
            binding!!.imageRemove2.visibility = View.INVISIBLE
        } else if (requestCode == IMAGE_REQUEST3 && data == null) {
            binding!!.imagePick3.visibility = View.VISIBLE
            binding!!.imageRemove3.visibility = View.INVISIBLE
        }

        /* else{
            Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            binding.imagePick1.setVisibility(View.VISIBLE);
            binding.imageRemove1.setVisibility(View.INVISIBLE);

            binding.imagePick2.setVisibility(View.VISIBLE);
            binding.imageRemove2.setVisibility(View.INVISIBLE);

            binding.imagePick3.setVisibility(View.VISIBLE);
            binding.imageRemove3.setVisibility(View.INVISIBLE);
        }*/
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (parent.id == R.id.categorySpinner) {
            category = binding!!.categorySpinner.selectedItem.toString()
        } else if (parent.id == R.id.locationSpinner) {
            location = binding!!.locationSpinner.selectedItem.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    companion object {
        private const val IMAGE_REQUEST1 = 1
        private const val IMAGE_REQUEST2 = 2
        private const val IMAGE_REQUEST3 = 3
    }
}