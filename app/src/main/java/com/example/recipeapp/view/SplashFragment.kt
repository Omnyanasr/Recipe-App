package com.example.recipeapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.recipeapp.R
import com.example.recipeapp.authintication.SharedPrefManager

class SplashFragment : Fragment() {

    private val SPLASH_DURATION = 3000L // 3 seconds
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        lottieAnimationView = view.findViewById(R.id.lottie_animation_view)

        // Initialize SharedPrefManager
        sharedPrefManager =  SharedPrefManager.getInstance(requireActivity())

        // Start the animation
        lottieAnimationView.playAnimation()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate after the splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded) {
                val isLoggedIn = sharedPrefManager.isLoggedIn()
                Log.d("SplashFragment", "User logged in: $isLoggedIn")
                if (isLoggedIn) {
                    val intent = Intent(requireContext(), RecipeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, SPLASH_DURATION)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the animation when the view is destroyed
        lottieAnimationView.cancelAnimation()
    }
}
