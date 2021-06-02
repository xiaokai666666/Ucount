package com.yuukidach.ucount.callback;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yuukidach.ucount.R;
import com.yuukidach.ucount.view.adapter.MoneyItemAdapter;

//import com.yuukidach.ucount.GlobalVariables;

public class MainItemCallback extends ItemTouchHelper.SimpleCallback {
    // DO NOT make them as final
    private MoneyItemAdapter adapter;
    private Context context;
    private RecyclerView recyclerView;

    private Button mBtnConfirm;
    private Button mBtnCancel;

    public MainItemCallback(Context context, RecyclerView recyclerView, MoneyItemAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT);
        this.context = context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        // get selected item position
        final int position = viewHolder.getBindingAdapterPosition();

        if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final AlertDialog dialog = builder.create();
            View dialogView = View.inflate(context, R.layout.dialog_common, null);
            dialog.setView(dialogView);
            dialog.setCancelable(false);
            dialog.show();

            mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
            mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
            mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeItem(position);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    dialog.dismiss();
                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    dialog.dismiss();
                }
            });
        }
    }
}
