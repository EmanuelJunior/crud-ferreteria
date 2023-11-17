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
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidoActualizarActivity;

public class ClientesListarActivity extends AppCompatActivity {

    private ClienteOperations clienteOps;
    private List<Cliente> clienteList;
    private ClienteListAdapter clienteAdapter;
    private ListView listaClientes;

    private boolean desdePedido; // Variable para identificar si se abrió desde PedidoActivity
    private String desdeActualizarOInsertar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        clienteOps = new ClienteOperations(this);
        clienteList = new ArrayList<>();

        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesDesdeLista);
        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaClientes = findViewById(R.id.listaClientes);

        // Obtener información de la actividad que la abrió
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("DESDE_PEDIDO")) {
            desdePedido = intent.getBooleanExtra("DESDE_PEDIDO", false);
            desdeActualizarOInsertar = "DESDE_PEDIDO";
        } else if ( intent != null && intent.hasExtra("DESDE_ACTUALIZAR_PEDIDO") ) {
            desdePedido = intent.getBooleanExtra("DESDE_ACTUALIZAR_PEDIDO", false);
            desdeActualizarOInsertar = "DESDE_ACTUALIZAR_PEDIDO";
        }

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
                Log.e("CursorError", "Al menos una columna no existe en el cursor");
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        clienteAdapter = new ClienteListAdapter(this, clienteList);
        listaClientes.setAdapter(clienteAdapter);

        // Agregar listener solo si se abrió desde PedidoActivity
        if (desdePedido) {
            listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cliente clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);

                    if ( desdeActualizarOInsertar == "DESDE_PEDIDO" ) {
                        devolverClienteSeleccionado(clienteSeleccionado);
                    } else if ( desdeActualizarOInsertar == "DESDE_ACTUALIZAR_PEDIDO" ){
                        abrirActualizarPedido(clienteSeleccionado);
                    }
                }
            });
        }

        clienteOps.close();
    }

    private void devolverClienteSeleccionado(Cliente cliente) {
        Intent resultadoIntent = new Intent();
        resultadoIntent.putExtra("CLIENTE_SELECCIONADO", cliente);
        setResult(RESULT_OK, resultadoIntent);
        finish();
    }

    private void abrirActualizarPedido(Cliente cliente) {
        Intent intent = new Intent(this, PedidoActualizarActivity.class);
        intent.putExtra("CLIENTE_SELECCIONADO", cliente);
        setResult(RESULT_OK, intent);
        finish();
    }
}
