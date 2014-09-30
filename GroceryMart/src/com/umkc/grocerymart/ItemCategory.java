package com.umkc.grocerymart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ItemCategory extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_screen);
		Button b1 = (Button)findViewById(R.id.cat1);
		Button b2 = (Button)findViewById(R.id.cat2);
		Button b3 = (Button)findViewById(R.id.cat3);
		Button b4 = (Button)findViewById(R.id.cat4);
		Button b5 = (Button)findViewById(R.id.cat5);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		
		Intent i = new Intent(getApplicationContext(), ListOfItems.class);
		switch(arg0.getId())
		{
		case R.id.cat1:
			i.putExtra("category", "dairy");
			break;
		case R.id.cat2:
			i.putExtra("category", "fruits");
			break;
		case R.id.cat3:
			i.putExtra("category", "frozen");
			break;
		case R.id.cat4:
			i.putExtra("category", "meat");
			break;
		case R.id.cat5:
			i.putExtra("category", "beverage");
			break;
		}
		startActivity(i);
	}
}
