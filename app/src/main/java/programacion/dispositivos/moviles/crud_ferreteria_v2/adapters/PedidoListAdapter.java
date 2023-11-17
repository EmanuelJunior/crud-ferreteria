package programacion.dispositivos.moviles.crud_ferreteria_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;

public class PedidoListAdapter extends BaseAdapter {

    private Context context;
    private List<Pedido> pedidoList;

    public PedidoListAdapter(Context context, List<Pedido> pedidoList) {
        this.context = context;
        this.pedidoList = pedidoList;
    }

    @Override
    public int getCount() {
        return pedidoList.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pedido_listar_item, parent, false);
        }

        // Obtén el pedido actual
        Pedido pedido = pedidoList.get(position);

        // Asigna los valores a las vistas del elemento de la lista
        TextView txtDescripcion = convertView.findViewById(R.id.txtDescripcionListar);
        TextView txtFecha = convertView.findViewById(R.id.txtFechaListar);
        TextView txtCantidad = convertView.findViewById(R.id.txtCantidadListar);

        // Configura los valores en las vistas
        txtDescripcion.setText("Descripción: " + pedido.getDescripcion());
        txtFecha.setText("Fecha: " + pedido.getFecha());
        txtCantidad.setText("Cantidad: " + pedido.getCantidad());

        return convertView;
    }
}
