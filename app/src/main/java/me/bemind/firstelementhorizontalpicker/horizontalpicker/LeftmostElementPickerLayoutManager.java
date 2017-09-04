package me.bemind.firstelementhorizontalpicker.horizontalpicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by angelomoroni on 22/06/17.
 */

public class LeftmostElementPickerLayoutManager extends LinearLayoutManager {



    private HorizontalPicker.onScrollStopListener onScrollStopListener = null;

    private View f;
    private RecyclerView rv;


    public LeftmostElementPickerLayoutManager(Context context, int orientation, RecyclerView rv, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.rv = rv;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {

            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            updateSelection();
            return scrolled;

        } else return 0;
    }



    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == 0) {
            if (onScrollStopListener != null  ) {
                onScrollStopListener.selectedView(f);
            }
        }
    }

    public void firstSelection() {
        try {
            updateSelection();

            if (onScrollStopListener != null) {
                onScrollStopListener.selectedView(f);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateSelection() {
        View newf = findFirstVisibleItem();
        if(!newf.equals(f) ) {
            f = newf;

            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                v.setAlpha(v.equals(f)?1f:0.5f);
            }
        }
    }


    public void setOnScrollStopListener(HorizontalPicker.onScrollStopListener onScrollStopListener) {
        this.onScrollStopListener = onScrollStopListener;
    }

    public int getItemCount() {
        return super.getItemCount();
    }

    public int findFirstVisibleItemPosition() {
        final View child = findOneVisibleChild(0, getChildCount(), false, true);
        return child == null ? RecyclerView.NO_POSITION : rv.getChildAdapterPosition(child);
    }

    public View findFirstVisibleItem(){
        return findOneVisibleChild(0, getChildCount(), false, true);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        final View child = findOneVisibleChild(0, getChildCount(), true, false);
        return child == null ? RecyclerView.NO_POSITION : rv.getChildAdapterPosition(child);
    }

    public View findFirstCompletelyVisibleItem(){
        return findOneVisibleChild(0, getChildCount(), true, false);
    }

    View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                             boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if (this.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(this);
        } else {
            helper = OrientationHelper.createHorizontalHelper(this);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = this.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }


    public void select(int position) {
        scrollToPositionWithOffset(position,10);
        rv.smoothScrollBy(1,0);
    }
}
