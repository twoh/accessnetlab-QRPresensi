package id.lab.akses.presensi;



import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.BarcodeTest.IntentIntegrator;
import com.example.BarcodeTest.IntentResult;

public class QRPresensiActivity extends Activity {
    /** Called when the activity is first created. */
	
	private String jenisprak;
	private Calendar c;
	private String date;
	private String time;
	private String upc;
	private ArrayList<NameValuePair> dataMap = new ArrayList<NameValuePair>(4);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle b = this.getIntent().getExtras();
        c = Calendar.getInstance();
        
        SimpleDateFormat fdate = new SimpleDateFormat("dd/MM/yyyy");
        date = fdate.format(c.getTime());
        
        SimpleDateFormat ftime = new SimpleDateFormat("HH:mm");
        time = ftime.format(c.getTime());
        
        jenisprak = String.valueOf(b.getInt("prak"));
        Log.v("Access PRESENSI", jenisprak);
        Log.v("Access PRESENSI", date);
        Log.v("Access PRESENSI", time);
        
        Toast.makeText(this, "praktikum "+jenisprak, Toast.LENGTH_LONG).show();
        IntentIntegrator.initiateScan(this);
        
        
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	
    	
    	switch(requestCode) {
    	case IntentIntegrator.REQUEST_CODE: {
    	if (resultCode != RESULT_CANCELED) {
    	IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    	if (scanResult != null) {
    	upc = scanResult.getContents();
    	
    	final AlertDialog.Builder builder=new AlertDialog.Builder(QRPresensiActivity.this);
		builder.setTitle("QRPresensi");
		builder.setMessage("NIM : "+upc+"\n"+
				"Praktikum : "+jenisprak+"\n"+
				"Tanggal : "+date+"\n"+
				"Waktu : "+time+"\n"
				);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton("Simpan", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Buat simpan ke database
				try {
					postDataPraktikum();
					QRPresensiActivity.this.finish();
					IntentIntegrator.initiateScan(QRPresensiActivity.this);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	 
    	});
		builder.setNegativeButton("Coba Lagi", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				QRPresensiActivity.this.finish();
				IntentIntegrator.initiateScan(QRPresensiActivity.this);
			}
			
		});
			builder.show();
    				}
    		break;
    			}
    		}
    	}
    	
}
    
    protected void postDataPraktikum() throws URISyntaxException, InterruptedException, ClientProtocolException, IOException
    {
    	dataMap.add(new BasicNameValuePair("modul",jenisprak));
    	dataMap.add(new BasicNameValuePair("nim",upc));
    	dataMap.add(new BasicNameValuePair("jam",time));
		dataMap.add(new BasicNameValuePair("tanggal",date));
		
		
		try {
			URI uri = new URI("http://10.11.6.20/akses/insert.php");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(uri);
			httpost.setEntity(new UrlEncodedFormEntity(dataMap,HTTP.UTF_8));
			
			HttpResponse response = httpclient.execute(httpost);
			//HttpEntity entity = response.getEntity();
			//InputStream is = entity.getContent();
			Log.i("postData ",response.getStatusLine().toString());
			String responseBody = EntityUtils.toString(response.getEntity());
			Log.i("postData ",responseBody);
			int status = response.getStatusLine().getStatusCode();
			Log.i("postData "," "+status);
			
			if(status==200)
			{
				
				Toast.makeText(this, "Sukses", Toast.LENGTH_LONG).show();
				
			}
		}
		finally
		{
			
		}
    }
    
}