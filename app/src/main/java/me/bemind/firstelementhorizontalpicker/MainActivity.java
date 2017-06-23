package me.bemind.firstelementhorizontalpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.bemind.firstelementhorizontalpicker.horizontalpicker.HorizontalPicker;

public class MainActivity extends AppCompatActivity {

    private HorizontalPicker rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (HorizontalPicker) findViewById(R.id.rv);

        rv.setOnScrollStopListener(new HorizontalPicker.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                if(view instanceof TextView) {
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), view.getClass().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        rv.addData(getListString());
    }

    public List<String> getListString() {

        List<String> listString = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            listString.add("s: "+(i+1));
        }
        return listString;
    }
}
