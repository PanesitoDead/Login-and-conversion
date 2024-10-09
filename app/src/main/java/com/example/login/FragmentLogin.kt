package com.example.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.login.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val videoUri = Uri.parse("android.resource://${requireActivity().packageName}/raw/background_video")
        binding.videoViewBackground.setVideoURI(videoUri)


        binding.videoViewBackground.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f)
            binding.videoViewBackground.start()
        }


        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showAlertDialog("Error", "Por favor, complete todos los campos.")
            } else {
                showWelcomeDialog("Bienvenido", "Hola, $username")

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FragmentConvertidor())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun showWelcomeDialog(title: String, message: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()


        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
