package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.adapters.PedidoListAdapter;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;

public class PedidosListarActualizarActivity extends AppCompatActivity {

    private PedidoOperations pedidoOps;
    private List<Pedido> pedidoList;
    private PedidoListAdapter pedidoAdapter;
    private ListView listaPedidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actualizar_pedidos);

        pedidoOps = new PedidoOperations(this);
        pedidoList = new ArrayList<>();

        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesDesdeActualizarPedidos);
        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaPedidos = findViewById(R.id.listaActualizarPedidos);
        listaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido pedidoSeleccionado = (Pedido) parent.getItemAtPosition(position);
                abrirActualizarPedido(pedidoSeleccionado.getPedidoID());
            }
        });

        cargarPedidos();
    }

    private void cargarPedidos() {
        pedidoOps.open();
        Cursor cursor = pedidoOps.obtenerTodosPedidos();

        while (cursor != null && cursor.moveToNext()) {
            int pedidoIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_ID);
            int descripcionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_DESCRIPCION);
            int fechaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_FECHA);
            int cantidadIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_CANTIDAD);
            int clienteIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);

            if (pedidoIdIndex != -1 && descripcionIndex != -1 && fechaIndex != -1 && cantidadIndex != -1 && clienteIdIndex != -1) {
                int pedidoId = cursor.getInt(pedidoIdIndex);
                String descripcion = cursor.getString(descripcionIndex);
                String fecha = cursor.getString(fechaIndex);
                int cantidad = cursor.getInt(cantidadIndex);
                int clienteId = cursor.getInt(clienteIdIndex);

                Pedido pedido = new Pedido(descripcion, fecha, cantidad, clienteId);
                pedido.setPedidoID(pedidoId);
                pedidoList.add(pedido);
            } else {
                Log.e("CursorError", "Al menos una columna no existe en el cursor");
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        pedidoAdapter = new PedidoListAdapter(this, pedidoList);
        listaPedidos.setAdapter(pedidoAdapter);

        pedidoOps.close();
    }

    private void abrirActualizarPedido(int pedidoId) {
        Intent intent = new Intent(this, PedidoActualizarActivity.class);
        intent.putExtra("PEDIDO_ID", pedidoId);
        startActivity(intent);
    }
}
