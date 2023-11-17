package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.PedidoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente.ClientesListarActivity;

public class PedidoActivity extends AppCompatActivity {

    private PedidoOperations pedidoOps;
    private Cliente clienteSeleccionado;

    private static final int SELECCIONAR_CLIENTE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        pedidoOps = new PedidoOperations(this);

        Button btnSeleccionarCliente = findViewById(R.id.btnSeleccionarCliente);
        Button btnGuardarPedido = findViewById(R.id.btnInsertarPedido);
        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesPedido);

        btnSeleccionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarCliente();
            }
        });

        btnGuardarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPedido();
            }
        });

        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void agregarPedido() {
        EditText editDescripcion = findViewById(R.id.editDescripcionPedido);
        EditText editFecha = findViewById(R.id.editFechaPedido);
        EditText editCantidad = findViewById(R.id.editCantidadPedido);

        String descripcion = editDescripcion.getText().toString().trim();
        String fecha = editFecha.getText().toString().trim();
        String cantidadString = editCantidad.getText().toString().trim();

        // Validar que la descripción, fecha y cantidad no estén vacías
        if (descripcion.isEmpty() || fecha.isEmpty() || cantidadString.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadString);

        // Verificar que se haya seleccionado un cliente
        if (clienteSeleccionado != null) {

            pedidoOps.open();
            Pedido pedido = new Pedido(descripcion, fecha, cantidad, clienteSeleccionado.getClienteID());
            long resultado = pedidoOps.agregarPedido(pedido);
            pedidoOps.close();

            if (resultado != -1) {
                Toast.makeText(this, "Pedido agregado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al agregar el pedido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Seleccione un cliente antes de agregar el pedido", Toast.LENGTH_SHORT).show();
        }
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
                // Actualizar la interfaz o realizar acciones según sea necesario con el cliente seleccionado
            }
        }
    });

    private void seleccionarCliente() {
        // Lanzar la actividad para listar los clientes
        Intent intent = new Intent(this, ClientesListarActivity.class);
        intent.putExtra("DESDE_PEDIDO", true);
        // Pasar el intent al launcher
        launcher.launch (intent);
    }

}
