package com.example.cryptowallet.ui.main

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.cryptowallet.R
import com.example.cryptowallet.databinding.MainFragmentBinding
import com.example.cryptowallet.util.SharedPreference
import com.example.cryptowallet.util.network.NETWORK_CONNECTED_CODE
import com.example.cryptowallet.util.network.NetworkChangeReceiver
import com.example.domain.core.error.ApiFailure
import com.example.domain.core.error.Failure
import com.example.superdemo.condition.KeyStoreUtils.DeCryptor
import com.example.superdemo.condition.KeyStoreUtils.EnCryptor
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    private val adapter: TokenAdapter by lazy { TokenAdapter() }
    private var mNetworkReceiver: BroadcastReceiver? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configNetworkReceiver()
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.failureLiveData.observe(this) {
            showNetworkError(it)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = adapter
        saveSecurityKey("Contrary to popular belief, Lorem Ipsum is not simply random text")
        Toast.makeText(requireContext(), getSecurityKey(sharedPreference.encryptValue), Toast.LENGTH_LONG).show()
        mainViewModel.tokenListingLiveData.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        binding.imgSearch.setOnClickListener {
            openSearchScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterNetworkChanges()
    }

    private fun openSearchScreen() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun showNetworkError(failure: Failure?) {
        val message = when (failure) {
            is ApiFailure.Connection -> {
                getString(R.string.msg_no_internet_error)
            }
            is ApiFailure.Server -> {
                val errorMessage = failure.errorMessage
                errorMessage.ifEmpty {
                    getString(R.string.msg_unexpected_error)
                }
            }
            is ApiFailure.Unknown -> {
                getString(R.string.msg_unexpected_error)
            }
            else -> ""
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun configNetworkReceiver() {
        mNetworkReceiver =
            NetworkChangeReceiver(Handler(requireContext().mainLooper, Handler.Callback {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    handleNetworkListener(it.arg1)
                }

                return@Callback true
            }))
        registerNetworkBroadcast()
    }
    private fun registerNetworkBroadcast() {
        requireContext().registerReceiver(
            mNetworkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }
    private fun unregisterNetworkChanges() {
        try {
            mNetworkReceiver?.let { requireContext().unregisterReceiver(mNetworkReceiver) }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
    private fun handleNetworkListener(status: Int) {
        if (status == NETWORK_CONNECTED_CODE) {//connected
            mainViewModel.fetchTokenListingByPeriod()
        } else {

        }
    }
    val ENCRYPT_ALIAS = "PASSWORD_ALIAS"
    private var enCryptor = EnCryptor()
    private val deCryptor = DeCryptor()
    private val sharedPreference: SharedPreference by inject()
    fun getSecurityKey(encryptValue: String): String {
        return try {
            deCryptor.decryptData(
                ENCRYPT_ALIAS,
                Base64.decode(encryptValue, Base64.DEFAULT),
                Base64.decode(sharedPreference.encryptKey, Base64.DEFAULT)
            )
        } catch (e: Exception) {
            ""
        }
    }
    private fun saveSecurityKey(value: String) {
        sharedPreference.encryptValue = Base64.encodeToString(
            enCryptor.encryptText(ENCRYPT_ALIAS, value),
            Base64.DEFAULT
        )
        sharedPreference.encryptKey =
            Base64.encodeToString(enCryptor.iv, Base64.DEFAULT)
    }

}