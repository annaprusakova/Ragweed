package com.prusakova.ragweed.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.ChatAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.ChatActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Chat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomFragment extends Fragment {


    private List<Chat> chatRoomArrayList;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ChatAdapter.RecyclerViewClickListener listener;
    private Toolbar toolbar;
    private Api apiInterface;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_content, container, false);
        apiInterface=ApiClient.getClient().create(Api.class);
        progressBar = view.findViewById(R.id.prograss_chat_room);
        recyclerView = view.findViewById(R.id.recycler_view_chat);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        toolbar = view.findViewById(R.id.toolbar_chat_room);
        toolbar.inflateMenu(R.menu.chat_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Чати");
        }

        listener = new ChatAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("chat_id", chatRoomArrayList.get(position).getChatId());
                intent.putExtra("chat_name", chatRoomArrayList.get(position).getChatName());
                startActivity(intent);
            }

        };

        fetchChat("chat_rooms", "");
        return view;
    }



    public void fetchChat(String type, String key){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Chat>> call = apiInterface.getChat(type, key);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                progressBar.setVisibility(View.GONE);
                chatRoomArrayList = response.body();
                chatAdapter = new ChatAdapter(chatRoomArrayList,getContext(), listener);
                recyclerView.setAdapter(chatAdapter);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchChat("chat_rooms", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchChat("chat_rooms", newText);
                return false;
            }

        });
    }
}
