package programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClienteRenderizarEliminarActivity extends AppCompatActivity {

    private ClienteOperations clienteOps;
    private Cliente cliente;

    private static final int ELIMINAR_CLIENTE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderizar_eliminar_cliente);

        clienteOps = new ClienteOperations(this);
        cliente = new Cliente(); // Inicializar la variable cliente

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int clienteId = extras.getInt("CLIENTE_ID");
            cargarCliente(clienteId);
        }

        setupButtons();
    }

    private void cargarCliente(int clienteId) {
        clienteOps.open();
        Cursor cursor = clienteOps.obtenerClientePorID(clienteId);

        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los índices de las columnas
            int indexCedula = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_CEDULA);
            int indexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_NOMBRE);
            int indexDireccion = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_DIRECCION);
            int indexTelefono = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLIENTE_TELEFONO);

            // Obtener los valores de las columnas
            String cedula = cursor.getString(indexCedula);
            String nombre = cursor.getString(indexNombre);
            String direccion = cursor.getString(indexDireccion);
            String telefono = cursor.getString(indexTelefono);

            // Inicializar la variable cliente con los datos del cursor
            cliente = new Cliente(cedula, nombre, direccion, telefono);
            cliente.setClienteID(clienteId);

            // Mostrar los detalles del cliente en la interfaz
            TextView txtCedula = findViewById(R.id.txtCedulaRenderizar);
            TextView txtNombre = findViewById(R.id.txtNombreRenderizar);
            TextView txtDireccion = findViewById(R.id.txtDireccionRenderizar);
            TextView txtTelefono = findViewById(R.id.txtTelefonoRenderizar);

            txtCedula.setText("Cédula: " + cedula);
            txtNombre.setText("Nombre: " + nombre);
            txtDireccion.setText("Dirección: " + direccion);
            txtTelefono.setText("Teléfono: " + telefono);

            // Cerrar el cursor
            cursor.close();
        }

        clienteOps.close();
    }

    private void setupButtons() {
        Button btnEliminarCliente = findViewById(R.id.btnEliminarClienteRenderizar);
        Button btnVolverListaEliminarClientes = findViewById(R.id.btnVolverListaEliminarClientes);

        btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCliente();
            }
        });

        btnVolverListaEliminarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Crear un launcher para registrar el callback
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult (new ActivityResultContracts.StartActivityForResult (), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult (ActivityResult result) {
            // Verificar el código de resultado
            if (result.getResultCode () == Activity.RESULT_OK) {
                // Confirmación de eliminación
                clienteOps.open();

                // Verificar el valor del cliente y su ID
                Log.d("Cliente", "Cliente: " + cliente);
                Log.d("Cliente", "ID: " + cliente.getClienteID());

                // Eliminar el cliente
                if ( cliente.getClienteID() > 0 ) clienteOps.eliminarCliente(cliente.getClienteID());
                clienteOps.close();

                // Crear un intent para volver a ClienteOperacionesActivity
                Intent intent = new Intent(ClienteRenderizarEliminarActivity.this, ClienteOperacionesActivity.class);

                // Iniciar la actividad
                startActivity(intent);

                // Cerrar la actividad actual
                finish();
            } else if (result.getResultCode () == Activity.RESULT_CANCELED) {
                // Cancelar eliminación
                // No hacemos nada aquí, ya que el usuario canceló la eliminación
            }
        }
    });

    private void eliminarCliente() {
        // Verificar que cliente no sea nulo antes de intentar eliminar

        if (cliente != null) {
            // Mostrar la actividad de confirmación
            Intent intent = new Intent(this, ClienteEliminarActivity.class);
            // Pasar el intent al launcher
            launcher.launch(intent);
        } else {
            Log.e("ClienteRenderizarEliminarActivity", "Intento de eliminar un cliente nulo");
            // Manejar el error según sea necesario
        }
    }

}
