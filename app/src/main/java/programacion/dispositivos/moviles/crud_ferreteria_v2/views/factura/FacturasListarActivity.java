package programacion.dispositivos.moviles.crud_ferreteria_v2.views.factura;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import programacion.dispositivos.moviles.crud_ferreteria_v2.R;
import programacion.dispositivos.moviles.crud_ferreteria_v2.adapters.FacturaListAdapter;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations.FacturaOperations;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;

public class FacturasListarActivity extends AppCompatActivity {

    private FacturaOperations facturaOps;
    private List<Factura> facturaList;
    private FacturaListAdapter facturaAdapter;
    private ListView listaFacturas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_facturas);

        facturaOps = new FacturaOperations(this);
        facturaList = new ArrayList<>();

        Button btnVolverOperaciones = findViewById(R.id.btnVolverOperacionesDesdeListaFacturas);
        btnVolverOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaFacturas = findViewById(R.id.listaFacturas);

        cargarFacturas();
    }

    private void cargarFacturas() {
        facturaOps.open();
        Cursor cursor = facturaOps.obtenerTodasFacturas();

        while (cursor != null && cursor.moveToNext()) {
            int facturaIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_ID);
            int fechaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_FECHA);
            int numeroIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_NUMERO);
            int totalIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FACTURA_TOTAL);
            int pedidoIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PEDIDO_ID);

            if (facturaIdIndex != -1 && fechaIndex != -1 && totalIndex != -1 && pedidoIdIndex != -1 && numeroIndex != -1) {
                int facturaId = cursor.getInt(facturaIdIndex);
                String fecha = cursor.getString(fechaIndex);
                double total = cursor.getDouble(totalIndex);
                String numero = cursor.getString(numeroIndex);
                int pedidoId = cursor.getInt(pedidoIdIndex);

                Factura factura = new Factura(numero, fecha, total, pedidoId);
                factura.setFacturaID(facturaId);
                facturaList.add(factura);
            } else {
                Log.e("CursorError", "Al menos una columna no existe en el cursor");
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        facturaAdapter = new FacturaListAdapter(this, facturaList);
        listaFacturas.setAdapter(facturaAdapter);

        facturaOps.close();
    }
}
