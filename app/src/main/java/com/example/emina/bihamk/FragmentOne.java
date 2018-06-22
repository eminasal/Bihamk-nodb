package com.example.emina.bihamk;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOne extends Fragment {

    private TextView textView;
    private View RootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = (TextView) RootView.findViewById(R.id.text);
        View RootView = inflater.inflate(R.layout.fragment_one_layout, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null)
        {
            String [] radarStreets = bundle.getStringArray("ulice");
            String streets = radarStreets[0].substring(radarStreets[0].indexOf("Stacionarni")+27);
            textView.setText(Html.fromHtml(streets), TextView.BufferType.EDITABLE);
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
        else{

            textView.setText("Trenutno nema stacionarnih radara");
        }
        return RootView;
    }
}