package programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.FacturaOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidoOperacionesActivity;

public class FacturaRenderizarEliminarActivity extends AppCompatActivity {

    private FacturaOperations facturaOps;
    private PedidoOperations pedidoOps;
    private Factura factura;
    private Pedido pedido; // Agregamos una variable para almacenar el Pedido asociado

    private static final int ELIMINAR_FACTURA_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderizar_eliminar_factura);

        facturaOps = new FacturaOperations(this);
        pedidoOps = new PedidoOperations(this);
        factura = new Factura(); // Inicializar la variable factura
        pedido = new Pedido(); // Inicializar la variable pedido

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int facturaId = extras.getInt("FACTURA_ID");
            cargarFactura(facturaId);
        }

        setupButtons();
    }

    private void cargarFactura(int facturaId) {
        facturaOps.open();
        Cursor cursor = facturaOps.obtenerFacturaPorID(facturaId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexNumero = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_NUMERO);
            int indexFecha = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_FECHA);
            int indexTotal = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_TOTAL);
            int indexPedidoId = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_ID);

            String numero = cursor.getString(indexNumero);
            String fecha = cursor.getString(indexFecha);
            double total = cursor.getDouble(indexTotal);
            int pedidoId = cursor.getInt(indexPedidoId);

            // Cargar el pedido asociado a la factura
            cargarPedido(pedidoId);

            factura = new Factura(numero, fecha, total, pedidoId);
            factura.setFacturaID(facturaId);

            // Mostrar detalles de la factura en la interfaz
            TextView txtNumero = findViewById(R.id.txtNumeroRenderizarFactura);
            TextView txtFecha = findViewById(R.id.txtFechaRenderizarFactura);
            TextView txtTotal = findViewById(R.id.txtTotalRenderizarFactura);

            txtNumero.setText("Número: " + numero);
            txtFecha.setText("Fecha: " + fecha);
            txtTotal.setText("Total: " + total);

            // Mostrar detalles del pedido en la interfaz
            mostrarDetallesPedido();

            cursor.close();
        }

        facturaOps.close();
    }

    private void cargarPedido(int pedidoId) {
        pedidoOps.open();
        Cursor cursor = pedidoOps.obtenerPedidoPorID(pedidoId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexDescripcion = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_DESCRIPCION);
            int indexFecha = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_FECHA);
            int indexCantidad = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_CANTIDAD);
            int indexClienteId = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);

            String descripcion = cursor.getString(indexDescripcion);
            String fecha = cursor.getString(indexFecha);
            int cantidad = cursor.getInt(indexCantidad);
            int clienteId = cursor.getInt(indexClienteId);

            pedido = new Pedido(descripcion, fecha, cantidad, clienteId);
            pedido.setPedidoID(pedidoId);

            cursor.close();
        }

        pedidoOps.close();
    }

    private void setupButtons() {
        Button btnEliminarFactura = findViewById(R.id.btnEliminarFacturaRenderizar);
        Button btnVolverListaEliminarFacturas = findViewById(R.id.btnVolverListaEliminarFacturas);

        btnEliminarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarFactura();
            }
        });

        btnVolverListaEliminarFacturas.setOnClickListener(new View.OnClickListener() {
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
                facturaOps.open();

                // Verificar el valor del pedido y su ID
                Log.d("Factura", "Factura: " + factura);
                Log.d("Factura", "ID: " + factura.getPedidoID());

                // Eliminar el pedido
                if (factura.getFacturaID() > 0) facturaOps.eliminarFactura(factura.getFacturaID());
                facturaOps.close();

                // Crear un intent para volver a PedidoOperacionesActivity
                Intent intent = new Intent(FacturaRenderizarEliminarActivity.this, FacturaOperacionesActivity.class);

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

    private void eliminarFactura() {
        // Verificar que pedido no sea nulo antes de intentar eliminar
        if (factura != null) {
            // Mostrar la actividad de confirmación
            Intent intent = new Intent(this, FacturaEliminarActivity.class);
            // Pasar el intent al launcher
            launcher.launch(intent);
        } else {
            Log.e("FacturaRenderizarEliminarActivity", "Intento de eliminar una factura nulo");
            // Manejar el error según sea necesario
        }
    }

    private void mostrarDetallesPedido() {
        // Mostrar detalles del pedido en la interfaz
        TextView txtDescripcionPedido = findViewById(R.id.textViewDescripcionPedido);
        TextView txtFechaPedido = findViewById(R.id.textViewFechaPedido);
        TextView txtCantidadPedido = findViewById(R.id.textViewCantidadPedido);;

        if (pedido != null) {
            txtDescripcionPedido.setText("Descripción del Pedido: " + pedido.getDescripcion());
            txtFechaPedido.setText("Fecha del Pedido: " + pedido.getFecha());
            txtCantidadPedido.setText("Cantidad del Pedido: " + String.valueOf(pedido.getCantidad()));

            // Mostrar detalles del cliente en la interfaz
            mostrarDetallesCliente(pedido.getClienteID());
        } else {
            Log.e("FacturaRenderizarEliminar", "El pedido asociado es nulo");
            // Manejar el error según sea necesario
        }
    }

    private void mostrarDetallesCliente(int clienteId) {
        ClienteOperations clienteOps = new ClienteOperations(this);
        clienteOps.open();

        // Obtener el cliente por su ID
        Cursor cursor = clienteOps.obtenerClientePorID(clienteId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_NOMBRE);
            int indexTelefono = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_TELEFONO);

            String nombre = cursor.getString(indexNombre);
            String telefono = cursor.getString(indexTelefono);

            // Mostrar detalles del cliente en la interfaz
            TextView txtNombreCliente = findViewById(R.id.textViewNombreCliente);
            TextView txtTelefonoCliente = findViewById(R.id.textViewTelefonoCliente);

            txtNombreCliente.setText("Nombre del Cliente: " + nombre);
            txtTelefonoCliente.setText("Teléfono del Cliente: " + telefono);

            cursor.close();
            clienteOps.close();
        }
    }
}
