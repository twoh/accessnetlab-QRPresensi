package id.lab.akses.presensi;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity implements OnClickListener{

	private String[] praktikum =
		{
			"Praktikum A", "Praktikum B", "Praktikum C"
		};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, praktikum);
        setListAdapter(adapter);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String praktikumNow = praktikum[position];
		Bundle b = new Bundle();
		b.putString("prak", praktikumNow);
		Intent i = new Intent(this, QRPresensiActivity.class);
		startActivity(i);
	}
}
