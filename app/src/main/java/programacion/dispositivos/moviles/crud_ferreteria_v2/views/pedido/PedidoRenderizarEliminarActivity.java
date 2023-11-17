package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;

public class PedidoRenderizarEliminarActivity extends AppCompatActivity {

    private PedidoOperations pedidoOps;
    private Pedido pedido;

    private static final int ELIMINAR_PEDIDO_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderizar_eliminar_pedido);

        pedidoOps = new PedidoOperations(this);
        pedido = new Pedido(); // Inicializar la variable pedido

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int pedidoId = extras.getInt("PEDIDO_ID");
            cargarPedido(pedidoId);
        }

        setupButtons();
    }

    private void cargarPedido(int pedidoId) {
        pedidoOps.open();
        Cursor cursor = pedidoOps.obtenerPedidoPorID(pedidoId);

        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los índices de las columnas
            int indexDescripcion = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_DESCRIPCION);
            int indexFecha = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_FECHA);
            int indexCantidad = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_CANTIDAD);
            int indexClienteId = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);

            // Obtener los valores de las columnas
            String descripcion = cursor.getString(indexDescripcion);
            String fecha = cursor.getString(indexFecha);
            int cantidad = cursor.getInt(indexCantidad);
            int clienteId = cursor.getInt(indexClienteId);

            // Inicializar la variable pedido con los datos del cursor
            pedido = new Pedido(descripcion, fecha, cantidad, clienteId);
            pedido.setPedidoID(pedidoId);

            // Mostrar los detalles del pedido en la interfaz
            TextView txtDescripcion = findViewById(R.id.txtDescripcionRenderizarPedido);
            TextView txtFecha = findViewById(R.id.txtFechaRenderizarPedido);
            TextView txtCantidad = findViewById(R.id.txtCantidadRenderizarPedido);

            Cliente cliente = cargarCliente(clienteId);

            // Mostrar los detalles del cliente en los TextView correspondientes
            TextView textViewNombreCliente = findViewById(R.id.textViewNombreCliente);
            TextView textViewTelefonoCliente = findViewById(R.id.textViewTelefonoCliente);

            textViewNombreCliente.setText("Nombre del Cliente: " + cliente.getNombre());
            textViewTelefonoCliente.setText("Teléfono del Cliente: " + cliente.getTelefono());

            txtDescripcion.setText("Descripción: " + descripcion);
            txtFecha.setText("Fecha: " + fecha);
            txtCantidad.setText("Cantidad: " + cantidad);

            // Cerrar el cursor
            cursor.close();
        }

        pedidoOps.close();
    }

    private void setupButtons() {
        Button btnEliminarPedido = findViewById(R.id.btnEliminarPedidoRenderizar);
        Button btnVolverListaEliminarPedidos = findViewById(R.id.btnVolverListaEliminarPedidos);

        btnEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPedido();
            }
        });

        btnVolverListaEliminarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Crear un launcher para registrar el callback
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // Verificar el código de resultado
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Confirmación de eliminación
                pedidoOps.open();

                // Verificar el valor del pedido y su ID
                Log.d("Pedido", "Pedido: " + pedido);
                Log.d("Pedido", "ID: " + pedido.getPedidoID());

                // Eliminar el pedido
                if (pedido.getPedidoID() > 0) pedidoOps.eliminarPedido(pedido.getPedidoID());
                pedidoOps.close();

                // Crear un intent para volver a PedidoOperacionesActivity
                Intent intent = new Intent(PedidoRenderizarEliminarActivity.this, PedidoOperacionesActivity.class);

                // Iniciar la actividad
                startActivity(intent);

                // Cerrar la actividad actual
                finish();
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                // Cancelar eliminación
                // No hacemos nada aquí, ya que el usuario canceló la eliminación
            }
        }
    });

    private void eliminarPedido() {
        // Verificar que pedido no sea nulo antes de intentar eliminar
        if (pedido != null) {
            // Mostrar la actividad de confirmación
            Intent intent = new Intent(this, PedidoEliminarActivity.class);
            // Pasar el intent al launcher
            launcher.launch(intent);
        } else {
            Log.e("PedidoRenderizarEliminarActivity", "Intento de eliminar un pedido nulo");
            // Manejar el error según sea necesario
        }
    }

    private Cliente cargarCliente(int clienteId) {
        ClienteOperations clienteOps = new ClienteOperations(this);
        clienteOps.open();

        // Obtener el cliente por su ID
        Cursor cursor = clienteOps.obtenerClientePorID(clienteId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexCedula = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_CEDULA);
            int indexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_NOMBRE);
            int indexDireccion = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_DIRECCION);
            int indexTelefono = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_TELEFONO);

            String cedula = cursor.getString(indexCedula);
            String nombre = cursor.getString(indexNombre);
            String direccion = cursor.getString(indexDireccion);
            String telefono = cursor.getString(indexTelefono);

            // Crear un objeto Cliente con la información obtenida
            Cliente cliente = new Cliente(cedula, nombre, direccion, telefono);
            cliente.setClienteID(clienteId);

            cursor.close();
            clienteOps.close();

            // Asignar el cliente a la variable clienteSeleccionado
            return cliente;
        }

        clienteOps.close();
        return null;
    }
}
