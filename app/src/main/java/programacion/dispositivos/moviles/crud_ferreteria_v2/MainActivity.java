package programacion.dispositivos.moviles.crud_ferreteria_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ClienteOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.*;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.cliente.ClienteOperacionesActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura.FacturaActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura.FacturaOperacionesActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidoOperacionesActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto.ProductoOperacionesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClientes = findViewById(R.id.btnClientes);
        Button btnPedidos = findViewById(R.id.btnPedidos);
        Button btnProductos = findViewById(R.id.btnProductos);
        Button btnFacturas = findViewById(R.id.btnFacturas);

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirClientes();
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPedidos();
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirProductos();
            }
        });

        btnFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFacturas();
            }
        });
    }

    private void abrirClientes() {
        Intent intent = new Intent(this, ClienteOperacionesActivity.class);
        startActivity(intent);
    }

    private void abrirPedidos() {
        Intent intent = new Intent(this, PedidoOperacionesActivity.class);
        startActivity(intent);
    }

    private void abrirProductos() {
        Intent intent = new Intent(this, ProductoOperacionesActivity.class);
        startActivity(intent);
    }

    private void abrirFacturas() {
        Intent intent = new Intent(this, FacturaOperacionesActivity.class);
        startActivity(intent);
    }
}
