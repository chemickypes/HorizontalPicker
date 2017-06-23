package me.bemind.firstelementhorizontalpicker.horizontalpicker;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by angelomoroni on 22/06/17.
 */

public class EndOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mEndOffset;

    public EndOffsetDecoration(int bottomOffset) {
        mEndOffset = bottomOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, mEndOffset, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }

    }
}
