package programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClienteActivity extends AppCompatActivity {

    private ClienteOperations clienteOps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        clienteOps = new ClienteOperations(this);

        Button btnGuardarCliente = findViewById(R.id.btnInsertar);
        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperaciones);

        btnGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCliente();
            }
        });

        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void agregarCliente() {
        EditText editCedula = findViewById(R.id.editCedula);
        EditText editNombre = findViewById(R.id.editNombre);
        EditText editDireccion = findViewById(R.id.editDireccion);
        EditText editTelefono = findViewById(R.id.editTelefono);

        String cedula = editCedula.getText().toString().trim();
        String nombre = editNombre.getText().toString().trim();
        String direccion = editDireccion.getText().toString().trim();
        String telefono = editTelefono.getText().toString().trim();

        if (!cedula.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()) {
            clienteOps.open();
            Cliente cliente = new Cliente(cedula, nombre, direccion, telefono);
            long resultado = clienteOps.agregarCliente(cliente);
            clienteOps.close();

            if (resultado != -1) {
                Toast.makeText(ClienteActivity.this, "Cliente agregado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ClienteActivity.this, "Error al agregar cliente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ClienteActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
