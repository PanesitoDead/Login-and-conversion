package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.login.databinding.FragmentConvertidorBinding
import com.example.login.R

class FragmentConvertidor : Fragment() {
    private var _binding: FragmentConvertidorBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MonedaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConvertidorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el ViewModel
        viewModel = ViewModelProvider(this).get(MonedaViewModel::class.java)

        // Configura el spinner de monedas
        val monedas = resources.getStringArray(R.array.monedas)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, monedas)
        binding.spinnerCurrency.setAdapter(adapter)

        // Observa el resultado de la conversión
        setupObservers()

        // Configura el botón de conversión
        setupButtonClick()

        // Configura el botón de cerrar sesión
        binding.buttonLogout.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.conversionResult.observe(viewLifecycleOwner) { result ->
            binding.tvResult.text = result
        }
    }

    private fun setupButtonClick() {
        binding.btnConvert.setOnClickListener {
            val monto = binding.etAmount.text.toString().toDoubleOrNull()
            val moneda = binding.spinnerCurrency.text.toString()

            if (monto != null) {
                viewModel.convertir(monto, moneda)
            } else {
                binding.tvResult.text = "Ingresar número válido"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
