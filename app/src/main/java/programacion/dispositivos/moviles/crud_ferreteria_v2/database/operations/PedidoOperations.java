package programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Pedido;

public class PedidoOperations extends DatabaseHelper {

    public PedidoOperations(Context context) {
        super(context);
    }

    // Métodos para operaciones CRUD específicas de Pedido

    public long agregarPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PEDIDO_DESCRIPCION, pedido.getDescripcion());
        values.put(COLUMN_PEDIDO_FECHA, pedido.getFecha());
        values.put(COLUMN_PEDIDO_CANTIDAD, pedido.getCantidad());
        values.put(COLUMN_CLIENTE_ID, pedido.getClienteID()); // Asociación con cliente

        // Insertar fila
        long id = db.insert(TABLE_PEDIDO, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerTodosPedidos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PEDIDO, null, null, null, null, null, null);
    }

    public Cursor obtenerPedidoPorID(int pedidoID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(pedidoID)};
        return db.query(TABLE_PEDIDO, null, COLUMN_PEDIDO_ID + "=?", selectionArgs, null, null, null);
    }

    public int actualizarPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PEDIDO_DESCRIPCION, pedido.getDescripcion());
        values.put(COLUMN_PEDIDO_FECHA, pedido.getFecha());
        values.put(COLUMN_PEDIDO_CANTIDAD, pedido.getCantidad());
        values.put(COLUMN_CLIENTE_ID, pedido.getClienteID()); // Asociación con cliente

        // Actualizar fila
        String[] whereArgs = {String.valueOf(pedido.getPedidoID())};
        return db.update(TABLE_PEDIDO, values, COLUMN_PEDIDO_ID + "=?", whereArgs);
    }

    public int eliminarPedido(int pedidoID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(pedidoID)};
        return db.delete(TABLE_PEDIDO, COLUMN_PEDIDO_ID + "=?", whereArgs);
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