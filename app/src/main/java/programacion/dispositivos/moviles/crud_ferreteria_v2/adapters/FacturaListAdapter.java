package programacion.dispositivos.moviles.crud_ferreteria_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;

public class FacturaListAdapter extends BaseAdapter {

    private Context context;
    private List<Factura> facturaList;

    public FacturaListAdapter(Context context, List<Factura> facturaList) {
        this.context = context;
        this.facturaList = facturaList;
    }

    @Override
    public int getCount() {
        return facturaList.size();
    }

    @Override
    public Object getItem(int position) {
        return facturaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.factura_listar_item, parent, false);
        }

        // Obtén la factura actual
        Factura factura = facturaList.get(position);

        // Asigna los valores a las vistas del elemento de la lista
        TextView txtNumero = convertView.findViewById(R.id.txtNumeroListar);
        TextView txtFecha = convertView.findViewById(R.id.txtFechaListar);
        TextView txtTotal = convertView.findViewById(R.id.txtTotalListar);

        // Configura los valores en las vistas
        txtNumero.setText("Número: " + factura.getNumero());
        txtFecha.setText("Fecha: " + factura.getFecha());
        txtTotal.setText("Total: " + factura.getTotal());

        return convertView;
    }
}

