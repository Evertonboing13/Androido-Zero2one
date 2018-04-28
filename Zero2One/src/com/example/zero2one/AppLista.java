package com.example.zero2one;

import android.app.Activity; 
import android.app.AlertDialog; 
import android.os.Bundle; 
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener; 
import android.view.*;

public class AppLista extends Activity {
	public ListView lista;
	public String[] contatos = new String[] { "Alline","Lucas","Rafael","Gabriela","Silvana","Alline","Lucas","Rafael","Gabriela","Silvana", "Alline","Lucas","Rafael","Gabriela","Silvana"};

	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.instagram); 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contatos);
		lista = (ListView) findViewById(R.id.lstcontatos); lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener(){ 


			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(lista.getSelectedItem()!=null) {
					AlertDialog.Builder dialogo = new AlertDialog.Builder(AppLista.this);
					dialogo.setTitle("Contato selecionado"); dialogo.setMessage(lista.getSelectedItem().toString()); 
					dialogo.setNeutralButton("OK", null);
					dialogo.show();
				}
			}
		}); 
	}

}