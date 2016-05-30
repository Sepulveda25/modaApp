package com.example.android.modaapp;

/**
 * Created by Fede on 09/11/2015.
 */
public class Ubicacion {
    String id;
    String local;
    String direccion;
    String telefono;
    String email;
    String facebook;
    String latitud;
    String longitud;
    String frente;

    public Ubicacion(String id, String local,String direccion,String telefono,String email,String facebook,String latitud, String longitud,String frente){
        this.id=id;
        this.local=local;
        this.direccion=direccion;
        this.telefono=telefono;
        this.email=email;
        this.facebook=facebook;
        this.latitud=latitud;
        this.longitud=longitud;
        this.frente=frente;
    }

    public String getId(){return id;}
    public String getLocal(){return local;}
    public String getDireccion(){return direccion;}
    public String getTelefono(){return telefono;}
    public String getEmail(){return email;}
    public String getFacebook(){return facebook;}
    public String getLatitud(){
        return latitud;
    }
    public String getLongitud(){
        return longitud;
    }
    public String getFrente(){return frente;}

    public void setid(String id) {
        this.id = id;
    }
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setLongitud(String longitud) {this.longitud = longitud;}

    public void setDireccion(String direccion){this.direccion=direccion;}
    public void setTelefono(String telefono){this.telefono=telefono;}
    public void setEmail(String email){this.email=email;}
    public void setFacebook(String facebook){this.facebook=facebook;}
    public void setFrente(String frente){this.frente=frente;}

}
