package programacion.dispositivos.moviles.crud_ferreteria_v2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ferreteria.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Cliente
    public static final String TABLE_CLIENTE = "cliente";
    public static final String COLUMN_CLIENTE_ID = "cliente_id";
    public static final String COLUMN_CLIENTE_CEDULA = "cedula";
    public static final String COLUMN_CLIENTE_NOMBRE = "nombre";
    public static final String COLUMN_CLIENTE_DIRECCION = "direccion";
    public static final String COLUMN_CLIENTE_TELEFONO = "telefono";

    // Tabla Pedido
    public static final String TABLE_PEDIDO = "pedido";
    public static final String COLUMN_PEDIDO_ID = "pedido_id";
    public static final String COLUMN_PEDIDO_DESCRIPCION = "descripcion";
    public static final String COLUMN_PEDIDO_FECHA = "fecha";
    public static final String COLUMN_PEDIDO_CANTIDAD = "cantidad";

    // Tabla Producto
    public static final String TABLE_PRODUCTO = "producto";
    public static final String COLUMN_PRODUCTO_ID = "producto_id";
    public static final String COLUMN_PRODUCTO_DESCRIPCION = "descripcion";
    public static final String COLUMN_PRODUCTO_VALOR = "valor";

    // Tabla Factura
    public static final String TABLE_FACTURA = "factura";
    public static final String COLUMN_FACTURA_ID = "factura_id";
    public static final String COLUMN_FACTURA_NUMERO = "numero";
    public static final String COLUMN_FACTURA_FECHA = "fecha";
    public static final String COLUMN_FACTURA_TOTAL = "total";

    // Sentencias SQL para crear las tablas
    private static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + "("
            + COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLIENTE_CEDULA + " TEXT,"
            + COLUMN_CLIENTE_NOMBRE + " TEXT,"
            + COLUMN_CLIENTE_DIRECCION + " TEXT,"
            + COLUMN_CLIENTE_TELEFONO + " TEXT"
            + ")";

    private static final String CREATE_TABLE_PEDIDO = "CREATE TABLE " + TABLE_PEDIDO + "("
            + COLUMN_PEDIDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PEDIDO_DESCRIPCION + " TEXT,"
            + COLUMN_PEDIDO_FECHA + " TEXT,"
            + COLUMN_PEDIDO_CANTIDAD + " INTEGER,"
            + COLUMN_CLIENTE_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_CLIENTE_ID + ") REFERENCES " + TABLE_CLIENTE + "(" + COLUMN_CLIENTE_ID + ")"
            + ")";

    private static final String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + TABLE_PRODUCTO + "("
            + COLUMN_PRODUCTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCTO_DESCRIPCION + " TEXT,"
            + COLUMN_PRODUCTO_VALOR + " REAL"
            + ")";

    private static final String CREATE_TABLE_FACTURA = "CREATE TABLE " + TABLE_FACTURA + "("
            + COLUMN_FACTURA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FACTURA_NUMERO + " TEXT,"
            + COLUMN_FACTURA_FECHA + " TEXT,"
            + COLUMN_FACTURA_TOTAL + " REAL,"
            + COLUMN_PEDIDO_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_PEDIDO_ID + ") REFERENCES " + TABLE_PEDIDO + "(" + COLUMN_PEDIDO_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTE);
        db.execSQL(CREATE_TABLE_PEDIDO);
        db.execSQL(CREATE_TABLE_PRODUCTO);
        db.execSQL(CREATE_TABLE_FACTURA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes realizar acciones si la estructura de la base de datos cambia en futuras versiones.
    }
}
