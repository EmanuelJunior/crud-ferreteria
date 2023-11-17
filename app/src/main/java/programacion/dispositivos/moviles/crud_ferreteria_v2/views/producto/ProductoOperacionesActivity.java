package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.MainActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura.FacturaOperacionesActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura.FacturaRenderizarEliminarActivity;

public class ProductoOperacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_producto);

        Button btnInsertarProducto = findViewById(R.id.btnInsertarProducto);
        Button btnMostrarProductos = findViewById(R.id.btnMostrarProductos);
        Button btnActualizarProductos = findViewById(R.id.btnActualizarProducto);
        Button btnEliminarProductos = findViewById(R.id.btnEliminarProducto);
        Button btnVolverMenuPrincipal = findViewById(R.id.btnVolverMenuDesdeProductos);

        btnInsertarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInsertarProducto();
            }
        });

        btnMostrarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMostrarProductos();
            }
        });

        btnActualizarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActualizarProductos();
            }
        });

        btnEliminarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEliminarProductos();
            }
        });

        btnVolverMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverMenuPrincipal();
            }
        });
    }

    private void abrirInsertarProducto() {
        Intent intent = new Intent(this, ProductoActivity.class);
        startActivity(intent);
    }

    private void abrirMostrarProductos() {
        Intent intent = new Intent(this, ProductosListarActivity.class);
        startActivity(intent);
    }

    private void abrirActualizarProductos() {
        Intent intent = new Intent(this, ProductosListarActualizarActivity.class);
        startActivity(intent);
    }

    private void abrirEliminarProductos() {
        Intent intent = new Intent(this, ProductosListarEliminarActivity.class);
        startActivity(intent);
    }

    private void volverMenuPrincipal() {
        // Crear un intent para volver a PedidoOperacionesActivity
        Intent intent = new Intent(ProductoOperacionesActivity.this, MainActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
