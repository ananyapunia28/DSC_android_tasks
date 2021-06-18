package com.example.gazettes.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.gazettes.R


class WebFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_web, container, false)
        val url :String = arguments?.getString("url").toString()
        view.findViewById<WebView>(R.id.webView).webViewClient = WebViewClient()
        view.findViewById<WebView>(R.id.webView).loadUrl(url)

        return view
    }


}