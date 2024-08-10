package com.example.recipeapp.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.recipeapp.R

class SplashFragment : Fragment() {

    private val SPLASH_DURATION = 3000L // 3 seconds

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        val lottieAnimationView: LottieAnimationView = view.findViewById(R.id.lottie_animation_view)

        // Start the animation
        lottieAnimationView.playAnimation()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate after the splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            // Ensure we're still on the SplashFragment before navigating
            if (isAdded && findNavController().currentDestination?.id == R.id.splashFragment) {
                val sharedPreferences = activity?.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val isLoggedIn = sharedPreferences?.getBoolean("isLoggedIn", false) ?: false

                if (isLoggedIn) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, SPLASH_DURATION)
    }
}
