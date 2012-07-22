package net.chenyuzhao.cafecounter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.content.pm.PackageManager;


public class EditTransactionActivity extends CCActivity {
	AccountsDataSource ads;
	TransactionsDataSource tds;

	Camera camera;
	int cameraId = 0;
	
	Button btnReadReceiptCamera, btnReadReceiptFile;
	Spinner spnAccounts;
	RadioButton btnMadePayment, btnReceivedPayment;
	EditText txtDollar, txtCent, txtName, txtAddress, txtDescription, txtDate;
	ImageView imgReceipt;
	Button btnNewTransaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_transaction_layout);

		btnReadReceiptCamera = (Button)findViewById(R.id.btnReadReceiptCamera);
		btnReadReceiptFile = (Button)findViewById(R.id.btnReadReceiptFile);
		spnAccounts = (Spinner)findViewById(R.id.spnAccounts);
		btnMadePayment = (RadioButton)findViewById(R.id.btnMadePayment);
		btnReceivedPayment = (RadioButton)findViewById(R.id.btnReceivedPayment);
		txtDollar = (EditText) findViewById(R.id.txtDollar);
		txtCent = (EditText) findViewById(R.id.txtCent);
		txtName = (EditText) findViewById(R.id.txtName);
		txtAddress = (EditText) findViewById(R.id.txtAddress);
		txtDescription = (EditText) findViewById(R.id.txtDescription);
		txtDate = (EditText) findViewById(R.id.txtDate);
		imgReceipt = (ImageView)findViewById(R.id.imgReceipt);
		btnNewTransaction = (Button) findViewById(R.id.btnNewTransaction);
		
		btnMadePayment.setChecked(true);

		ads = new AccountsDataSource(this);
		tds = new TransactionsDataSource(this);
		
		ads.open();
		List<Account> accounts = ads.getAllAccounts();
		final ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_spinner_dropdown_item, accounts);
		spnAccounts.setAdapter(adapter);
		spnAccounts.setSelection( 0 );
		ads.close();
		
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.w("cam","asdfasdf");
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
		} else {
			cameraId = findFrontFacingCamera();
			camera = Camera.open(cameraId);
			Log.w("cam",camera == null ? "a" : "b");
			if (cameraId < 0) {
				Toast.makeText(this, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
			}
		}
		
		btnReadReceiptCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(camera != null) {
					camera.takePicture(null, null,new PhotoHandler(getApplicationContext()));
				}
			}
		});
		
		btnReadReceiptFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), FileDialogActivity.class);
                intent.putExtra(FileDialogActivity.START_PATH, "/sdcard/Pictures");
                intent.putExtra(FileDialogActivity.CAN_SELECT_DIR, false);
                startActivityForResult(intent, 10);
			}
		});

		btnNewTransaction.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String amount = txtDollar.getText().toString() + txtCent.getText().toString();
				
				if( btnMadePayment.isChecked() ) {
					amount = "-" + amount;
				}
				
				Account account = (Account)spnAccounts.getSelectedItem();
				
				String name = txtName.getText().toString();
				String address = txtAddress.getText().toString();
				String description = txtDescription.getText().toString();
				String timestamp = X.datestringToTimestamp(txtDate.getText().toString());
				String account_id = String.valueOf(account.id);
				
				tds.open();
				tds.createTransaction(amount, name, address, description, timestamp, account_id);
				tds.close();
				
				ads.open();
				account.balance += Long.parseLong(amount);
				ads.updateAccount(account);
				ads.close();
				
				Intent intent = new Intent(EditTransactionActivity.this, TransactionLogActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
    	if (resultCode == Activity.RESULT_OK && requestCode == 10) {
    		String filePath = data.getStringExtra(FileDialogActivity.RESULT_PATH);
    		File file = new File(filePath);
    		imgReceipt.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
    	}
    }
}
