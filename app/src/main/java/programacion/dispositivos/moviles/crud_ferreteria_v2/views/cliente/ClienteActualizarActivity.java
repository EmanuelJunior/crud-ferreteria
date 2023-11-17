package programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClienteActualizarActivity extends AppCompatActivity {

    private ClienteOperations clienteOps;
    private Cliente cliente;

    private EditText editCedula;
    private EditText editNombre;
    private EditText editDireccion;
    private EditText editTelefono;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cliente);

        clienteOps = new ClienteOperations(this);

        editCedula = findViewById(R.id.editCedulaActualizar);
        editNombre = findViewById(R.id.editNombreActualizar);
        editDireccion = findViewById(R.id.editDireccionActualizar);
        editTelefono = findViewById(R.id.editTelefonoActualizar);

        Button btnGuardarActualizacion = findViewById(R.id.btnGuardarActualizacion);
        Button btnCancelarActualizacion = findViewById(R.id.btnCancelarActualizacion);

        // Obtener el ID del cliente de la actividad anterior
        int clienteId = getIntent().getIntExtra("CLIENTE_ID", -1);

        if (clienteId != -1) {
            cargarCliente(clienteId);
        } else {
            Toast.makeText(this, "Error al obtener el ID del cliente", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnGuardarActualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarActualizacion();
            }
        });

        btnCancelarActualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarCliente(int clienteId) {
        clienteOps.open();
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

            cliente = new Cliente(cedula, nombre, direccion, telefono);
            cliente.setClienteID(clienteId);

            // Mostrar los detalles del cliente en la interfaz
            editCedula.setText(cedula);
            editNombre.setText(nombre);
            editDireccion.setText(direccion);
            editTelefono.setText(telefono);

            cursor.close();
        }

        clienteOps.close();
    }

    private void guardarActualizacion() {
        // Obtener los nuevos datos del cliente
        String nuevaCedula = editCedula.getText().toString().trim();
        String nuevoNombre = editNombre.getText().toString().trim();
        String nuevaDireccion = editDireccion.getText().toString().trim();
        String nuevoTelefono = editTelefono.getText().toString().trim();

        // Actualizar los datos del cliente
        cliente.setCedula(nuevaCedula);
        cliente.setNombre(nuevoNombre);
        cliente.setDireccion(nuevaDireccion);
        cliente.setTelefono(nuevoTelefono);

        // Actualizar en la base de datos
        clienteOps.open();
        int filasActualizadas = clienteOps.actualizarCliente(cliente);

        if (filasActualizadas > 0) {
            Toast.makeText(this, "Cliente actualizado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el cliente", Toast.LENGTH_SHORT).show();
        }

        clienteOps.close();

        // Crear un intent para volver a ClienteOperacionesActivity
        Intent intent = new Intent(ClienteActualizarActivity.this, ClienteOperacionesActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
