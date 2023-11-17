package programacion.dispositivos.moviles.crud_ferreteria_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClienteListAdapter extends BaseAdapter {

    private Context context;
    private List<Cliente> clienteList; // Cambia esto según la entidad correspondiente

    public ClienteListAdapter(Context context, List<Cliente> clienteList) {
        this.context = context;
        this.clienteList = clienteList;
    }

    @Override
    public int getCount() {
        return clienteList.size();
    }

    @Override
    public Object getItem(int position) {
        return clienteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cliente_listar_item, parent, false); // Cambia R.layout.cliente_list_item según la entidad
        }

        // Obtén el cliente actual
        Cliente cliente = clienteList.get(position); // Cambia Cliente según la entidad

        // Asigna los valores a las vistas del elemento de la lista
        TextView txtCedula = convertView.findViewById(R.id.txtCedulaListar); // Cambia según la entidad
        TextView txtNombre = convertView.findViewById(R.id.txtNombreListar); // Cambia según la entidad
        TextView txtDireccion = convertView.findViewById(R.id.txtDireccionListar); // Cambia según la entidad
        TextView txtTelefono = convertView.findViewById(R.id.txtTelefonoListar); // Cambia según la entidad

        // Configura los valores en las vistas
        txtCedula.setText("Cédula: " + cliente.getCedula()); // Cambia según la entidad
        txtNombre.setText("Nombre: " + cliente.getNombre()); // Cambia según la entidad
        txtDireccion.setText("Dirección: " + cliente.getDireccion()); // Cambia según la entidad
        txtTelefono.setText("Teléfono: " + cliente.getTelefono()); // Cambia según la entidad

        return convertView;
    }
}

