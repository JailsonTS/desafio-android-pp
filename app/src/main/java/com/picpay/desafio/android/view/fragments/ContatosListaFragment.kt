package com.picpay.desafio.android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapters.UserListAdapter
import com.picpay.desafio.android.api.FactoryService
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.databinding.FragmentContatosListaBinding
import com.picpay.desafio.android.data.User
import com.picpay.desafio.android.util.ListenerCallback
import com.picpay.desafio.android.util.ViewUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContatosListaFragment : Fragment(), ListenerCallback {
    private lateinit var binding: FragmentContatosListaBinding

    private lateinit var adapter: UserListAdapter
    private lateinit var listener: ListenerCallback

    //@Inject
    var serviceProvider: PicPayService? = null

    private val service: PicPayService by lazy {
        FactoryService(requireContext()).getPicPayService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContatosListaBinding.inflate(layoutInflater, container, false)
        listener = this

        // (requireContext().applicationContext as MyApplication).netComponent?.inject(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            ViewUtils.pageUp(fabUp, nestedScroll)

            adapter = UserListAdapter(listener)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            userListProgressBar.visibility = View.VISIBLE

            if (serviceProvider != null) {
                callGetListDagger()
            } else {
                callGetListaAPI()
            }

        }
    }

    private fun callGetListDagger() {
        serviceProvider!!.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = getString(R.string.error)

                    binding.userListProgressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE

                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    binding.userListProgressBar.visibility = View.GONE

                    response.body()?.let {
                        adapter.lista = it
                    }
                }
            })
    }

    private fun callGetListaAPI() {
        service.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = getString(R.string.error)

                    binding.userListProgressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE

                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    binding.userListProgressBar.visibility = View.GONE

                    response.body()?.let {
                        adapter.lista = it
                    }
                }
            })
    }

    override fun itemClicked(objeto: Any) {
        val userSelected = objeto as User
        val mensagem = "Selecionado: ${userSelected.name}"
        ViewUtils.dialogInformativo(requireContext(), mensagem)
    }


}