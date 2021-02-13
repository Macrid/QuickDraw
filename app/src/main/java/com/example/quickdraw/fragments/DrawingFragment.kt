package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import com.example.quickdraw.MyCanvasView
import com.example.quickdraw.R

class DrawingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_drawing, container, false)
    }

    override fun onStart() {
        super.onStart()
        var firstWordButton = requireView().findViewById<Button>(R.id.firstWordButton)
        var secondWordButton = requireView().findViewById<Button>(R.id.secondWordButton)
        var thirdWordButton = requireView().findViewById<Button>(R.id.thirdWordButton)

        firstWordButton.setOnClickListener {
            requireView().findViewById<ConstraintLayout>(R.id.overlayCL).isGone = true
        }

        var myCanvasView = MyCanvasView(this.requireContext())

        requireView().findViewById<FrameLayout>(R.id.canvasFrame).addView(myCanvasView)

        requireView().findViewById<Button>(R.id.switchColorButton).setOnClickListener {
            myCanvasView.drawColor = ResourcesCompat.getColor(resources, R.color.black, null)
            myCanvasView.changeColor()
        }

    }
}