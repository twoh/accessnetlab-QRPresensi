package id.lab.akses.presensi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class Menu extends Activity implements OnItemClickListener  {

	private String[] praktikum =
		{
			"SMS Gateway", "Radio Internet", "VoIP","IPTV dan VOD"
		};
	
	private String[] lab =
		{
			"by Lab. Jaringan Akses", "by Lab. Jaringan Akses", "by Lab. Jaringan Akses","by Lab. Jaringan Akses"
		};
	
	private int[] icons = new int[]{
			R.drawable.sms_gateway,
			R.drawable.radio,
			R.drawable.voip,
	        R.drawable.iptv,
	    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
     // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
 
        for(int i=0;i<=3;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("prak",praktikum[i]);
            hm.put("lab",lab[i]);
            hm.put("icon", Integer.toString(icons[i]) );
            aList.add(hm);
        }
 
        // Keys used in Hashmap
        String[] from = { "icon","prak","lab" };
 
       // Ids of views in listview_layout
       int[] to = { R.id.flag,R.id.txt,R.id.cur};
 
       // Instantiating an adapter to store each items
       // R.layout.listview_layout defines the layout of each item
       SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_layout, from, to);
       ListView listView =(ListView)findViewById(R.id.listx);
       //ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, praktikum);
       //Log.v("qrpresensi", listView.toString());
       listView.setAdapter(adapter);
       
       

       // Setting the item click listener for the listview
       listView.setOnItemClickListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub
		   String praktikumNow = praktikum[position];
    	   Bundle b = new Bundle();
    	   b.putInt("prak", position+1);
    	   Intent i = new Intent(this, QRPresensiActivity.class).putExtras(b);
    	   startActivity(i);
	}
	
    
    
	
}
