package com.example.emina.bihamk;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_toolbar));


        onCreateView();


        //poziv
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(SecondActivity.this, R.style.todoDialogLight);
                altdial.setMessage("Da li želite nazvati podršku korisnicima?")
                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:1282"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.setTitle("Dialog");
                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.odabir){
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.nama){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_nama, null);

            AlertDialog alertDialog = mBuilder.create();
            alertDialog.setView(mView);
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreateView(){
        ImageView bihamkImage = (ImageView) findViewById(R.id.imageView);
        // set a onclick listener for when the button gets clicked
        bihamkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
