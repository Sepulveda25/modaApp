package com.example.android.modaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TabHost TbH;
    private HttpURLConnection con;
    private double latitud;
    private double longitud;
    private TextView textoHome;


    private List<Ubicacion> listaUbicaciones=new ArrayList<>();
    private HashMap<Marker, Ubicacion> mMarkersHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
            Arranca el TabHost
         */
        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost lo activamos
        TbH.setup();
        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");

        tab1.setIndicator("Mapa");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.linearLayout); //definimos el id de cada Tab (pestaña)

        tab2.setIndicator("Tips");
        tab2.setContent(R.id.linearLayout2);

        tab3.setIndicator("Info");
        tab3.setContent(R.id.linearLayout3);

        tab4.setIndicator("Nosotros");
        tab4.setContent(R.id.linearLayout4);

        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);


        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                new JsonTask().execute(new URL("http://modasincomplejos.esy.es/db_nuevo.php"));
            } else {
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        textoHome = (TextView) findViewById(R.id.textView);



    }

    public class JsonTask extends AsyncTask<URL, Void, List<Ubicacion>> {

        @Override
        protected List<Ubicacion> doInBackground(URL... urls) {
            List<Ubicacion> ubicaciones = null;

            try {

                // Establecer la conexión
                con = (HttpURLConnection) urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if (statusCode != 200) {

                    ubicaciones = new ArrayList<>();
                    ubicaciones.add(new Ubicacion("Error", null, null, null,null,null,null,null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    GsonUbicacionParser parser = new GsonUbicacionParser();
                    ubicaciones = parser.leerFlujoJson(in);

                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                con.disconnect();
            }

            return ubicaciones;
        }


        protected void onPostExecute(List<Ubicacion> ubicaciones) {
            listaUbicaciones=ubicaciones;


                /*
                Se cargan los puntos en el mapa de forma iterativa
                 */
            if (ubicaciones != null) {


                for (int i=0;i<ubicaciones.size();i++) {
                    latitud = Double.parseDouble(ubicaciones.get(i).getLatitud());
                    longitud = Double.parseDouble(ubicaciones.get(i).getLongitud());

                    LatLng lugar = new LatLng(latitud, longitud);
                    mMap.addMarker(new MarkerOptions().position(lugar).title(ubicaciones.get(i).getLocal()).snippet(ubicaciones.get(i).getDireccion()));

                }


            }else {
                Toast.makeText(
                        getBaseContext(),
                        "No se cargaron los puntos, intentalo nuevamente",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            /*
            Vista principal para el mapa
             */
            LatLng cordoba = new LatLng(-31.420131, -64.188854);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cordoba));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cordoba, 11));
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


}
