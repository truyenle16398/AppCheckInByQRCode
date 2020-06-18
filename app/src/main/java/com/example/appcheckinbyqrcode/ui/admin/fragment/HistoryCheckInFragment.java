package com.example.appcheckinbyqrcode.ui.admin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.adapter.ItemHistoryAdapter;
import com.example.appcheckinbyqrcode.ui.admin.model.HistoryCheckIn;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryCheckInFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    View view;
    TextView tvCountMan,tvclear;
    RecyclerView recyclerView;
    ItemHistoryAdapter adapter;
    ArrayList<HistoryCheckIn> items;
    ArrayList<InfoQR> listinfo;
    InfoQR infoQR;
    private MyDatabaseHelper myDatabaseHelper;

    public HistoryCheckInFragment() {
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
        view = inflater.inflate(R.layout.fragment_history_check_in, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        listinfo = new ArrayList<>();
        listinfo = (ArrayList<InfoQR>) myDatabaseHelper.getAllInfo();
        InitWidget();
        getdata();
        onclick();
        Log.d("nnn", "show total: "+ myDatabaseHelper.getInfoCount());
        tvCountMan.setText("Total: "+myDatabaseHelper.getInfoCount());
        return view;
    }

    private void onclick() {
        tvclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(getActivity()).setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                myDatabaseHelper.deleteall();
                                listinfo.clear();
                                adapter.notifyDataSetChanged();
                                getdata();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_info).show();
            }
        });
    }

    private void InitWidget() {
        tvclear = view.findViewById(R.id.tvclearhistory);
        tvCountMan = view.findViewById(R.id.countman);
        refreshLayout = view.findViewById(R.id.swipeRefreshLayoutHistory);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listinfo = (ArrayList<InfoQR>) myDatabaseHelper.getAllInfo();
                adapter.notifyDataSetChanged();
                tvCountMan.setText("Total: "+myDatabaseHelper.getInfoCount());
                getdata();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getdata() {
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemHistoryAdapter(getActivity(),listinfo);
        recyclerView.setAdapter(adapter);
    }


}
