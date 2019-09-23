package com.example.optimusway.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.optimusway.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Home extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button btnGetDirection;
    MarkerOptions saida, destino;
    Polyline currentPolyline;
    Integer google_play_services_version = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        btnGetDirection = findViewById(R.id.btnGetDirection);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //27.658143,85.3199503
        //27.667491,85.3208583
        saida = new MarkerOptions().position(new LatLng( 27.658143,85.3199503)).title("Saída");
        destino = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Destino");

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getUrl(saida.getPosition(), destino.getPosition(), "Dirigindo");
                new FetchURL(Home.this).execute(url, "Dirigindo");
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(saida);
        map.addMarker(destino);
    }
    private String getUrl (LatLng origin, LatLng dest, String directionMode) {
        //origem da rota
        String str_origin = "Origem= " + origin.latitude + "," + origin.longitude;
        //destino da rota
        String str_dest = "Destino= " + dest.latitude + "," + dest.longitude;
        //Mode
        String mode = "Modo= " + directionMode;
        //Construindo os parametros para o serviço web
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        //Formato de saída
        String output = "json";
        //Construindo o url para o serviço web
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +"& key" +  getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
