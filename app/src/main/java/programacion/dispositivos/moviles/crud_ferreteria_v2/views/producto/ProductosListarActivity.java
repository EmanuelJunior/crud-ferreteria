package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.adapters.ProductoListAdapter;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ProductoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductosListarActivity extends AppCompatActivity {

    private ProductoOperations productoOps;
    private List<Producto> productoList;
    private ProductoListAdapter productoAdapter;
    private ListView listaProductos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        productoOps = new ProductoOperations(this);
        productoList = new ArrayList<>();

        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesDesdeListaProductos);
        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaProductos = findViewById(R.id.listaProductos);

        cargarProductos();
    }

    private void cargarProductos() {

        productoOps.open();
        Cursor cursor = productoOps.obtenerTodosProductos();

        while (cursor != null && cursor.moveToNext()) {
            int productoIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_ID);
            int nombreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_DESCRIPCION);
            int precioIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCTO_VALOR);

            // Verificar que todas las columnas existan
            if (productoIdIndex != -1 && nombreIndex != -1 && precioIndex != -1) {
                int productoId = cursor.getInt(productoIdIndex);
                String nombre = cursor.getString(nombreIndex);
                double precio = cursor.getDouble(precioIndex);

                Producto producto = new Producto(nombre, precio);
                producto.setProductoID(productoId);
                productoList.add(producto);
            } else {
                // Manejar la situación donde una columna no existe en el cursor
                Log.e("CursorError", "Al menos una columna no existe en el cursor");
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        productoAdapter = new ProductoListAdapter(this, productoList);
        listaProductos.setAdapter(productoAdapter);

        productoOps.close();
    }
}
