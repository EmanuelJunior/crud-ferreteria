package programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.adapters.ClienteListAdapter;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClientesListarActualizarActivity extends AppCompatActivity {

    private ClienteOperations clienteOps;
    private List<Cliente> clienteList;
    private ClienteListAdapter clienteAdapter;
    private ListView listaClientes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actualizar_clientes);

        clienteOps = new ClienteOperations(this);
        clienteList = new ArrayList<>();

        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesDesdeActualizar);
        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaClientes = findViewById(R.id.listaActualizarClientes);
        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);
                abrirActualizarCliente(clienteSeleccionado.getClienteID());
            }
        });

        cargarClientes();
    }

    private void cargarClientes() {
        clienteOps.open();
        Cursor cursor = clienteOps.obtenerTodosClientes();

        while (cursor != null && cursor.moveToNext()) {
            int clienteIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);
            int cedulaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_CEDULA);
            int nombreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_NOMBRE);
            int direccionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_DIRECCION);
            int telefonoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_TELEFONO);

            // Verificar que todas las columnas existan
            if (clienteIdIndex != -1 && cedulaIndex != -1 && nombreIndex != -1 && direccionIndex != -1 && telefonoIndex != -1) {
                int clienteId = cursor.getInt(clienteIdIndex);
                String cedula = cursor.getString(cedulaIndex);
                String nombre = cursor.getString(nombreIndex);
                String direccion = cursor.getString(direccionIndex);
                String telefono = cursor.getString(telefonoIndex);

                Cliente cliente = new Cliente(cedula, nombre, direccion, telefono);
                cliente.setClienteID(clienteId);
                clienteList.add(cliente);
            } else {
                // Manejar la situación donde una columna no existe en el cursor
                Log.e("CursorError", "Al menos una columna no existe en el cursor");
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        clienteAdapter = new ClienteListAdapter(this, clienteList);
        listaClientes.setAdapter(clienteAdapter);

        clienteOps.close();
    }

    private void abrirActualizarCliente(int clienteId) {
        Intent intent = new Intent(this, ClienteActualizarActivity.class);
        intent.putExtra("CLIENTE_ID", clienteId);
        startActivity(intent);
    }
}
