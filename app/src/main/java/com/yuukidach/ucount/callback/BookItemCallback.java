package com.yuukidach.ucount.callback;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yuukidach.ucount.R;
import com.yuukidach.ucount.view.adapter.BookItemAdapter;


public class BookItemCallback extends ItemTouchHelper.SimpleCallback {
    // DO NOT use final
    private Context context;
    private BookItemAdapter adapter;
    private RecyclerView recyclerView;

    private Button mBtnConfirm;
    private Button mBtnCancel;

    public BookItemCallback(Context context, RecyclerView recyclerView, BookItemAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT);
        this.context = context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        // 如果不想上下拖动，可以将 dragFlags = 0
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        // 如果你想左右滑动，可以将 swipeFlags = 0
        int swipeFlags = ItemTouchHelper.RIGHT;

        //最终的动作标识（flags）必须要用makeMovementFlags()方法生成
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

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
