package com.CriGiu.thememyxperia;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Themer extends Activity {
	

	boolean pressed;
	
	
	AlertDialog loading;
	Button apply, reboot;
	DataOutputStream os;
	File folder1, folder2, folder3, folderTmp;
	File frameWork = new File("/sdcard/TMX/tmp/framework-res.apk");
	File semcFrameWork = new File("/sdcard/TMX/tmp/SemcGenericUxpRes.apk");
	File statusBar = new File("/sdcard/TMX/tmp/SystemUI.apk");
	ImageView screen;
	Process p;
	RadioButton r1, r2, r3, r4, rDefault;
	TextView themeName;
	String isDone;
	String folder = "/sdcard/TMX/tmp/";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themer);
		
		apply = (Button)findViewById(R.id.bApply);
		r1 = (RadioButton)findViewById(R.id.r1);
		r2 = (RadioButton)findViewById(R.id.r2);
		r3 = (RadioButton)findViewById(R.id.r3);
		rDefault = (RadioButton)findViewById(R.id.rDefault);
		reboot = (Button)findViewById(R.id.bReboot);
		screen = (ImageView)findViewById(R.id.ivScreen);
		themeName = (TextView)findViewById(R.id.tvTheme);
		
		//Making Core Folders
        folder1 = new File("/sdcard/TMX/1/");
        folder2 = new File("/sdcard/TMX/2/");
        folder3 = new File("/sdcard/TMX/3/");
        folderTmp = new File("/sdcard/TMX/tmp/");
        folder1.mkdir();
        folder2.mkdir();
        folder3.mkdir();
        folderTmp.mkdir();

        
        
        
        //Obtaining SU And Extracting SEMC Default Theme From Assets 
        try { 
		    p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());	
            InputStream myInput = getAssets().open("default.zip");
		    String outFileName = "/sdcard/TMX/tmp/DefaultSEMC.zip";
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
           {myOutput.write(buffer, 0, length);}
            myOutput.flush();
            myOutput.close();
            myInput.close();
		    } catch (IOException e) {
			    e.printStackTrace();
		}

        rDefault.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			themeName.setText("SEMC Default Theme");
			Bitmap themePreview = BitmapFactory.decodeResource(getResources(), R.drawable.semc_preview);
		    screen.setImageBitmap(themePreview);
			}
		 });
		
		r1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				File zip = new File("/sdcard/TMX/1/theme.zip");
			    String file = "/sdcard/TMX/1/theme.zip";
					if(zip.exists()){
					try{

						UnzipUtil a = new UnzipUtil(file, folder);
						a.unzip();
						readProp();
						
					} catch (Exception e) {}
					} else  {
						themeName.setText("No theme has been found");
						screen.setImageResource(R.drawable.no_preview);
						Toast.makeText(getApplicationContext(), "No theme has been found into" + folder, Toast.LENGTH_SHORT).show();
					}
			}
		});
		
		r2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				File zip = new File("/sdcard/TMX/2/theme.zip");
			    String file = "/sdcard/TMX/2/theme.zip";
					if(zip.exists()){
					try{
						UnzipUtil a = new UnzipUtil(file, folder);
						a.unzip();
						readProp();
						
					} catch (Exception e) {}
					} else  {
						themeName.setText("No theme has been found");
						screen.setImageResource(R.drawable.no_preview);
						Toast.makeText(getApplicationContext(), "No theme has been found into" + folder, Toast.LENGTH_SHORT).show();
					}
			}
		});
		
		r3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				File zip = new File("/sdcard/TMX/3/theme.zip");
			    String file = "/sdcard/TMX/3/theme.zip";
					if(zip.exists()){
					try{
						UnzipUtil a = new UnzipUtil(file, folder);
						a.unzip();
						readProp();
						
					} catch (Exception e) {}
					} else  {
						themeName.setText("No theme has been found");
					    screen.setImageResource(R.drawable.no_preview);
						Toast.makeText(getApplicationContext(), "No theme has been found into" + folder, Toast.LENGTH_SHORT).show();
					}
			}
		});
        
        apply.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0){
            

			if (rDefault.isChecked()){

					try{
				        String file = "/sdcard/TMX/tmp/DefaultSEMC.zip";
						UnzipUtil a = new UnzipUtil(file, folder);
						a.unzip();
						applyTheme();
						
					} catch (Exception e) {}

			}
			
			if (r1.isChecked()){
				
				    if(frameWork.exists() && statusBar.exists() && semcFrameWork.exists()){
				        applyTheme();
				        
				    }else{
			        	Toast.makeText(getApplicationContext(), "Sorry, file(s) missing into theme.zip", Toast.LENGTH_SHORT).show();
				    }
			}
			
			if (r2.isChecked()){ 
				
			    if(frameWork.exists() && statusBar.exists() && semcFrameWork.exists()){
			        applyTheme();
			        
			    }else{
		        	Toast.makeText(getApplicationContext(), "Sorry, file(s) missing into theme.zip", Toast.LENGTH_SHORT).show();
		   }
		}
			
			if (r3.isChecked()){
				
			    if(frameWork.exists() && statusBar.exists() && semcFrameWork.exists()){
			        applyTheme();
			        
			    }else{
		        	Toast.makeText(getApplicationContext(), "Sorry, file(s) missing into theme.zip", Toast.LENGTH_SHORT).show();
		   }
		}
	  }
   });
               
        reboot.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		    try {
	    	    os.writeBytes("reboot\n");
		        } catch (Exception e){
		        	
		        }
			}
		});
	}
	
	//Method For Reading Theme Properties
    public void readProp() {
	
    	try {
            File myFile = new File("/sdcard/TMX/tmp/title.xml");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            themeName.setText(aBuffer);
            themeName.setGravity(Gravity.CENTER);
            myReader.close();
        } catch (Exception e) {
        	themeName.setText("There's no theme in there");
        	Toast.makeText(getApplicationContext(), "Sorry, no theme has been found into" + folder, Toast.LENGTH_SHORT).show();
        }
	        Bitmap themePreview = BitmapFactory.decodeFile("/sdcard/TMX/tmp/screen.png");
            screen.setImageBitmap(themePreview);
	}
		
	
    //Method For Applying Theme
	public void applyTheme() {
		
		try {
            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock0 /system\n"
                         + "busybox cp -f /sdcard/TMX/tmp/framework-res.apk /system/framework/framework-res.apk\n"
                         + "busybox cp -f /sdcard/TMX/tmp/SystemUI.apk /system/app/SystemUI.apk\n"
                         + "busybox cp -f /sdcard/TMX/tmp/SemcGenericUxpRes.apk /system/framework/SemcGenericUxpRes.apk\n"
                         + "busybox rm -r /sdcard/TMX/tmp/\n"
                         + "reboot\n");
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//Method For Closing The App After Pressing BACK Twice
    public void onBackPressed() {
		if (pressed) {
            super.onBackPressed();
            return;
        }
        this.pressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        try {
			os.writeBytes("busybox rm -r /sdcard/TMX/tmp/\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             pressed = false;   
            }
        }, 1000);
    } 	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.themer, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			
		case R.id.exit:
			
			try {
				os.writeBytes("busybox rm -r /sdcard/TMX/tmp/\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			finish();
			
			break;
		 
		}
		return false;
	}
    
	

}
