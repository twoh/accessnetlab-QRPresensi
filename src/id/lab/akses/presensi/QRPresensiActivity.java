package id.lab.akses.presensi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.BarcodeTest.IntentIntegrator;
import com.example.BarcodeTest.IntentResult;

public class QRPresensiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        IntentIntegrator.initiateScan(this);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    	case IntentIntegrator.REQUEST_CODE: {
    	if (resultCode != RESULT_CANCELED) {
    	IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    	if (scanResult != null) {
    	String upc = scanResult.getContents();
    	
    	
    	//mulai dari sini
    	//setContentView(R.layout.main);
    	//Button bt=(Button)findViewById(R.id.bt);
    	/*bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentIntegrator.initiateScan(QRPresensiActivity.this);
			}
		});
    	Button save=(Button)findViewById(R.id.bt2);
    	save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sv=new Intent(QRPresensiActivity.this, List.class);
				startActivity(sv);
			}
		});*/
    	//TextView tv = (TextView)findViewById(R.id.tv);
    	//tv.setText(upc);
    	final AlertDialog.Builder builder=new AlertDialog.Builder(QRPresensiActivity.this);
		builder.setTitle("QRPresensi");
		builder.setMessage(upc);
		builder.setIcon(android.R.drawable.ic_dialog_alert);

		builder.setPositiveButton("Simpan", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Buat simpan ke database
				
			}
    	 
    	});
		builder.setNegativeButton("Capture", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
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
}