package com.example.v51_frutiapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2Lvl4 extends AppCompatActivity {

    // Declaramos los Objetos que usaremos a la hora de pasar los adtos
    private TextView tvName, tvScore;
    private ImageView ivNum1, ivNum2, ivChances, ivOperador; // Para el Nivel 4 se Agrego el ImageView del Operador
    private EditText etAnswer;
    private MediaPlayer mpBG, mpGreat, mpBad;

    // Declaramos las variables que vamos a Utilizar
    int iScore, iRndm1, iRndm2, iRes, iChances = 3; // Las oportunidades se inicializan en tres
    String sPlayer, sScore, sChances;

    // Declaramos el Vector que tendrá las imagenes que usaremos
    String sNum [] = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_lvl4);

        // Indicamos al Usuario en que nivel esta
        Toast.makeText(this, "Nivel 4 - Sumas y Restas", Toast.LENGTH_SHORT).show();

        // Creamos las Relaciones
        tvName = (TextView)findViewById(R.id.txtPlayer);
        tvScore = (TextView)findViewById(R.id.txtScore);
        ivNum1 = (ImageView)findViewById(R.id.ivNum1);
        ivNum2 = (ImageView)findViewById(R.id.ivNum2);
        ivChances = (ImageView)findViewById(R.id.ivChances);
        ivOperador = (ImageView)findViewById(R.id.ivOperator); // Agregamos este Casting para el Nivel 4
        etAnswer = (EditText)findViewById(R.id.txtAnswer);

        // Recuperamos los Datos del Activity Anterior
        sPlayer = getIntent().getStringExtra("player");

        // Lo colocamos dentro del TextView
        tvName.setText("Jugador: " +sPlayer);

        // Estas tres líneas de código son nuevas que se agregarón del Lvl1 al Lvl2
        // Recuperamos los datos del Record
        sScore = getIntent().getStringExtra("score");

        // La Convertimos a Entero
        iScore = Integer.parseInt(sScore);

        // Lo Ponemos dentro del TextView:
        tvScore.setText("Puntaje: " + iScore);

        // Recuperamos las Oportidades en las siguientes  líneas de código (Difiere del lvl1)
        sChances = getIntent().getStringExtra("chances");

        // Convertimos el Dato a Entero
        iChances = Integer.parseInt(sChances);

        /* Esta es otra forma de declararlo unos ahorramos una línea de código
        iChances = Integer.parseInt(sChances = getIntent().getStringExtra("chances"));
         */

        // Mediante Estructura Condicional mostramos la cantidad de vidas que quedan
        // En esta ocación lo haremos con un IF el anterior fue con un SWITCH
        if (iChances == 3) {
            ivChances.setImageResource(R.drawable.tresvidas);
        } else if (iChances == 2){
            Toast.makeText(this,"¡" + sPlayer + " te quedan 2 Oportunidades!",Toast.LENGTH_SHORT).show();
            ivChances.setImageResource(R.drawable.dosvidas);
        } else if (iChances == 1) {
            Toast.makeText(this, "¡" + sPlayer + " te queda 1 Oportunidad!", Toast.LENGTH_SHORT).show();
            ivChances.setImageResource(R.drawable.unavida);
        } else {
            Toast.makeText(this, "Lo sentimos " + sPlayer + " ya no tienes Oportunidades",Toast.LENGTH_SHORT).show();
        }

        // Agregamos los iconos al Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher1);

        // Agregamos el Audio
        mpBG = MediaPlayer.create(this, R.raw.goats);
        mpBG.start();
        mpBG.setLooping(true);

        // Inicializamos el Metodo que hemos creado
        numRandom();

        // Indicamos con Audio si la respuesta es correcta o incorrecta
        mpGreat = MediaPlayer.create(this,R.raw.wonderful); // Correcta
        mpBad = MediaPlayer.create(this, R.raw.bad); // Incorrecta
    }

    // Creamos los Métodos
    // Este Método lo Modificamos del Nivel 1 para Adaptarlo al nivel 2
    public void numRandom (){
        // Creamos las Estructura Condicional
        if (iScore <= 39) {
            iRndm1 = (int) (Math.random() * 10);
            iRndm2 = (int) (Math.random() * 10);

            // Estructura Condicional para Alternar Operaciones
            if (iRndm1 >= 0 && iRndm1 <= 4) {
                // Agregamos la Operación de Suma
                iRes = iRndm1 + iRndm2;

                // Fijamos la Operación
                ivOperador.setImageResource(R.drawable.adicion);

            } else {
                // Agregamos la Operación de Resta
                iRes = iRndm1 - iRndm2;

                // Fijamos la Operación
                ivOperador.setImageResource(R.drawable.resta);

            }

            // Estructura Condicional para no mostras resultados negativos en las restas
            if (iRes >= 0) {
                // Ponemos una Estructura Repetitiva para la Resta
                for (int i = 0; i < sNum.length; i ++){
                    int id = getResources().getIdentifier(sNum[i], "drawable", getPackageName());

                    // Indicamos la Imagen que tendrá cada cuatdro
                    if (iRndm1 == i) {
                        ivNum1.setImageResource(id);
                    }
                    if (iRndm2 == i) {
                        ivNum2.setImageResource(id);
                    }
                }

            } else {
                // Aplicamos la Recursividad
                numRandom();
            }

        } else {
            // En caso de que sea mayor a 9 pasamos a la siguiente Activity
            Intent lvl2 = new Intent(this, MainActivity2Lvl5.class);

            // Enviamos el record y el nombre del usuario al siguiente Activity
            sScore = String.valueOf(iScore); // Cambiamos el tipop de dato de entero a cadena
            sChances = String.valueOf(iChances);

            // Pasamos los Valores
            lvl2.putExtra("player", sPlayer);
            lvl2.putExtra("score", sScore);
            lvl2.putExtra("chances", sChances);

            // Inicializamos el siguiente Activity
            startActivity(lvl2);

            // Cerramos la Presente Activity
            finish();

            // Paramos el Audio:
            mpBG.stop();

            // Destrimos el Objeto y liberamos recursos
            mpBG.release();
        }
    }

    // Método Botón Respuesta
    public void btnAnswer(View view){
        // Recuperamos la respuesta introducida por el usuario
        // Inicializamos las variables
        String sAnswer = etAnswer.getText().toString();

        // Validamos que el usuario haya escrito una respuesta mediante estructura condiciona;
        if (!sAnswer.equals("")) {
            // Convertimos la respuesta del Jugador a Entero
            int iAnswer = Integer.parseInt(sAnswer);

            // Validamos que la respuesta del Jugador sea correcta
            if (iRes == iAnswer) {
                // Indicamos con Audio que la respuesta es correcta
                mpGreat.start();

                // Mensame Motivacional (Mas Adelante por Cambiarlo por uno dinámico)
                Toast.makeText(this,"Excelente", Toast.LENGTH_SHORT).show();

                // Incrementamos el record
                iScore ++;

                // Modificamos el TextView
                tvScore.setText("Puntaje: " + iScore);

                // Limpiamos el TextView
                etAnswer.setText("");

                // Actualizamos en Base de Datos
                dbCrud();

            } else {
                // En caso de que el usuario cometa un error
                // Se lo indicamos mediante un audio
                mpBad.start();

                // Disminuimos las Oportunidades
                iChances--;

                // Actualizamos la Base de Datos
                dbCrud();

                // Mediante un Swith indicamos como se comportará el programa
                switch (iChances){
                    case 3:
                        ivChances.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        Toast.makeText(this, sPlayer + " Te quedan dos Oportunidades",Toast.LENGTH_SHORT).show();
                        ivChances.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this, sPlayer + " Te queda una Oportunidad",Toast.LENGTH_SHORT).show();
                        ivChances.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this, "¡Lo Sentimos! " + sPlayer,Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,"Ya no te quedan Oportunidades",Toast.LENGTH_SHORT).show();

                        // Mandamos al Usuario al Primer Activity
                        Intent lvlMain = new Intent(this, MainActivity.class);

                        // Cerramos el Presente Activitu
                        finish();

                        // Enviamos al Activitu Principal
                        startActivity(lvlMain);

                        // Detenemos el Audio
                        mpBG.stop();

                        // Liberamos los Recursos
                        mpBG.release();
                        break;
                    default:
                        Toast.makeText(this, "¿Qué hago?", Toast.LENGTH_SHORT).show();
                }
                // Limpiamos el EditText de la Respuesta
                etAnswer.getText().clear();
            }

        } else {
            Toast.makeText(this,"Favor de Escriir tu Respuesta", Toast.LENGTH_SHORT).show();
        }
        // Actualizamos la Operación
        numRandom();
    }

    // Método CRUD (Insertar, Consultar y Modificar dentro de la Bae de Datos)
    public void  dbCrud (){
        // Creamos el Objeto para utilizar la Clase de SQLite
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db", null, 1);

        // Usamos la Clase SQL DataBase (Lectura y Escritura)
        SQLiteDatabase db = admin.getWritableDatabase();

        // Consultamos si hubiera registros
        Cursor query = db.rawQuery("SELECT * FROM scores WHERE points = (SELECT MAX (points) FROM scores)", null);

        // Estructura Condional para corroborar que se haya encontrado registros
        if (query.moveToFirst()) { // Para concer si hubo registros
            // Guardamos los datos obtenidos en una variable
            String sDbName = query.getString(0); // El cero es porque es el Indice que tiene la Primer Columna
            String sDbScore = query.getString(1); // Dado a que es la segunda columna en la Base de Datos

            // Convertimos el Puntaje a Entero
            int iDbScore = Integer.parseInt(sDbScore);

            // Realizamos la Comparación (Verificar que el Record sea mayor para modifica e insertar la base de datos)
            if (iScore > iDbScore) {
                // Clase SQL para modicicar los resultados
                ContentValues updateDB = new ContentValues();

                // Actualizamos los valores
                updateDB.put("name", sPlayer);
                updateDB.put("points", iScore);

                // Indicamos a la Base de Datos la misma inserción
                db.update("scores", updateDB, "points=" + iDbScore, null);
            }
            // Cerramos la Base de Datos
            db.close();

        } else {
            // En caso de que no haya registros
            // Insertamos el Dato con ContentValues
            ContentValues save = new ContentValues();

            // Insertamos los Valores
            save.put("name", sPlayer);
            save.put("points", iScore);

            // Guardamos en la Base de Datos
            db.insert("scores", null, save);

            // Cerramos la Base de Datos
            db.close();
        }
    }

    // Control del Botón Back para que no se regrese al Activitu Anterior
    @Override // Lo sobreescribimos porque ya existe en la aplicación
    public void onBackPressed ()
    {
        Toast.makeText(this, "Botón Desactivado", Toast.LENGTH_SHORT).show();
    }
}
