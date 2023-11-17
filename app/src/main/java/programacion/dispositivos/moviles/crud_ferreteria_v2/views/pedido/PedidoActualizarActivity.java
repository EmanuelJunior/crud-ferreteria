package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente.ClientesListarActivity;

public class PedidoActualizarActivity extends AppCompatActivity {

    private PedidoOperations pedidoOps;
    private Cliente clienteSeleccionado;
    private Pedido pedido;

    private EditText editDescripcion;
    private EditText editFecha;
    private EditText editCantidad;

    private static final int SELECCIONAR_CLIENTE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pedido);

        pedidoOps = new PedidoOperations(this);

        editDescripcion = findViewById(R.id.editDescripcionActualizarPedido);
        editFecha = findViewById(R.id.editFechaActualizarPedido);
        editCantidad = findViewById(R.id.editCantidadActualizarPedido);

        Button btnSeleccionarCliente = findViewById(R.id.btnSeleccionarClienteActualizar);
        Button btnGuardarActualizacionPedido = findViewById(R.id.btnGuardarActualizacionPedido);
        Button btnVolverOperaciones = findViewById(R.id.btnCancelarActualizacionPedido);

        // Obtener el ID del pedido que se va a actualizar
        int pedidoId = getIntent().getIntExtra("PEDIDO_ID", -1);

        if (pedidoId != -1) {
            // Cargar los detalles del pedido
            cargarPedido(pedidoId);
        } else {
            // Manejar el caso en el que no se proporciona un ID de pedido válido
            Toast.makeText(this, "Error al obtener el ID del pedido", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSeleccionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarCliente();
            }
        });

        btnGuardarActualizacionPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarActualizacionPedido();
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
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult (new ActivityResultContracts.StartActivityForResult (), new ActivityResultCallback<ActivityResult> () {
        @Override
        public void onActivityResult (ActivityResult result) {
            // Verificar el código de resultado
            if (result.getResultCode () == Activity.RESULT_OK) {
                // Obtener el intent de la actividad
                Intent data = result.getData();
                // Obtener el cliente seleccionado del intent
                clienteSeleccionado = data.getParcelableExtra ("CLIENTE_SELECCIONADO");

                // Mostrar los detalles del cliente en los TextView correspondientes
                TextView textViewNombreCliente = findViewById(R.id.textViewNombreCliente);
                TextView textViewTelefonoCliente = findViewById(R.id.textViewTelefonoCliente);

                textViewNombreCliente.setText("Nombre del Cliente: " + clienteSeleccionado.getNombre());
                textViewTelefonoCliente.setText("Teléfono del Cliente: " + clienteSeleccionado.getTelefono());
                // Puedes realizar acciones con el cliente seleccionado si es necesario
            }
        }
    });

    private void seleccionarCliente() {
        // Lanzar la actividad para listar los clientes
        Intent intent = new Intent(this, ClientesListarActivity.class);
        intent.putExtra("DESDE_ACTUALIZAR_PEDIDO", true);

        // Pasar el intent al launcher
        launcher.launch(intent);
    }

    private void cargarPedido(int pedidoId) {
        pedidoOps.open();
        Cursor cursor = pedidoOps.obtenerPedidoPorID(pedidoId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexDescripcion = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_DESCRIPCION);
            int indexFecha = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_FECHA);
            int indexCantidad = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_CANTIDAD);
            int indexClienteID = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_ID);

            String descripcion = cursor.getString(indexDescripcion);
            String fecha = cursor.getString(indexFecha);
            int cantidad = cursor.getInt(indexCantidad);
            int clienteID = cursor.getInt(indexClienteID);

            // Puedes agregar la lógica para cargar el cliente asociado al pedido si es necesario
            // Para simplificar, asumiré que tienes un método que carga un cliente por su ID
            pedido = new Pedido( descripcion, fecha, cantidad, clienteID );
            pedido.setPedidoID(pedidoId);

            // Cargar detalles del cliente (esto es un ejemplo, asegúrate de tener un método para cargar cliente por ID)
            cargarCliente(clienteID);

            // Mostrar los detalles del cliente en los TextView correspondientes
            TextView textViewNombreCliente = findViewById(R.id.textViewNombreCliente);
            TextView textViewTelefonoCliente = findViewById(R.id.textViewTelefonoCliente);

            textViewNombreCliente.setText("Nombre del Cliente: " + clienteSeleccionado.getNombre());
            textViewTelefonoCliente.setText("Teléfono del Cliente: " + clienteSeleccionado.getTelefono());

            // Mostrar los detalles del pedido en la interfaz
            editDescripcion.setText(descripcion);
            editFecha.setText(fecha);
            editCantidad.setText(String.valueOf(cantidad));

            cursor.close();
        }

        pedidoOps.close();
    }

    private void cargarCliente(int clienteId) {
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

            // Asignar el cliente a la variable clienteSeleccionado
            clienteSeleccionado = cliente;

            cursor.close();
        }

        clienteOps.close();
    }

    private void guardarActualizacionPedido() {
        // Verificar que se haya seleccionado un cliente
        if (clienteSeleccionado != null) {

            String descripcion = editDescripcion.getText().toString().trim();
            String fecha = editFecha.getText().toString().trim();
            String cantidadString = editCantidad.getText().toString().trim();

            // Validar que la descripción, fecha y cantidad no estén vacías
            if (descripcion.isEmpty() || fecha.isEmpty() || cantidadString.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int cantidad = Integer.parseInt(cantidadString);

            pedido.setDescripcion(descripcion);
            pedido.setFecha(fecha);
            pedido.setCantidad(cantidad);
            pedido.setClienteID( clienteSeleccionado.getClienteID() );

            pedidoOps.open();

            // Puedes realizar acciones adicionales o validaciones antes de actualizar el pedido
            long resultado = pedidoOps.actualizarPedido(pedido);

            if (resultado != -1) {
                Toast.makeText(this, "Pedido actualizado correctamente", Toast.LENGTH_SHORT).show();

                // Crear un intent para volver a ProductoOperacionesActivity
                Intent intent = new Intent(PedidoActualizarActivity.this, PedidoOperacionesActivity.class);

                // Iniciar la actividad
                startActivity(intent);

                // Cerrar la actividad actual
                finish();

            } else {
                Toast.makeText(this, "Error al actualizar el pedido", Toast.LENGTH_SHORT).show();
            }

            pedidoOps.close();
        } else {
            Toast.makeText(this, "Seleccione un cliente antes de actualizar el pedido", Toast.LENGTH_SHORT).show();
        }
    }
}
