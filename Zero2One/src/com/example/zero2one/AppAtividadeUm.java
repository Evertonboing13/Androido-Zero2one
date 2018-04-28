package com.example.zero2one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AppAtividadeUm extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public void abrirAtividadeDois() {
		Intent intent = new Intent(AppAtividadeUm.this, AppAtividadeDois.class);
		Bundle b = new Bundle();
		b.putInt("soma", 400); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		startActivity(intent);
	}
}
