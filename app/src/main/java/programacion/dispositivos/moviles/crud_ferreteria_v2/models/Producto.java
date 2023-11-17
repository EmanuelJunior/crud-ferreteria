package programacion.dispositivos.moviles.crud_ferreteria_v2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    private int productoID;
    private String descripcion;
    private double valor;

    public Producto() {
        // Constructor vacío necesario para algunas operaciones con la base de datos
    }

    public Producto(String descripcion, double valor) {
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // Implementación de Parcelable para permitir pasar objetos Producto entre actividades
    protected Producto(Parcel in) {
        productoID = in.readInt();
        descripcion = in.readString();
        valor = in.readDouble();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productoID);
        dest.writeString(descripcion);
        dest.writeDouble(valor);
    }
}
