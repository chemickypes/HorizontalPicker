package me.bemind.firstelementhorizontalpicker.horizontalpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimAdapterEx;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.List;

import me.bemind.firstelementhorizontalpicker.R;

/**
 * Created by angelomoroni on 23/06/17.
 */

public class HorizontalPicker extends RecyclerView {
    private SlimAdapterEx slimAdapter;
    private LeftmostElementPickerLayoutManager pickerLayoutManager;

    public HorizontalPicker(Context context) {
        super(context);

        init(context);
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {

                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int w = getWidth();
                    addItemDecoration(new EndOffsetDecoration(w - 50));

                    firstSelection();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        StartSnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(this);

        pickerLayoutManager = new LeftmostElementPickerLayoutManager(context, LinearLayoutManager.HORIZONTAL, this, false);

        setLayoutManager(pickerLayoutManager);

        slimAdapter =  SlimAdapter.create()
                .register(R.layout.picker_item_layout, new SlimInjector<String>() {
                    @Override
                    public void onInject(String data, IViewInjector injector) {
                        injector.text(R.id.picker_item,data);
                    }
                }).attachTo(this);

    }

    public void addData(List<?> data){
        slimAdapter.updateData(data);

        post(new Runnable() {
            @Override
            public void run() {
                firstSelection();
            }
        });
    }

    public void setOnScrollStopListener(onScrollStopListener listener){
        if(pickerLayoutManager!=null){
            pickerLayoutManager.setOnScrollStopListener(listener);
        }
    }

    public void firstSelection(){
        pickerLayoutManager.firstSelection();


    }


    public interface onScrollStopListener{

        void selectedView(View view);
    }
}
