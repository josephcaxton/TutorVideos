package com.learnersCloud.iEvaluator.Maths250;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.learnersCloud.iEvaluator.Maths250/databases/";
 
    private static String DB_NAME = "GCSEMaths.sqlite";
 
    private SQLiteDatabase myDataBase;
    private SQLiteDatabase TempDataBase; 
 
    private final Context myContext;
	
	
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context){
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
	}
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
    	 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//Check if we need upgrade... If so Copy to replace Database. 
    		
    		if(CheckDatabaseVersion()){  // I don't know why this is not returning the right version from the database so shot by replacing database everytime
    			
    			//Delete Exisiting Database and replace
    			String myPath = DB_PATH + DB_NAME;
    			File file = new File(myPath);
    			file.delete();

    			copyDataBase();
    			
    		}else{ 
    		//Do nothing Database is same as bundle copy
    		// Log.v("Joseph","Not copied");
    		
    		}
    		
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
    			//myDataBase.close();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	//SQLiteDatabase checkDB = null;
    	
    	myDataBase= null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(myDataBase != null){
 
    		myDataBase.close();
 
    	}
 
    	return myDataBase != null ? true : false;
    }
    
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
    private void CopyTempDB()throws IOException{
    	
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + "TempDB.sqlite";
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	
	public Cursor query(String TableName, String[] projection, String WhereFilter, String[] selectionArgs,String GroupBy,String Having, String OrderBy) {
		
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	    qb.setTables(TableName);
	    
	 // Opens the database object in "read" mode, since no writes need to be done.
	       SQLiteDatabase db = this.getReadableDatabase();

	       Cursor c = qb.query(db, projection, WhereFilter, selectionArgs, GroupBy, Having, OrderBy) ;
	       
	       return c;


		
	}
	
	public Cursor query1(String TableName, String[] projection, String WhereFilter, String[] selectionArgs,String GroupBy,String Having, String OrderBy, String LIMIT) {
		// This is used to include a LIMIT
		
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	    qb.setTables(TableName);
	    
	 // Opens the database object in "read" mode, since no writes need to be done.
	       SQLiteDatabase db = this.getReadableDatabase();

	       Cursor c = qb.query(db, projection, WhereFilter, selectionArgs, GroupBy, Having, OrderBy, LIMIT) ;
	       
	       return c;


		
	}

public Cursor Rawquery(String qr, String[] selectionArgs) {
		  
	    
	 // Opens the database object in "read" mode, since no writes need to be done.
	       SQLiteDatabase db = this.getReadableDatabase();

	       Cursor cur = db.rawQuery(qr, selectionArgs);
	   	
	       return cur;


		
	}
public void InsertToDatabase(String Query){
	
	// Used to insert to database 
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	myDataBase.execSQL(Query);
	
	myDataBase.close();
	
	
	 
}

private boolean CheckDatabaseVersion() throws IOException
{
	int InstalledVersion = 0;
	int BundleVersion = 0;
	myDataBase= null;
	
	try{
		
		// Get Version Number of Phone Database
		String InstalledPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(InstalledPath, null, SQLiteDatabase.OPEN_READONLY);
		
		String[] Columns = {"VERSIONNUMBER"};
		
		Cursor c = this.query("DBVERSION", Columns,
				null, null, null, null, null);
		
		 if (c.getCount() > 0){
				
				c.moveToPosition(0);
				InstalledVersion = Integer.parseInt(c.getString(0));
				
				}
		 
		 myDataBase.close();
		// Get Version Number of Bundled Database
		 
		 CopyTempDB();
		 //backupDatabase();
		 
		 String BundlePath = DB_PATH + "TempDB.sqlite";
		 
		 TempDataBase= null;
		 
		 TempDataBase = SQLiteDatabase.openDatabase(BundlePath, null, SQLiteDatabase.OPEN_READONLY);
			
			String[] BundleColumns = {"VERSIONNUMBER"};
			
			Cursor Bundlecursor = this.query("DBVERSION", BundleColumns,
					null, null, null, null, null);
			
			 if (Bundlecursor.getCount() > 0){
					
				 Bundlecursor.moveToPosition(0);
					BundleVersion = Integer.parseInt( Bundlecursor.getString(0));
					
					}
			 
			 TempDataBase.close();
			 
			 if(BundleVersion > InstalledVersion ){
				 
				 RemoveTempDB();
				 return true;
			 }
			 else
				 
			 {
				 RemoveTempDB();
				 return false;
			 }

	}catch(SQLiteException e){

		//There is a problem.
		return false;

	}

	
	
}

private void RemoveTempDB() {
	String myPath = DB_PATH + "TempDB.sqlite";
	
	File file = new File(myPath);
	
	file.delete();
	
}

// I am using this for testing only
public static void backupDatabase() throws IOException {
    //Open your local db as the input stream
    String inFileName = "/data/data/com.learnersCloud.iEvaluator.Maths250/databases/TempDB.sqlite";
    File dbFile = new File(inFileName);
    FileInputStream fis = new FileInputStream(dbFile);

    String outFileName = Environment.getExternalStorageDirectory()+"/TempDB.sqlite";
    //Open the empty db as the output stream
    OutputStream output = new FileOutputStream(outFileName);
    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = fis.read(buffer))>0){
        output.write(buffer, 0, length);
    }
    //Close the streams
    output.flush();
    output.close();
    fis.close();
}
	
	
}

