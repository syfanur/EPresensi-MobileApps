package com.example.adhibeton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListFragment extends Fragment {
    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    LinearLayoutManager mLayoutManager;
    LayoutInflater inflater;
    FirebaseRecyclerAdapter<ModelKehadiran, HolderView> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ModelKehadiran> options;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.list_kehadiran, viewGroup, false);
        mRecyclerView= v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Kehadiran");
        showdata();
        return v;
    }
    private void showdata(){
        options= new FirebaseRecyclerOptions.Builder<ModelKehadiran>().setQuery( mRef.child("NPP").child("1202170038").child("AbsenDatang").child("JUNE"), ModelKehadiran.class).build();
        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ModelKehadiran, HolderView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HolderView holder, int i, @NonNull ModelKehadiran model) {
                holder.setDetails(getContext(),model.getAbsen(), model.getKeterangan(),model.getLokasi(),model.getStatus(),model.getTanggal(),model.getWaktu());
            holder.mDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailKehadiran.class);
                    startActivity(intent);
                }
            });
            }

            @NonNull
            @Override
            public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kehadiran,parent,false);
                HolderView holderView= new HolderView(itemView);
                return holderView;
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }
}
