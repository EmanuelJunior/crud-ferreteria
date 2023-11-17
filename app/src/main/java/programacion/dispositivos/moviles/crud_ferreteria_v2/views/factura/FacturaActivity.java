package programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.FacturaOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;
import programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido.PedidosListarActivity;

public class FacturaActivity extends AppCompatActivity {

    private FacturaOperations facturaOps;
    private Pedido pedidoSeleccionado;

    private static final int SELECCIONAR_PEDIDO_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        facturaOps = new FacturaOperations(this);

        Button btnSeleccionarPedido = findViewById(R.id.btnSeleccionarPedido);
        Button btnGuardarFactura = findViewById(R.id.btnInsertarFactura);
        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesFactura);

        btnSeleccionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarPedido();
            }
        });

        btnGuardarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarFactura();
            }
        });

        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void agregarFactura() {
        EditText editNumero = findViewById(R.id.editNumeroFactura);
        EditText editFecha = findViewById(R.id.editFechaFactura);
        EditText editTotal = findViewById(R.id.editTotalFactura);

        String numero = editNumero.getText().toString().trim();
        String fecha = editFecha.getText().toString().trim();
        String totalString = editTotal.getText().toString().trim();

        // Validar que el número, fecha y total no estén vacíos
        if (numero.isEmpty() || fecha.isEmpty() || totalString.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = Double.parseDouble(totalString);

        // Verificar que se haya seleccionado un pedido
        if (pedidoSeleccionado != null) {

            facturaOps.open();
            Factura factura = new Factura(numero, fecha, total, pedidoSeleccionado.getPedidoID());
            long resultado = facturaOps.agregarFactura(factura);
            facturaOps.close();

            if (resultado != -1) {
                Toast.makeText(this, "Factura agregada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al agregar la factura", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Seleccione un pedido antes de agregar la factura", Toast.LENGTH_SHORT).show();
        }
    }

    // Crear un launcher para registrar el callback
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // Verificar el código de resultado
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Obtener el intent de la actividad
                Intent data = result.getData();
                // Obtener el pedido seleccionado del intent
                pedidoSeleccionado = data.getParcelableExtra("PEDIDO_SELECCIONADO");

                // Mostrar los detalles del pedido en los TextView correspondientes
                TextView textViewDescripcionPedido = findViewById(R.id.textViewDescripcionPedido);
                TextView textViewFechaPedido = findViewById(R.id.textViewFechaPedido);
                TextView textViewCantidadPedido = findViewById(R.id.textViewCantidadPedido);

                textViewDescripcionPedido.setText("Descripción del Pedido: " + pedidoSeleccionado.getDescripcion());
                textViewFechaPedido.setText("Fecha del Pedido: " + pedidoSeleccionado.getFecha());
                textViewCantidadPedido.setText("Cantidad del Pedido: " + pedidoSeleccionado.getCantidad());
                // Actualizar la interfaz o realizar acciones según sea necesario con el pedido seleccionado
            }
        }
    });

    private void seleccionarPedido() {
        // Lanzar la actividad para listar los pedidos
        Intent intent = new Intent(this, PedidosListarActivity.class);
        intent.putExtra("DESDE_FACTURA", true);
        // Pasar el intent al launcher
        launcher.launch(intent);
    }
}
