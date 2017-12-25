package com.example.dimazatolokin.myapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dimazatolokin.myapplication.R;
import com.example.dimazatolokin.myapplication.data.model.OperationDb;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    private Context context;
    private List<OperationDb> operations;

    public HistoryAdapter(Context context, List<OperationDb> operations) {
        this.context = context;
        this.operations = operations;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.applyData(operations.get(position));
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtFirstCur;
        private TextView txtSecondCur;
        private TextView txtPrice;

        public VH(View itemView) {
            super(itemView);

            txtTime = itemView.findViewById(R.id.txt_time);
            txtFirstCur = itemView.findViewById(R.id.txt_first_cur);
            txtSecondCur = itemView.findViewById(R.id.txt_second_cur);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }

        public void applyData(OperationDb operation) {
            txtTime.setText(String.valueOf(operation.getTimestamp()));
            txtFirstCur.setText(operation.getTicker().getBase());
            txtSecondCur.setText(operation.getTicker().getTarget());
            txtPrice.setText(operation.getTicker().getPrice());
        }
    }
}
