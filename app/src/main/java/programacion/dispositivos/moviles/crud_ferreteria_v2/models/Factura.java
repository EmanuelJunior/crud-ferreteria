package programacion.dispositivos.moviles.crud_ferreteria_v2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Factura implements Parcelable {

    private int facturaID;
    private String numero;
    private String fecha;
    private double total;
    private int pedidoID;

    public Factura() {
        // Constructor vacío necesario para algunas operaciones con la base de datos
    }

    public Factura(String numero, String fecha, double total, int pedidoID) {
        this.numero = numero;
        this.fecha = fecha;
        this.total = total;
        this.pedidoID = pedidoID;
    }

    public int getFacturaID() {
        return facturaID;
    }

    public void setFacturaID(int facturaID) {
        this.facturaID = facturaID;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    // Implementación de Parcelable para permitir pasar objetos Factura entre actividades
    protected Factura(Parcel in) {
        facturaID = in.readInt();
        numero = in.readString();
        fecha = in.readString();
        total = in.readDouble();
        pedidoID = in.readInt();
    }

    public static final Creator<Factura> CREATOR = new Creator<Factura>() {
        @Override
        public Factura createFromParcel(Parcel in) {
            return new Factura(in);
        }

        @Override
        public Factura[] newArray(int size) {
            return new Factura[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(facturaID);
        dest.writeString(numero);
        dest.writeString(fecha);
        dest.writeDouble(total);
        dest.writeInt(pedidoID);
    }
}

