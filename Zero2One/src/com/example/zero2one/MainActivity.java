package com.example.zero2one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText etNumberOne;
	private EditText etNumberTwo;
	private Button btSoma;
	private TextView tvResultado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etNumberOne = (EditText) findViewById(R.id.etNumberOne);
		etNumberTwo = (EditText) findViewById(R.id.etNumberTwo);
		btSoma      = (Button) findViewById(R.id.btSoma);
		tvResultado = (TextView) findViewById(R.id.tvResultado);
		tvResultado.setText("0");
		bind();
	}
	
	public void bind() {
		btSoma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				somar();
			}
		});
	}

	public void somar() {
		String s1 = etNumberOne.getText().toString();
		String s2 = etNumberTwo.getText().toString();
		int n1 = 0;
		int n2 = 0;
		try{
			n1 = Integer.parseInt(s1);
		} catch (NumberFormatException e) {
			Toast.makeText(MainActivity.this, s1 + " nao e um numero!", Toast.LENGTH_LONG).show();
			return;
		}
		
		try{
			n2 = Integer.parseInt(s2);
		} catch (NumberFormatException e) {
			Toast.makeText(MainActivity.this, s2 + " nao e um numero!", Toast.LENGTH_LONG).show();
			return;
		}
		
		int sum = n1 + n2;
		tvResultado.setText(String.valueOf(sum));
	}
}
