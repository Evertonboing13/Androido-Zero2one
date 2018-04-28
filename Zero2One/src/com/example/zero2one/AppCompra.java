package com.example.zero2one;

import android.app.AlertDialog; 
import android.os.Bundle; 
import android.widget.*;
import android.view.*;
import android.app.*;

public class AppCompra extends Activity {
	CheckBox chkarroz,chkleite,chkcarne,chkfeijao; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); setContentView(R.layout.compras);
		chkarroz = (CheckBox) findViewById(R.id.chkarroz); 
		chkleite = (CheckBox) findViewById(R.id.chkleite);
		chkcarne = (CheckBox) findViewById(R.id.chkcarne); 
		chkfeijao = (CheckBox) findViewById(R.id.chkfeijao);
		Button bttotal = (Button) findViewById(R.id.bttotal); 
		bttotal.setOnClickListener(new View.OnClickListener(){

			public void onClick(View arg0) { 
				double total =0;
				if(chkarroz.isChecked()) 
					total += 2.69;
				if(chkleite.isChecked()) 
					total += 5.00;
				if(chkcarne.isChecked()) 
					total += 9.7;
				if(chkfeijao.isChecked())
					total += 2.30;
				
			AlertDialog.Builder dialogo = new AlertDialog.Builder(AppCompra.this);
			dialogo.setTitle("Aviso"); 
			String.valueOf(total);
			dialogo.setNeutralButton("OK", null); //adicionando o
			dialogo.show(); //mostrando o Dialog
			} });
	}
	}