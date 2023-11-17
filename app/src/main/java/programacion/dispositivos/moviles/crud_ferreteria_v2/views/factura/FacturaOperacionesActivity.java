package programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.MainActivity;
import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidoOperacionesActivity;

public class FacturaOperacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_factura);

        Button btnInsertarFactura = findViewById(R.id.btnInsertarFactura);
        Button btnMostrarFacturas = findViewById(R.id.btnMostrarFacturas);
        Button btnActualizarFacturas = findViewById(R.id.btnActualizarFactura);
        Button btnEliminarFacturas = findViewById(R.id.btnEliminarFactura);
        Button btnVolverMenuPrincipal = findViewById(R.id.btnVolverMenuDesdeFacturas);

        btnInsertarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInsertarFactura();
            }
        });

        btnMostrarFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMostrarFacturas();
            }
        });

        btnActualizarFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActualizarFacturas();
            }
        });

        btnEliminarFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEliminarFacturas();
            }
        });

        btnVolverMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverMenuPrincipal();
            }
        });
    }

    private void abrirInsertarFactura() {
        Intent intent = new Intent(this, FacturaActivity.class);
        startActivity(intent);
    }

    private void abrirMostrarFacturas() {
        Intent intent = new Intent(this, FacturasListarActivity.class);
        startActivity(intent);
    }

    private void abrirActualizarFacturas() {
        Intent intent = new Intent(this, FacturasListarActualizarActivity.class);
        startActivity(intent);
    }

    private void abrirEliminarFacturas() {
        Intent intent = new Intent(this, FacturasListarEliminarActivity.class);
        startActivity(intent);
    }

    private void volverMenuPrincipal() {
        // Crear un intent para volver a PedidoOperacionesActivity
        Intent intent = new Intent(FacturaOperacionesActivity.this, MainActivity.class);

        // Iniciar la actividad
        startActivity(intent);

        // Cerrar la actividad actual
        finish();
    }
}
