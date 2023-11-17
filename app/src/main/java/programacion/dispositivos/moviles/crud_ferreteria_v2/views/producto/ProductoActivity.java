package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.ProductoOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductoActivity extends AppCompatActivity {

    private ProductoOperations productoOps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        productoOps = new ProductoOperations(this);

        Button btnGuardarProducto = findViewById(R.id.btnInsertarProducto);
        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesProducto);

        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });

        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void agregarProducto() {
        EditText editNombreProducto = findViewById(R.id.editDescripcionProducto);
        EditText editPrecioProducto = findViewById(R.id.editValorProducto);

        String nombre = editNombreProducto.getText().toString().trim();
        String precioStr = editPrecioProducto.getText().toString().trim();

        if (!nombre.isEmpty() && !precioStr.isEmpty()) {
            double precio = Double.parseDouble(precioStr);

            productoOps.open();
            Producto producto = new Producto(nombre, precio);
            long resultado = productoOps.agregarProducto(producto);
            productoOps.close();

            if (resultado != -1) {
                Toast.makeText(ProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ProductoActivity.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ProductoActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
