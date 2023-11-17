package programacion.dispositivos.moviles.crud_ferreteria_v2.views.pedido;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;

public class PedidoEliminarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_pedido);

        Button btnConfirmarEliminar = findViewById(R.id.btnConfirmarEliminarPedido);
        Button btnCancelarEliminar = findViewById(R.id.btnCancelarEliminarPedido);

        btnConfirmarEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para confirmar la eliminación del pedido (eliminarPedido())
                // Aquí deberías llamar al método o la lógica correspondiente para eliminar el pedido
                // Por ejemplo, podrías utilizar un Intent para enviar un mensaje a la actividad que
                // maneja la lógica de eliminación del pedido.

                // Puedes usar setResult(RESULT_OK) si la eliminación fue exitosa
                // o setResult(RESULT_CANCELED) si el usuario cancela la eliminación.

                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancelarEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si el usuario cancela la eliminación
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
