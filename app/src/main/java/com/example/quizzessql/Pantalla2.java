package com.example.quizzessql;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pantalla2 extends AppCompatActivity implements View.OnClickListener {

    //Declaration and creation of variables and necessary objects
    TextView texto;
    Button boton;
    int dato=0;
    int numPregunta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        //Relationship of created objects with the elements present in the layout
        texto=findViewById(R.id.resultadotxt);
        boton=findViewById(R.id.botonP2);

        //Call the button's setOnClickListener method.
        boton.setOnClickListener(this);

        //Reception of the data from the first screen
        dato= getIntent().getExtras().getInt("resultado");
        numPregunta=getIntent().getExtras().getInt("numPregunta");

        //If the answer is correct
        if(dato==1)
        {
            //If it is the last question
            if(numPregunta==4)
            {
                boton.setText(getResources().getString(R.string.boton2Final));//Get the text of the button, from the file strings (multi language function)
                boton.setBackgroundColor(Color.parseColor("#00FF00"));//set the background color of the button
                texto.setText(getResources().getString(R.string.resFinal)); //Get the message text from the strings file (multi language function)
            }
            //If it is not the last question
            else
            {
                boton.setText(getResources().getString(R.string.boton2Correcto));
                boton.setBackgroundColor(Color.parseColor("#00FF00"));
                texto.setText(getResources().getString(R.string.resCorrecto));
            }
        }
        //If the answer is not correct
        else
        {
            boton.setText(getResources().getString(R.string.boton2Incorrecto));
            boton.setBackgroundColor(Color.parseColor("#FF0000"));
            texto.setText(getResources().getString(R.string.resIncorrecto));
        }
    }


    //if the button on the second screen is pressed
    @Override
    public void onClick(View view) {
        //If it was the last question
        if(numPregunta==4)
        {
            numPregunta=1;//The number of questions is reset to zero to start again
        }
        //Call to the method that contains the intent to launch the first screen
        startInicio();
    }


    //Method for the intent to launch to the first screen
    public void startInicio()
    {
        Intent intent =new Intent(Pantalla2.this,MainActivity.class);
        intent.putExtra("numPregunta",numPregunta);
        startActivity(intent);
    }
}
