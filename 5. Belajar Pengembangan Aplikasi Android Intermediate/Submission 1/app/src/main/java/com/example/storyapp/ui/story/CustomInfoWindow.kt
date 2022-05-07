package com.example.storyapp.ui.story

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.storyapp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter {
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun rendowWindowText(marker: Marker, view: View) {

        val tvName = view.findViewById<TextView>(R.id.item_name)
        val tvDesc = view.findViewById<TextView>(R.id.item_description)

        tvName.text = marker.title
        tvDesc.text = marker.snippet
    }

    override fun getInfoContents(marker: Marker): View {
        val tvName = mWindow.findViewById<TextView>(R.id.item_name)
        val tvDesc = mWindow.findViewById<TextView>(R.id.item_description)

        tvName.text = marker.title
        tvDesc.text = marker.snippet

        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}