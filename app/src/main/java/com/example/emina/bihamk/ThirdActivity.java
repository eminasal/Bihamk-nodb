package com.example.emina.bihamk;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThirdActivity extends SecondActivity {

        final String[] cantonsIDs = {"46","47","83","84","93","105","110","112","113","117","118"};
        String  [] radarStreets ;
        StringBuilder sb = new StringBuilder();

        private String readStream(InputStream is) throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
            for (String line = r.readLine(); line != null; line =r.readLine()){
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }

        public void displayExceptionMessage(String msg)
        {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }



        /*konekcija na stranicu  */
        protected void connection(int position){

            if(Build.VERSION.SDK_INT>9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                URL url = null;
                try {
                    url = new URL("https://bihamk.ba/bs/spi/radari/"+cantonsIDs[position]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    displayExceptionMessage(e.getMessage());
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    displayExceptionMessage(e.getMessage());
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                    displayExceptionMessage(e.getMessage());
                }
                finally {
                    sb.toString();
                    urlConnection.disconnect();
                    extractFromPage(sb);
                }
            }
        }






        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_third);

            //toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(getDrawable(R.drawable.ic_toolbar));

            //dropdown meni

            Spinner dropdown = findViewById(R.id.spinner);
            dropdown.setPrompt("Select");
            String[] spinner_items = new String[]{"","Zeničko-Dobojski kanton", "Kanton Sarajevo", "Unsko-Sanski kanton",
                    "Tuzlanski kanton", "Srednjobosanski kanton", "Zapadno-Hercegovački kanton",
                    "Kanton 10(Livno)", "Bosansko-Podrinjski kanton", "Hercegovačko-Neretvanski kanton",
                    "Posavski kanton", "Brčko distrikt"};

            final int choice = dropdown.getSelectedItemPosition();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, spinner_items);
            dropdown.setAdapter(adapter);

            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){
                        Toast.makeText(ThirdActivity.this, "Izaberite kanton!",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        position=position-1;

                        if(!isNetworkStatusAvialable (getApplicationContext())) {
                            //no connection
                            Toast toast = Toast.makeText(ThirdActivity.this, "No Internet Connection", Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                        }
                        else{

                        connection(position);
                        final Intent intent;
                        Bundle bundleFirst = new Bundle();
                        Bundle bundleSecond = new Bundle();

                        bundleFirst.putString("cantonID", cantonsIDs[position]);
                        bundleSecond.putStringArray("ulice", radarStreets);

                        FragmentOne fragmentOne = new FragmentOne();
                        FragmentTwo fragmentTwo = new FragmentTwo();

                        fragmentOne.setArguments(bundleFirst);
                        fragmentTwo.setArguments(bundleSecond);
                        intent = new Intent(ThirdActivity.this, FActivity.class).putExtras(bundleFirst).putExtras(bundleSecond);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(ThirdActivity.this, "Selektujte neku opciju!",
                            Toast.LENGTH_LONG).show();

                }
            });


        }

        private void extractFromPage(StringBuilder text) {
            String splitText = text.toString();
            radarStreets = new String[2];
            radarStreets[0]=splitText;
            if(splitText.contains("Nestacionarni radari")) {

                radarStreets[0]=splitText.substring(0,splitText.indexOf("Nestacionarni"));
                splitText = splitText.substring(splitText.indexOf("Najavljene"));
                radarStreets[1] = splitText;
            }
            else{
                radarStreets[1]="Trenutno nemamo podataka o nestacionarnim radarima. Vozite pazljivo!";
            }
        }
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }



}
