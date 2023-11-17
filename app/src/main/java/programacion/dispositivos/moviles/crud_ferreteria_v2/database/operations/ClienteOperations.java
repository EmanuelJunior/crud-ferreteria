package programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Cliente;

public class ClienteOperations extends DatabaseHelper {
    public ClienteOperations(Context context) {
        super(context);
    }

    // Métodos para operaciones CRUD específicas de Cliente

    public long agregarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENTE_CEDULA, cliente.getCedula());
        values.put(COLUMN_CLIENTE_NOMBRE, cliente.getNombre());
        values.put(COLUMN_CLIENTE_DIRECCION, cliente.getDireccion());
        values.put(COLUMN_CLIENTE_TELEFONO, cliente.getTelefono());

        // Insertar fila
        long id = db.insert(TABLE_CLIENTE, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerTodosClientes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CLIENTE, null, null, null, null, null, null);
    }

    public Cursor obtenerClientePorID(int clienteID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(clienteID)};
        return db.query(TABLE_CLIENTE, null, COLUMN_CLIENTE_ID + "=?", selectionArgs, null, null, null);
    }

    public int actualizarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENTE_CEDULA, cliente.getCedula());
        values.put(COLUMN_CLIENTE_NOMBRE, cliente.getNombre());
        values.put(COLUMN_CLIENTE_DIRECCION, cliente.getDireccion());
        values.put(COLUMN_CLIENTE_TELEFONO, cliente.getTelefono());

        // Actualizar fila
        String[] whereArgs = {String.valueOf(cliente.getClienteID())};
        return db.update(TABLE_CLIENTE, values, COLUMN_CLIENTE_ID + "=?", whereArgs);
    }

    public int eliminarCliente(int clienteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(clienteID)};
        return db.delete(TABLE_CLIENTE, COLUMN_CLIENTE_ID + "=?", whereArgs);
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
