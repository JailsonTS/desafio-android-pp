package com.picpay.desafio.android.adapters.contatos.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.dto.User
import com.picpay.desafio.android.util.ListenerCallback
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListViewHolder(private val binding: ListItemUserBinding, private val  listener: ListenerCallback) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.apply {
            name.text = user.name
            username.text = user.username
            progressBar.visibility = View.VISIBLE

            Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(picture, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })

            container.setOnClickListener {
                listener.itemClicked(user)
            }
        }





    }

}