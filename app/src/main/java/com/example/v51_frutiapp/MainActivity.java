package com.example.v51_frutiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Creamos los Objetos
    private EditText et1;
    private ImageView iv1;
    private TextView tv1;
    private MediaPlayer mp1;

    // Creamos una variable aleatoria para cambiar las imagenes dinámicamente
    int iNum = (int) (Math.random() * 11); // Hacemos el casting para convertirlo a entero, lo multiplicamos por 10 para darle el rango que necesitamos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pasamos la inforación de la parte gráfica a la parte lógica
        et1 = (EditText) findViewById(R.id.txtName);
        iv1 = (ImageView) findViewById(R.id.ivPersonaje);
        tv1 = (TextView) findViewById(R.id.txtBest);

        // Ponemos las líneas de código para poner el icóno en la Activity
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher1);

        // Indicamos que el icono sea aleatorio
        int id;

        /*
        // Con IF
        // Estructura condicional para poner la imagen condicional
        if (iNum == 0 || iNum == 10) {
            id = getResources().getIdentifier("manzana", "drawable", getPackageName()); // getIdentifier nos mpermite obtener la ruta y la imagen, getPackageName obtiene la ruta en general
            iv1.setImageResource(id); // Indicamos que es lo que colocará en el image view
        } else if (iNum == 1 || iNum == 9) {
            id = getResources().getIdentifier( "naranja", "drawable", getPackageName());
            iv1.setImageResource(id);
        } else if (iNum == 2 || iNum == 8) {
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv1.setImageResource(id);
        } else if (iNum == 3 || iNum == 7) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv1.setImageResource(id);
        } else if (iNum == 4 || iNum == 6) {
            id = getResources().getIdentifier("mango", "drawable", getPackageName());
            iv1.setImageResource(id);
        } else if (iNum == 5 || iNum == 11) {
            id = getResources().getIdentifier("uva", "drawable", getPackageName());
            iv1.setImageResource(id);
        } */

        // con switch
        switch (iNum) {
            case 0:
            case 10:
                id = getResources().getIdentifier("manzana", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            case 1:
            case 9:
                id = getResources().getIdentifier("naranja", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            case 2:
            case 8:
                id = getResources().getIdentifier("fresa", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            case 3:
            case 7:
                id = getResources().getIdentifier("sandia", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            case 4:
            case 6:
                id = getResources().getIdentifier("mango", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            case 5:
            case 11:
                id = getResources().getIdentifier("uva", "drawable", getPackageName());
                iv1.setImageResource(id);
                break;
            default:
                Toast.makeText(this, "Favor de Poner un Valor Válido", Toast.LENGTH_LONG).show();
        }

        // Abrimos la Conexión con la Base de Datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db", null, 1);

        // Creamos el Objeto de la Base de Datos de Lectura y Escritura
        SQLiteDatabase db = admin.getWritableDatabase();

        // Creamos la Consulta a la Base de Datos
        Cursor query = db.rawQuery(
                "SELECT * FROM scores WHERE points = (SELECT MAX (points) FROM scores)", null);

        // En caso de que la Base de Datos este vacia y/o se ejecute por priemra vez
        if (query.moveToFirst()) {
            // Guardamos el nombre obtenido de la base de datos
            String sName = query.getString(0);

            // Obtenemos el Puntaje
            String sScore = query.getString(1);

            // Lo Guardamos dentro del TextView
            tv1.setText("Record: " + sName + " con " + sScore);

            // Cerramos la Base de Datos
            db.close();

        } else {
            // Sino ecnuentra nada cerramos la Base de Datos
            db.close();
        }

        // Agregamos la pista de Audio
        mp1 = MediaPlayer.create(this, R.raw.alphabet_song); // Guardamos la pista dentro del objeto

        // Reproducimos la pista
        mp1.start();

        // Indicamos que se debe de ciclar
        mp1.setLooping(true);
    }

    // Programación de los Métodos (Botón Jugar)
    public void btnPlay(View view) {
        // Verificamos si el usuario escribió su nombre
        String sName = et1.getText().toString();

        // Creamos la Estrutura condional
        if (!sName.equals("")) {
            // Detenemos el Audio
            mp1.stop();
            mp1.release(); // Con esto paramos el audio y liberamos recursos

            // Pasamos al Siguiente Activity
            Intent lvl1 = new Intent(this, MainActivity2Lvl1.class);

            // Pasamos el nombre del Jugador
            lvl1.putExtra("player", sName);

            // Inicializamos el Objeto para irnos al otro Activitu
            startActivity(lvl1);

            // Cerramos el Activity en el que estamos
            finish();

        } else {
            // Indicamos al Usuario que debe de escribir su nombre
            Toast.makeText(this, "Favor de Escribir su Nombre", Toast.LENGTH_SHORT);

            // Mandamos al Usuario al EditText
            et1.requestFocus();

            // Mandamos llamar al Teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et1, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    // Controlamos el Botón Back
    @Override
    public void onBackPressed () { // Debemos de respetar el nombre del Método ya que nos estamos refiriendo al Botón Back
        Toast.makeText(this, "Botón Desactivado",Toast.LENGTH_SHORT).show();
    }

}