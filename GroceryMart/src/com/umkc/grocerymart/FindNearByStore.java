package com.umkc.grocerymart;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FindNearByStore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_store);

		final EditText zip = (EditText) findViewById(R.id.zipcode);
		final ListView storeList = (ListView) findViewById(R.id.storeslist);
		final TextView noStoresText = (TextView) findViewById(R.id.nostores);
		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String zipcode = zip.getText().toString();
				if(zipcode.isEmpty())
					return;
				System.out.println("Entered zip code: " + zipcode);
				new GetStoreListAsyncTask(storeList, noStoresText)
						.execute(zipcode);
			}
		});
		
		storeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				StoreDetails storeDetails = (StoreDetails)storeList.getAdapter().getItem(arg2);
				ShoppingCart.shoppingCartInstance().setStoreDetails(storeDetails);
				
				Intent i = new Intent(getApplicationContext(), ItemCategory.class);
				startActivity(i);
			}
		});
	}

	private class GetStoreListAsyncTask extends
			AsyncTask<String, Void, ArrayList<StoreDetails>> {

		ListView listV;
		TextView textV;

		public GetStoreListAsyncTask(ListView list, TextView text) {
			this.listV = list;
			this.textV = text;
		}

		@Override
		protected ArrayList<StoreDetails> doInBackground(String... params) {
			return new ResponseReader().getStoreDetailsFromZipcode(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<StoreDetails> listOfSearchedItems) {
			if (listOfSearchedItems.size() > 0) {
				listV.setVisibility(View.VISIBLE);
				textV.setVisibility(View.GONE);
				listV.setAdapter(new CustomAdapter(FindNearByStore.this,
						listOfSearchedItems));

			} else {
				textV.setText("No Stores found in specified zipcode");
				listV.setVisibility(View.GONE);
				textV.setVisibility(View.VISIBLE);
			}
		}

	}

	public class CustomAdapter extends ArrayAdapter<StoreDetails> {
		private final Context context;
		private final ArrayList<StoreDetails> values;

		public CustomAdapter(Context context, ArrayList<StoreDetails> values) {
			super(context, R.layout.custom_list_layout_stores, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.custom_list_layout_stores,
					parent, false);
			TextView storename = (TextView) rowView
					.findViewById(R.id.storename);
			TextView address = (TextView) rowView.findViewById(R.id.address);
			storename.setText(values.get(position).getName());
			address.setText(values.get(position).getAddress());

			return rowView;
		}
	}
}
