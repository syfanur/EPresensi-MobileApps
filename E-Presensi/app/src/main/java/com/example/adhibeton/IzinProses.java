package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class IzinProses extends Fragment {
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Izin> options;
    FirebaseRecyclerAdapter<Izin, IzinViewHolder> adapter;
    DatabaseReference DataRef;
    private View ProsesView;

    public IzinProses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        ProsesView = inflater.inflate(R.layout.activity_izin_proses, container, false);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Perizinan");
        recyclerView = ProsesView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);



        return ProsesView;


    }

    @Override
    public void onStart(){
        super.onStart();
        options = new FirebaseRecyclerOptions.Builder<Izin>()
                .setQuery(DataRef.child("Karyawan")
                        .child(Prevalent.currentOnlineUser.getNpp()), Izin.class).build();

        adapter = new FirebaseRecyclerAdapter<Izin, IzinViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull IzinViewHolder holder, final int position, @NonNull final Izin model) {
                holder.mjenis.setText("Izin " + model.getJenis());
                holder.mtanggal.setText(model.getHari() + " | " +model.getJam());
                Picasso.get().load(model.getBukti()).into(holder.mbukti);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(getActivity(),detailizin.class);
                        e.putExtra("pid", model.getId());
                        startActivity(e);
                    }
                });

            }

            @NonNull
            @Override
            public IzinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_izin, parent, false);
                return new IzinViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }}

