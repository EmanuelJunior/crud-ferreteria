package programacion.dispositivos.moviles.crud_ferreteria_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductoListAdapter extends BaseAdapter {

    private Context context;
    private List<Producto> productoList;

    public ProductoListAdapter(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @Override
    public int getCount() {
        return productoList.size();
    }

    @Override
    public Object getItem(int position) {
        return productoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.producto_listar_item, parent, false);
        }

        // Obtén el producto actual
        Producto producto = productoList.get(position);

        // Asigna los valores a las vistas del elemento de la lista
        TextView txtDescripcion = convertView.findViewById(R.id.txtDescripcionListar);
        TextView txtValor = convertView.findViewById(R.id.txtValorListar);

        // Configura los valores en las vistas
        txtDescripcion.setText("Descripción: " + producto.getDescripcion());
        txtValor.setText("Valor: " + producto.getValor());

        return convertView;
    }
}

