package com.example.quizzessql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int i=1;
    int resultado=0;
    TextView conteoTexto;
    TextView enunciadoTexto;
    TextView preguntaTexto;
    RadioButton opcion1;
    RadioButton opcion2;
    RadioButton opcion3;
    RadioButton opcion4;
    Button enviarBoton;
    RadioGroup grupo;
    BaseDatosOpenHelper admin = new BaseDatosOpenHelper(this,"preguntas",null,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrar();
        conteoTexto = (TextView)findViewById(R.id.conteotxt);
        enunciadoTexto = (TextView)findViewById(R.id.enunciadotxt);
        preguntaTexto = (TextView)findViewById(R.id.preguntatxt);
        opcion1 = (RadioButton)findViewById(R.id.opcion1);
        opcion2 = (RadioButton)findViewById(R.id.opcion2);
        opcion3 = (RadioButton)findViewById(R.id.opcion3);
        opcion4 = (RadioButton)findViewById(R.id.opcion4);
        enviarBoton = (Button)findViewById(R.id.enviarBoton);
        grupo = (RadioGroup)findViewById(R.id.radioGroup);

        //Obtaining the text for the button on screen 1 from the file strings (multi-language function)
        enviarBoton.setText(getResources().getString(R.string.boton1));

        //Call the button's setOnClickListener method.
        enviarBoton.setOnClickListener(this);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros!=null) {
            i = getIntent().getExtras().getInt("numPregunta");
        }
        //called the consulta function to update the data displayed on the screen
        consulta(i);

    }

    //Method for the intent to launch to the second screen
    public void startPantalla2()
    {
        Intent intent =new Intent(MainActivity.this,Pantalla2.class);
        intent.putExtra("resultado",resultado); //delivery the data if the answer is correct or not to the second screen
        intent.putExtra("numPregunta",i); //delivery the number of the current question
        startActivity(intent);
    }

    //Creation of the refrescar function to update the data displayed on the screen

    public void registrar()
    {
        //BaseDatosOpenHelper admin = new BaseDatosOpenHelper(this,"preguntas",null,1);

        SQLiteDatabase preguntas=admin.getWritableDatabase();


        ContentValues registro = new ContentValues();

        registro.put("numPregunta",1);
        registro.put("enunciado","Select the correct result for the next operation");
        registro.put("pregunta","25*2-4 =");
        registro.put("opcion1","46");
        registro.put("opcion2","-50");
        registro.put("opcion3","42");
        registro.put("opcion4","-46");

        preguntas.insert("preguntas",null,registro);

        registro.put("numPregunta",2);
        registro.put("enunciado","What is the value of y to solve the following equation");
        registro.put("pregunta","10y = 70");
        registro.put("opcion1","10");
        registro.put("opcion2","7");
        registro.put("opcion3","1.4");
        registro.put("opcion4","0.7");

        preguntas.insert("preguntas",null,registro);

        registro.put("numPregunta",3);
        registro.put("enunciado","Select the correct result");
        registro.put("pregunta","2^8 =");
        registro.put("opcion1","1024");
        registro.put("opcion2","128");
        registro.put("opcion3","512");
        registro.put("opcion4","256");

        preguntas.insert("preguntas",null,registro);

        preguntas.close();
    }

public void consulta(int i)
{
    //BaseDatosOpenHelper admin = new BaseDatosOpenHelper(this,"preguntas",null,1);

    SQLiteDatabase preguntas = admin.getReadableDatabase();

    Cursor fila = preguntas.rawQuery("select enunciado, pregunta, opcion1, opcion2, opcion3, opcion4 from preguntas where numPregunta= "+i,null);

    if(fila.moveToFirst()){
        conteoTexto.setText(""+i+"/3");
        enunciadoTexto.setText(fila.getString(0));
        preguntaTexto.setText(fila.getString(1));
        opcion1.setText(fila.getString(2));
        opcion2.setText(fila.getString(3));
        opcion3.setText(fila.getString(4));
        opcion4.setText(fila.getString(5));

    }
    else
    {
        enunciadoTexto.setText("No se encuentra pregunta");
    }

    preguntas.close();


}

    @Override
    public void onClick(View view) {
        //Creation of the toast to be displayed in case no option is selected
        Toast mensaje=Toast.makeText(getApplicationContext(),getResources().getString(R.string.aviso),Toast.LENGTH_LONG);
        mensaje.setGravity(Gravity.CENTER,0,0);

        //Review if the send button has been pressed
        if(view.getId()==R.id.enviarBoton)
        {
            //Review if a response option has been chosen
            if(grupo.getCheckedRadioButtonId()!=-1) {
                //Verification if the correct answer has been chosen for each question
                switch (i) {
                    case 1:
                        if (opcion1.isChecked()) {
                            i++;
                            resultado = 1;
                        } else {
                            resultado = 0;
                        }
                        break;
                    case 2:
                        if (opcion2.isChecked()) {
                            i++;
                            resultado = 1;
                        } else {
                            resultado = 0;
                        }
                        break;
                    case 3:
                        if (opcion4.isChecked()) {
                            i++;
                            resultado = 1;

                        } else {
                            resultado = 0;
                        }
                        break;
                    default:
                        conteoTexto.setText("Juego Terminado");
                        break;
                }
                //Call to the method that contains the intent to launch the second screen
                startPantalla2();
            }
            //If no option has been selected, then show the toast
            else
            {
                mensaje.show();
            }

        }
    }
}
