package programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.FacturaOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente.ClientesListarActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidosListarActivity;

// Importes omitidos para brevedad

public class FacturaActualizarActivity extends AppCompatActivity {

    private FacturaOperations facturaOps;
    private Pedido pedidoSeleccionado;
    private Factura factura;

    private EditText editNumero;
    private EditText editFecha;
    private EditText editTotal;

    private static final int SELECCIONAR_PEDIDO_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_factura);

        facturaOps = new FacturaOperations(this);

        editNumero = findViewById(R.id.editNumeroActualizarFactura);
        editFecha = findViewById(R.id.editFechaActualizarFactura);
        editTotal = findViewById(R.id.editTotalActualizarFactura);

        Button btnSeleccionarPedido = findViewById(R.id.btnSeleccionarPedidoActualizarFactura);
        Button btnGuardarActualizacionFactura = findViewById(R.id.btnGuardarActualizacionFactura);
        Button btnVolverOperaciones = findViewById(R.id.btnCancelarActualizacionFactura);

        // Obtener el ID de la factura que se va a actualizar
        int facturaId = getIntent().getIntExtra("FACTURA_ID", -1);

        if (facturaId != -1) {
            // Cargar los detalles de la factura
            cargarFactura(facturaId);
        } else {
            // Manejar el caso en el que no se proporciona un ID de factura válido
            Toast.makeText(this, "Error al obtener el ID de la factura", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSeleccionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarPedido();
            }
        });

        btnGuardarActualizacionFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarActualizacionFactura();
            }
        });

        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
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
                // Obtener el intent de la actividad
                Intent data = result.getData();
                // Obtener el pedido seleccionado del intent
                pedidoSeleccionado = data.getParcelableExtra("PEDIDO_SELECCIONADO");

                // Mostrar los detalles del pedido en los TextView correspondientes
                TextView textViewDescripcionPedido = findViewById(R.id.textViewDescripcionPedidoActualizarFactura);
                TextView textViewFechaPedido = findViewById(R.id.textViewFechaPedidoActualizarFactura);
                TextView textViewCantidadPedido = findViewById(R.id.textViewCantidadPedidoActualizarFactura);

                textViewDescripcionPedido.setText("Descripción del Pedido: " + pedidoSeleccionado.getDescripcion());
                textViewFechaPedido.setText("Fecha del Pedido: " + pedidoSeleccionado.getFecha());
                textViewCantidadPedido.setText("Cantidad del Pedido: " + String.valueOf(pedidoSeleccionado.getCantidad()));
                // Puedes realizar acciones con el pedido seleccionado si es necesario
            }
        }
    });

    private void seleccionarPedido() {
        // Lanzar la actividad para listar los pedidos
        Intent intent = new Intent(this, PedidosListarActivity.class);
        intent.putExtra("DESDE_ACTUALIZAR_FACTURA", true);

        // Pasar el intent al launcher
        launcher.launch(intent);
    }

    private void cargarFactura(int facturaId) {
        facturaOps.open();
        Cursor cursor = facturaOps.obtenerFacturaPorID(facturaId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexNumero = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_NUMERO);
            int indexFecha = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_FECHA);
            int indexTotal = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_TOTAL);
            int indexPedidoID = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_ID);

            String numero = cursor.getString(indexNumero);
            String fecha = cursor.getString(indexFecha);
            double total = cursor.getDouble(indexTotal);
            int pedidoID = cursor.getInt(indexPedidoID);

            // Puedes agregar la lógica para cargar el pedido asociado a la factura si es necesario
            // Para simplificar, asumiré que tienes un método que carga un pedido por su ID
            factura = new Factura(numero, fecha, total, pedidoID);
            factura.setFacturaID(facturaId);

            // Cargar detalles del pedido (esto es un ejemplo, asegúrate de tener un método para cargar pedido por ID)
            cargarPedido(pedidoID);

            // Mostrar los detalles del pedido en los TextView correspondientes
            TextView textViewDescripcionPedido = findViewById(R.id.textViewDescripcionPedidoActualizarFactura);
            TextView textViewFechaPedido = findViewById(R.id.textViewFechaPedidoActualizarFactura);
            TextView textViewCantidadPedido = findViewById(R.id.textViewCantidadPedidoActualizarFactura);

            textViewDescripcionPedido.setText("Descripción del Pedido: " + pedidoSeleccionado.getDescripcion());
            textViewFechaPedido.setText("Fecha del Pedido: " + pedidoSeleccionado.getFecha());
            textViewCantidadPedido.setText("Cantidad del Pedido: " + String.valueOf(pedidoSeleccionado.getCantidad()));

            // Mostrar los detalles de la factura en la interfaz
            editNumero.setText(numero);
            editFecha.setText(fecha);
            editTotal.setText(String.valueOf(total));

            cursor.close();
        }

        facturaOps.close();
    }

    private void cargarPedido(int pedidoId) {
        PedidoOperations pedidoOps = new PedidoOperations(this);
        pedidoOps.open();

        // Obtener el pedido por su ID
        Cursor cursor = pedidoOps.obtenerPedidoPorID(pedidoId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexDescripcion = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_DESCRIPCION);
            int indexFechaPedido = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_FECHA);
            int indexCantidadPedido = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_CANTIDAD);
            int indexClienteID = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);

            String descripcionPedido = cursor.getString(indexDescripcion);
            String fechaPedido = cursor.getString(indexFechaPedido);
            int cantidadPedido = cursor.getInt(indexCantidadPedido);
            int clienteID = cursor.getInt(indexClienteID);

            // Crear un objeto Pedido con la información obtenida
            Pedido pedido = new Pedido(descripcionPedido, fechaPedido, cantidadPedido, clienteID);
            pedido.setPedidoID(pedidoId);

            // Asignar el pedido a la variable pedidoSeleccionado
            pedidoSeleccionado = pedido;

            cursor.close();
        }

        pedidoOps.close();
    }

    private void guardarActualizacionFactura() {
        // Verificar que se haya seleccionado un pedido
        if (pedidoSeleccionado != null) {

            String numero = editNumero.getText().toString().trim();
            String fecha = editFecha.getText().toString().trim();
            String totalString = editTotal.getText().toString().trim();

            // Validar que el número, fecha y total no estén vacíos
            if (numero.isEmpty() || fecha.isEmpty() || totalString.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = Double.parseDouble(totalString);

            factura.setNumero(numero);
            factura.setFecha(fecha);
            factura.setTotal(total);
            factura.setPedidoID(pedidoSeleccionado.getPedidoID());

            facturaOps.open();

            // Puedes realizar acciones adicionales o validaciones antes de actualizar la factura
            long resultado = facturaOps.actualizarFactura(factura);

            if (resultado != -1) {
                Toast.makeText(this, "Factura actualizada correctamente", Toast.LENGTH_SHORT).show();

                // Crear un intent para volver a FacturaOperacionesActivity
                Intent intent = new Intent(FacturaActualizarActivity.this, FacturaOperacionesActivity.class);

                // Iniciar la actividad
                startActivity(intent);

                // Cerrar la actividad actual
                finish();

            } else {
                Toast.makeText(this, "Error al actualizar la factura", Toast.LENGTH_SHORT).show();
            }

            facturaOps.close();
        } else {
            Toast.makeText(this, "Seleccione un pedido antes de actualizar la factura", Toast.LENGTH_SHORT).show();
        }
    }
}
