package programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Factura;

public class FacturaOperations extends DatabaseHelper {

    public FacturaOperations(Context context) {
        super(context);
    }

    // Métodos para operaciones CRUD específicas de Factura

    public long agregarFactura(Factura factura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FACTURA_NUMERO, factura.getNumero());
        values.put(COLUMN_FACTURA_FECHA, factura.getFecha());
        values.put(COLUMN_FACTURA_TOTAL, factura.getTotal());
        values.put(COLUMN_PEDIDO_ID, factura.getPedidoID()); // Asociación con pedido

        // Insertar fila
        long id = db.insert(TABLE_FACTURA, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerTodasFacturas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_FACTURA, null, null, null, null, null, null);
    }

    public Cursor obtenerFacturaPorID(int facturaID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(facturaID)};
        return db.query(TABLE_FACTURA, null, COLUMN_FACTURA_ID + "=?", selectionArgs, null, null, null);
    }

    public int actualizarFactura(Factura factura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FACTURA_NUMERO, factura.getNumero());
        values.put(COLUMN_FACTURA_FECHA, factura.getFecha());
        values.put(COLUMN_FACTURA_TOTAL, factura.getTotal());
        values.put(COLUMN_PEDIDO_ID, factura.getPedidoID()); // Asociación con pedido

        // Actualizar fila
        String[] whereArgs = {String.valueOf(factura.getFacturaID())};
        return db.update(TABLE_FACTURA, values, COLUMN_FACTURA_ID + "=?", whereArgs);
    }

    public int eliminarFactura(int facturaID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(facturaID)};
        return db.delete(TABLE_FACTURA, COLUMN_FACTURA_ID + "=?", whereArgs);
    }

    // Métodos para abrir y cerrar la base de datos

    private SQLiteDatabase database;

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
