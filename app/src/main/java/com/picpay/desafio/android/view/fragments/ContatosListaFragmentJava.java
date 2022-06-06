package com.picpay.desafio.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.picpay.desafio.android.R;
import com.picpay.desafio.android.adapters.UserListAdapter;
import com.picpay.desafio.android.api.PicPayService;
import com.picpay.desafio.android.data.User;
import com.picpay.desafio.android.databinding.FragmentContatosListaBinding;
import com.picpay.desafio.android.di.MyApplicationJava;
import com.picpay.desafio.android.util.ListenerCallback;
import com.picpay.desafio.android.util.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContatosListaFragmentJava extends Fragment implements ListenerCallback {

    private FragmentContatosListaBinding binding;
    private UserListAdapter adapter;
    private ListenerCallback listener;


    @Inject
    PicPayService serviceProvider = null;

    public ContatosListaFragmentJava() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContatosListaBinding.inflate(inflater, container, false);

        ((MyApplicationJava) requireContext()).getNetComponent().inject(requireActivity());


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //pageUp(binding.fabUp, binding.nestedScroll);

        adapter = new UserListAdapter(listener);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userListProgressBar.setVisibility(View.VISIBLE);

        //if (serviceProvider != null) {
        //callGetListDagger()
        // } else {
        //callGetListaAPI()
        //}

        serviceProvider.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                String message = getString(R.string.error);
                binding.userListProgressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                    binding.userListProgressBar.setVisibility(View.GONE);

                    adapter.setLista((List<User>) call.request().body());
            }
        });

    }

    @Override
    public void itemClicked(@NonNull Object objeto) {

    }
}