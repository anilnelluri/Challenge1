package com.umkc.grocerymart;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfItems extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_items);

		final ListView itemsList = (ListView) findViewById(R.id.itemlist);
		TextView tv = (TextView)findViewById(R.id.noitems);
		String category = "dairy";
		if(getIntent().getStringExtra("category").isEmpty())
			category = "dairy";
		else
			category = getIntent().getStringExtra("category");
			
		new GetStoreListAsyncTask(itemsList, tv).execute(category);

		itemsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("++++++++ Item clicked");
				
				ProductInfo obj = (ProductInfo)itemsList.getAdapter().getItem(arg2);
				
				ShoppingCart.shoppingCartInstance().addAProduct(obj);
				
				Toast.makeText(getApplicationContext(), "Press back to select another category", Toast.LENGTH_SHORT).show();
						
			}
			
		});
		
		itemsList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				System.out.println("++++++++ Item selected");
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private class GetStoreListAsyncTask extends
			AsyncTask<String, Void, ArrayList<ProductInfo>> {

		ListView listV;
		TextView textV;

		public GetStoreListAsyncTask(ListView list, TextView text) {
			this.listV = list;
			this.textV = text;
		}

		@Override
		protected ArrayList<ProductInfo> doInBackground(String... params) {
			return new ResponseReader().getItemDetailsFromCategory(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ProductInfo> listOfSearchedItems) {
			
			System.out.println("++++++++++++ NUmber of items in list: "+listOfSearchedItems.size());
			if (listOfSearchedItems.size() > 0) {
				listV.setVisibility(View.VISIBLE);
				textV.setVisibility(View.GONE);
				listV.setAdapter(new ItemCustomAdapter(getApplicationContext(),
						listOfSearchedItems));

			} else {
				textV.setText("No Items found for specified category");
				listV.setVisibility(View.GONE);
				textV.setVisibility(View.VISIBLE);
			}
		}

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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(getApplicationContext(), Checkout.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
