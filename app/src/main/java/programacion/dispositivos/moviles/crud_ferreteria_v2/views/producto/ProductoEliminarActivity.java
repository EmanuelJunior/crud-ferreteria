package programacion.dispositivos.moviles.crud_ferreteria_v2.views.producto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;

public class ProductoEliminarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);

        Button btnConfirmarEliminar = findViewById(R.id.btnConfirmarEliminarProducto);
        Button btnCancelarEliminar = findViewById(R.id.btnCancelarEliminarProducto);

        btnConfirmarEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para confirmar la eliminación (eliminarProducto())
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancelarEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
