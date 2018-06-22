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

public class FragmentTwo extends Fragment {

        private TextView textView;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View RootView = inflater.inflate(R.layout.fragment_two_layout, container, false);
            textView=RootView.findViewById(R.id.text);
            Bundle bundle = getActivity().getIntent().getExtras();
            if(bundle != null)
            {
                String [] radarStreets = bundle.getStringArray("ulice");
                TextView textView = (TextView) RootView.findViewById(R.id.text);
                String streets = "";
                final String mimeType = "text/html";
                final String encoding = "UTF-8";
                textView.setText(Html.fromHtml(radarStreets[1]), TextView.BufferType.EDITABLE);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
            else{
                TextView textView = (TextView) RootView.findViewById(R.id.text);
                textView.setText("Trenutno nema nestacionarnih radara");
            }
            return RootView;
        }
}

