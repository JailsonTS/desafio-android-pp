package com.picpay.desafio.android.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapters.contatos.UserListAdapter
import com.picpay.desafio.android.api.BuildService
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.databinding.FragmentContatosListaBinding
import com.picpay.desafio.android.dto.User
import com.picpay.desafio.android.util.ListenerCallback
import com.picpay.desafio.android.util.SimpleCallback
import com.picpay.desafio.android.util.ViewUtils
import kotlinx.android.synthetic.main.list_item_user.*
import okhttp3.Cache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContatosListaFragment : Fragment(), ListenerCallback {
    private lateinit var binding: FragmentContatosListaBinding

    private lateinit var adapter: UserListAdapter
    private lateinit var listener: ListenerCallback


    private val service: PicPayService by lazy {
       BuildService(requireContext()).getPicPayService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContatosListaBinding.inflate(layoutInflater, container, false)
        listener = this

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

            service.getUsers()
                .enqueue(object : Callback<List<User>> {
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        val message = getString(R.string.error)

                        userListProgressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        userListProgressBar.visibility = View.GONE

                        response.body()?.let {
                            adapter.lista = it
                        }
                    }
                })

        }
    }

    override fun itemClicked(objeto: Any) {
        val userSelected = objeto as User
        val mensagem = "Selecionado: ${userSelected.name}"
        ViewUtils.dialogInformativo(requireContext(), mensagem)
    }



}