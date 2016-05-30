package com.example.android.modaapp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Creado por Hermosa Programación.
 */
public class GsonUbicacionParser {


    public List<Ubicacion> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Ubicacion> ubic= new ArrayList<>();

        // Iniciar el array
        reader.beginArray();

        while (reader.hasNext()) {
            // Lectura de objetos
            Ubicacion ubicacion = gson.fromJson(reader, Ubicacion.class);
            ubic.add(ubicacion);
        }

        reader.endArray();
        reader.close();

        return ubic;
    }
}
