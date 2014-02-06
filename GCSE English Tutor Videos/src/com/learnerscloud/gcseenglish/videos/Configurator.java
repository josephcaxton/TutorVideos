package com.learnerscloud.gcseenglish.videos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

public class Configurator {
	
	public boolean DownloadFile(String fileURL, String filename, Context con) {
		
		DeleteFileIfExist(filename, con);
			
        try {
             
        	File file = new File(con.getExternalFilesDir(null), filename);
        	
            FileOutputStream f = new FileOutputStream(file);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setAllowUserInteraction(false);
            c.setDoInput(true);
           // c.setDoOutput(true);
            c.setReadTimeout(10000);
            c.connect();
 
            InputStream in = c.getInputStream();
 
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
 
    }
	
	private boolean DeleteFileIfExist(String fileName, Context con){
		
		File file = new File(con.getExternalFilesDir(null), fileName);
		if (file != null) {
			
			 file.delete();
			
		}
		 return true;
	}
	
	public boolean hasExternalStoragePrivateFile(String fileName, Context con) {
	   
	    File file = new File(con.getExternalFilesDir(null), fileName);
	    if (file != null) {
	        return file.exists();
	    }
	   
	    return false;
	    
	}
	
	

}
