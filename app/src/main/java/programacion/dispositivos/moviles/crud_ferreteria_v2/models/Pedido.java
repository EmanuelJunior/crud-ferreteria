package programacion.dispositivos.moviles.crud_ferreteria_v2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Pedido implements Parcelable {

    private int pedidoID;
    private String descripcion;
    private String fecha;
    private int cantidad;
    private int clienteID;

    public Pedido() {
        // Constructor vacío necesario para algunas operaciones con la base de datos
    }

    public Pedido(String descripcion, String fecha, int cantidad, int clienteID) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.clienteID = clienteID;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    // Implementación de Parcelable para permitir pasar objetos Pedido entre actividades
    protected Pedido(Parcel in) {
        pedidoID = in.readInt();
        descripcion = in.readString();
        fecha = in.readString();
        cantidad = in.readInt();
        clienteID = in.readInt();
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pedidoID);
        dest.writeString(descripcion);
        dest.writeString(fecha);
        dest.writeInt(cantidad);
        dest.writeInt(clienteID);
    }
}
