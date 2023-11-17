package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.MainActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto.ProductoOperacionesActivity;

public class PedidoOperacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_pedido);

        Button btnInsertarPedido = findViewById(R.id.btnInsertarPedido);
        Button btnMostrarPedidos = findViewById(R.id.btnMostrarPedidos);
        Button btnActualizarPedidos = findViewById(R.id.btnActualizarPedido);
        Button btnEliminarPedidos = findViewById(R.id.btnEliminarPedido);
        Button btnVolverMenuPrincipal = findViewById(R.id.btnVolverMenuDesdePedidos);

        btnInsertarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInsertarPedido();
            }
        });

        btnMostrarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMostrarPedidos();
            }
        });

        btnActualizarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActualizarPedidos();
            }
        });

        btnEliminarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEliminarPedidos();
            }
        });

        btnVolverMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverMenuPrincipal();
            }
        });
    }

    private void abrirInsertarPedido() {
        Intent intent = new Intent(this, PedidoActivity.class);
        startActivity(intent);
    }

    private void abrirMostrarPedidos() {
        Intent intent = new Intent(this, PedidosListarActivity.class);
        startActivity(intent);
    }

    private void abrirActualizarPedidos() {
        Intent intent = new Intent(this, PedidosListarActualizarActivity.class);
        startActivity(intent);
    }

    private void abrirEliminarPedidos() {
        Intent intent = new Intent(this, PedidosListarEliminarActivity.class);
        startActivity(intent);
    }

    private void volverMenuPrincipal() {
        // Crear un intent para volver a PedidoOperacionesActivity
        Intent intent = new Intent(PedidoOperacionesActivity.this, MainActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
