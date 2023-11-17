package programacion.dispositivos.moviles.crud_ferreteria_v2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {

    private int clienteID;
    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;

    public Cliente() {
        // Constructor vacío necesario para algunas operaciones con la base de datos
    }

    public Cliente(String cedula, String nombre, String direccion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Implementación de Parcelable para permitir pasar objetos Cliente entre actividades
    protected Cliente(Parcel in) {
        clienteID = in.readInt();
        cedula = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        telefono = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clienteID);
        dest.writeString(cedula);
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeString(telefono);
    }
}
