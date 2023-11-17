package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

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
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ProductoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductoRenderizarEliminarActivity extends AppCompatActivity {

    private ProductoOperations productoOps;
    private Producto producto;

    private static final int ELIMINAR_PRODUCTO_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderizar_eliminar_producto);

        productoOps = new ProductoOperations(this);
        producto = new Producto(); // Inicializar la variable producto

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int productoId = extras.getInt("PRODUCTO_ID");
            cargarProducto(productoId);
        }

        setupButtons();
    }

    private void cargarProducto(int productoId) {
        productoOps.open();
        Cursor cursor = productoOps.obtenerProductoPorID(productoId);

        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los índices de las columnas
            int indexNombre = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_DESCRIPCION);
            int indexPrecio = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_VALOR);

            // Obtener los valores de las columnas
            String nombre = cursor.getString(indexNombre);
            double precio = cursor.getDouble(indexPrecio);

            // Inicializar la variable producto con los datos del cursor
            producto = new Producto(nombre, precio);
            producto.setProductoID(productoId);

            // Mostrar los detalles del producto en la interfaz
            TextView txtNombre = findViewById(R.id.txtDescripcionRenderizarProducto);
            TextView txtPrecio = findViewById(R.id.txtValorRenderizarProducto);

            txtNombre.setText("Nombre: " + nombre);
            txtPrecio.setText("Precio: " + String.valueOf(precio));

            // Cerrar el cursor
            cursor.close();
        }

        productoOps.close();
    }

    private void setupButtons() {
        Button btnEliminarProducto = findViewById(R.id.btnEliminarProductoRenderizar);
        Button btnVolverListaEliminarProductos = findViewById(R.id.btnVolverListaEliminarProductos);

        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto();
            }
        });

        btnVolverListaEliminarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Crear un launcher para registrar el callback
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // Verificar el código de resultado
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Confirmación de eliminación
                productoOps.open();

                // Verificar el valor del producto y su ID
                Log.d("Producto", "Producto: " + producto);
                Log.d("Producto", "ID: " + producto.getProductoID());

                // Eliminar el producto
                if (producto.getProductoID() > 0) productoOps.eliminarProducto(producto.getProductoID());
                productoOps.close();

                // Crear un intent para volver a ProductoOperacionesActivity
                Intent intent = new Intent(ProductoRenderizarEliminarActivity.this, ProductoOperacionesActivity.class);

                // Iniciar la actividad
                startActivity(intent);

                // Cerrar la actividad actual
                finish();
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                // Cancelar eliminación
                // No hacemos nada aquí, ya que el usuario canceló la eliminación
            }
        }
    });

    private void eliminarProducto() {
        // Verificar que producto no sea nulo antes de intentar eliminar
        if (producto != null) {
            // Mostrar la actividad de confirmación
            Intent intent = new Intent(this, ProductoEliminarActivity.class);
            // Pasar el intent al launcher
            launcher.launch(intent);
        } else {
            Log.e("ProductoRenderizarEliminarActivity", "Intento de eliminar un producto nulo");
            // Manejar el error según sea necesario
        }
    }
}
