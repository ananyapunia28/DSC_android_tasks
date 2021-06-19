package com.example.gazettes.UI

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gazettes.R
import com.example.gazettes.R.id.nav_host_fragment_container
import com.example.gazettes.adapter.NewsAdapter
import com.example.gazettes.data.Article
import com.example.gazettes.data.MyNews
import com.example.gazettes.data.NewsApiJSON
import com.example.gazettes.databinding.FragmentMainBinding
import com.example.gazettes.service.APIRequest
import com.google.android.gms.common.api.Api
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.ncornette.cache.OkCacheControl
import com.ncornette.cache.OkCacheControl.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.news_lay.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import androidx.constraintlayout.solver.Cache as Cache1

const val BASE_URL = "https://newsapi.org/"
class MainFragment : Fragment(), Toolbar.OnMenuItemClickListener,
    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {

//    lateinit var countdownTimer: CountDownTimer
//    private var seconds = 3L
private lateinit var auth : FirebaseAuth

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()
    private var pubList = mutableListOf<String>()
    lateinit var binding : FragmentMainBinding
    lateinit var adapter: NewsAdapter
    private lateinit var navController: NavController
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val myadapter by lazy {
        NewsAdapter(titlesList, descList, imagesList, linksList, pubList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
//        val progressbar: ProgressBar = rootView.findViewById(R.id.progressBar)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.topAppBar?.setOnMenuItemClickListener(this)
        return binding.root
    }

    private fun setUpRecyclerView() {

        recyclerView.adapter = myadapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun addToList(
        title: String,
        description: String,
        image: String,
        link: String,
        date: String
    ) {
        linksList.add(link)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
        pubList.add(date)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val navHostFragment = FragmentManager.findFragment(R.id.myHostFragment) as NavHostFragment
        navController = Navigation.findNavController(view)
        makeApiRequest()
    }

    private fun makeApiRequest() {
        progressBar.visibility = View.VISIBLE
//***********************************************************
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB

        val cache = Cache(requireContext().cacheDir, cacheSize)

        //
        fun isInternetAvailable(context: Context): Boolean? {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }

            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnectedOrConnecting
            }
        }

        val okHttpClient = OkCacheControl.on(OkHttpClient.Builder())
            .overrideServerCachePolicy(1, TimeUnit.MINUTES)
            .forceCacheWhenOffline(networkMonitor)
            .apply()
            .cache(cache)
            .build()


//***********************************************************

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(APIRequest::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: NewsApiJSON = api.getNews()
                for (article in response.articles) {
                    Log.i("MainFragment", "Results = $article")
                    addToList(
                        article.title,
                        article.description,
                        article.urlToImage,
                        article.url,
                        article.publishedAt
                    )
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("MainFragment", e.toString())
            }
            withContext(Dispatchers.Main) {
//                attemptRequestAgain()

            }

        }
    }

//    fun attemptRequestAgain() {
//        countdownTimer = object: CountDownTimer(5*1000,1000){
//            override fun onTick(millisUntilFinished: Long) {
//                Log.i("MainFragment","Error! Retrying in ${millisUntilFinished/1000} seconds")
//            }
//
//            override fun onFinish() {
////                makeApiRequest()
//                countdownTimer.cancel()
//            }
//
//        }
//        countdownTimer.start()
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//
//        return when (item.itemId) {
//            R.id.m1 -> {
//                findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
//                true
//            }
//            R.id.m2 -> {
//                findNavController().navigate(R.id.action_mainFragment_to_licenseFragment)
//                true
//            }
//            R.id.m3 -> {
//                findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//
//        }
//
//
//    }

    object networkMonitor : OkCacheControl.NetworkMonitor {
        override fun isOnline(): Boolean {
            var networkMonitor = NetworkMonitor { isInternetAvailable() }
            return true
        }

        private fun isInternetAvailable(): Boolean {
            fun isInternetAvailable(context: Context): Boolean? {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network = connectivityManager.activeNetwork ?: return false
                    val activeNetwork =
                        connectivityManager.getNetworkCapabilities(network) ?: return false
                    return when {
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }

                } else {
                    val networkInfo = connectivityManager.activeNetworkInfo
                    return networkInfo != null && networkInfo.isConnectedOrConnecting
                }
            }
            return true
        }


    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.m1 -> {
                findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)

            }
            R.id.m2 -> {
                activity?.let{
                    val intent = Intent (it, OssLicensesMenuActivity::class.java)
                    it.startActivity(intent)
                }

            }
            R.id.m3 -> {
                findNavController().navigate(R.id.action_mainFragment_to_profileFragment)

            }
            R.id.m4 -> {
                auth.signOut()
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment2)


            }
            else -> Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()

        }
        return true
    }
}



