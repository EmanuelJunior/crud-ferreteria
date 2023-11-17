package programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.MainActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura.FacturaOperacionesActivity;

public class ClienteOperacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_cliente);

        Button btnInsertarCliente = findViewById(R.id.btnInsertarCliente);
        Button btnMostrarClientes = findViewById(R.id.btnMostrarClientes);
        Button btnActualizarClientes = findViewById(R.id.btnActualizarCliente);
        Button btnEliminarClientes = findViewById(R.id.btnEliminarCliente);
        Button btnVolverMenuPrincipal = findViewById(R.id.btnVolverMenu);

        btnInsertarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInsertarCliente();
            }
        });

        btnMostrarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMostrarClientes();
            }
        });

        btnActualizarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActualizarClientes();
            }
        });

        btnEliminarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEliminarClientes();
            }
        });

        btnVolverMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverMenuPrincipal();
            }
        });
    }

    private void abrirInsertarCliente() {
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }

    private void abrirMostrarClientes() {
        Intent intent = new Intent(this, ClientesListarActivity.class);
        startActivity(intent);
    }

    private void abrirActualizarClientes() {
        Intent intent = new Intent(this, ClientesListarActualizarActivity.class);
        startActivity(intent);
    }

    private void abrirEliminarClientes() {
        Intent intent = new Intent(this, ClientesListarEliminarActivity.class);
        startActivity(intent);
    }

    private void volverMenuPrincipal() {
        // Crear un intent para volver a PedidoOperacionesActivity
        Intent intent = new Intent(ClienteOperacionesActivity.this, MainActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
