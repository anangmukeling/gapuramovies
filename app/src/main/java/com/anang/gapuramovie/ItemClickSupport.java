package com.anang.gapuramovie;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Anang Muklhisin on 04/09/2018.
 */

public class ItemClickSupport {

    private final RecyclerView mRecyclerView;
    private OnItemClickListener monItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;

    public ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    private View.OnClickListener mOnclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                monItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnlongclicklistener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (monItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return monItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (monItemClickListener != null) {
                view.setOnClickListener(mOnclicklistener);
            }
            if (monItemLongClickListener != null) {
                view.setOnLongClickListener(mOnlongclicklistener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        monItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        monItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

}
