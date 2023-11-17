package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ProductoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductoActualizarActivity extends AppCompatActivity {

    private ProductoOperations productoOps;
    private Producto producto;

    private EditText editNombre;
    private EditText editPrecio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_producto);

        productoOps = new ProductoOperations(this);

        editNombre = findViewById(R.id.editDescripcionActualizarProducto);
        editPrecio = findViewById(R.id.editValorActualizarProducto);

        Button btnGuardarActualizacion = findViewById(R.id.btnGuardarActualizacionProducto);
        Button btnCancelarActualizacion = findViewById(R.id.btnCancelarActualizacionProducto);

        // Obtener el ID del producto de la actividad anterior
        int productoId = getIntent().getIntExtra("PRODUCTO_ID", -1);

        if (productoId != -1) {
            cargarProducto(productoId);
        } else {
            Toast.makeText(this, "Error al obtener el ID del producto", Toast.LENGTH_SHORT).show();
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

    private void cargarProducto(int productoId) {
        productoOps.open();
        Cursor cursor = productoOps.obtenerProductoPorID(productoId);

        if (cursor != null && cursor.moveToFirst()) {
            int indexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_DESCRIPCION);
            int indexPrecio = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_VALOR);

            String nombre = cursor.getString(indexNombre);
            double precio = cursor.getDouble(indexPrecio);

            producto = new Producto(nombre, precio);
            producto.setProductoID(productoId);

            // Mostrar los detalles del producto en la interfaz
            editNombre.setText(nombre);
            editPrecio.setText(String.valueOf(precio));

            cursor.close();
        }

        productoOps.close();
    }

    private void guardarActualizacion() {
        // Obtener los nuevos datos del producto
        String nuevoNombre = editNombre.getText().toString().trim();
        double nuevoPrecio = Double.parseDouble(editPrecio.getText().toString().trim());

        // Actualizar los datos del producto
        producto.setDescripcion(nuevoNombre);
        producto.setValor(nuevoPrecio);

        // Actualizar en la base de datos
        productoOps.open();
        int filasActualizadas = productoOps.actualizarProducto(producto);

        if (filasActualizadas > 0) {
            Toast.makeText(this, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
        }

        productoOps.close();

        // Crear un intent para volver a ProductoOperacionesActivity
        Intent intent = new Intent(ProductoActualizarActivity.this, ProductoOperacionesActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
