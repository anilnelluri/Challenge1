package com.umkc.grocerymart;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Checkout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
	
		final ListView itemsList = (ListView) findViewById(R.id.selecteditemlist);
		itemsList.setAdapter(new ItemCustomAdapter(getApplicationContext(), ShoppingCart.shoppingCartInstance().getAllItems()));
	}
	
	
	
	public class ItemCustomAdapter extends ArrayAdapter<ProductInfo> {
		private final Context context;
		private final ArrayList<ProductInfo> listItems;

		public ItemCustomAdapter(Context context, ArrayList<ProductInfo> values) {
			super(context, R.layout.custom_list_layout_items, values);
			this.context = context;
			this.listItems = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.custom_list_layout_items,
					parent, false);
			TextView tv = (TextView) rowView
					.findViewById(R.id.itemtitle);
			TextView pV = (TextView) rowView.findViewById(R.id.price);
			tv.setText(listItems.get(position).getName());
			pV.setText("$" + listItems.get(position).getPrice());
			return rowView;
		}
	}
}
