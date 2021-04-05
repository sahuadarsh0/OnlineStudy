package com.techipinfotech.onlinestudy1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.techipinfotech.onlinestudy1.BuildConfig
import com.techipinfotech.onlinestudy1.R

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(
            R.layout.fragment_menu, container,
            false
        )

        val techip: View = view.findViewById(R.id.view)
        val contact: CardView = view.findViewById(R.id.contact)
        val share: CardView = view.findViewById(R.id.share)




        contact.setOnClickListener {

            try {
                val whatsAppRoot = "http://api.whatsapp.com/"
                val number =
                    "send?phone=+919644200007" //here the mobile number with its international prefix
                val text = "&text=Hi Pragati Institute support team !!! I need help in "
                val uri = whatsAppRoot + number + text
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context,
                    "WhatsApp cannot be opened", Toast.LENGTH_SHORT
                ).show()
            }


        }

        share.setOnClickListener {

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            Toast.makeText(context, "Share the app", Toast.LENGTH_SHORT).show()
        }

        techip.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://techipinfotech.com/"))
            startActivity(browserIntent)
        }

        return view
    }

}
