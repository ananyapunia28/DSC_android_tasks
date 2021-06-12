package com.example.gazettes.UI

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gazettes.data.MyNews
import com.example.gazettes.adapter.NewsAdapter
import com.example.gazettes.NewsService
import com.example.gazettes.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    lateinit var adapter: NewsAdapter
    private lateinit var navController: NavController
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        val progressbar: ProgressBar = rootView.findViewById(R.id.progressBar)
        val news = NewsService.NewsInstance.getHeadline("in", 1)
        news.enqueue(object : Callback<MyNews> {
            override fun onResponse(call: Call<MyNews>, response: Response<MyNews>) {
                val news = response.body()
                if (news != null) {
                    Log.d("Gazettes", news.toString())
                    val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
                    adapter = NewsAdapter(this@MainFragment, news.articles)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    progressbar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MyNews>, t: Throwable) {
                Log.d("Gazettes", "Error in fetching data", t)

            }
        })



        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.m1 -> {

                navController.navigate(R.id.action_mainFragment_to_aboutFragment)
            }
            R.id.m2 -> {
                navController.navigate(R.id.action_mainFragment_to_licenseFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}


