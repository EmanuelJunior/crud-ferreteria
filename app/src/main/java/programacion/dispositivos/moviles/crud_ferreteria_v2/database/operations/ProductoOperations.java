package programacion.dispositivos.moviles.crud_ferreteria_v2.database.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import programacion.dispositivos.moviles.crud_ferreteria_v2.database.DatabaseHelper;
import programacion.dispositivos.moviles.crud_ferreteria_v2.models.Producto;

public class ProductoOperations extends DatabaseHelper {

    public ProductoOperations(Context context) {
        super(context);
    }

    // Métodos para operaciones CRUD específicas de Producto

    public long agregarProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRODUCTO_VALOR, producto.getValor());

        // Insertar fila
        long id = db.insert(TABLE_PRODUCTO, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerTodosProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTO, null, null, null, null, null, null);
    }

    public Cursor obtenerProductoPorID(int productoID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(productoID)};
        return db.query(TABLE_PRODUCTO, null, COLUMN_PRODUCTO_ID + "=?", selectionArgs, null, null, null);
    }

    public int actualizarProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRODUCTO_VALOR, producto.getValor());

        // Actualizar fila
        String[] whereArgs = {String.valueOf(producto.getProductoID())};
        return db.update(TABLE_PRODUCTO, values, COLUMN_PRODUCTO_ID + "=?", whereArgs);
    }

    public int eliminarProducto(int productoID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(productoID)};
        return db.delete(TABLE_PRODUCTO, COLUMN_PRODUCTO_ID + "=?", whereArgs);
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