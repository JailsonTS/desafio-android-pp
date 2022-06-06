package com.picpay.desafio.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.adapters.viewHolder.UserListViewHolder
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.data.User
import com.picpay.desafio.android.util.ListenerCallback


class UserListAdapter(private val listener: ListenerCallback) :
    RecyclerView.Adapter<UserListViewHolder>() {

      var lista = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff( UserListDiffCallback(field,  value))
            result.dispatchUpdatesTo(this)
            field = value
        }

    lateinit var binding: ListItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}