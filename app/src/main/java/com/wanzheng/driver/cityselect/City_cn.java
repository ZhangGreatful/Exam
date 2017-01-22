package com.wanzheng.driver.cityselect;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class City_cn {
    /** Called when the activity is first created. */
	private DBManager dbm;
	private SQLiteDatabase db;
	private Context context;
	
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        spinner1=(Spinner)findViewById(R.id.spinner1);
//        spinner2=(Spinner)findViewById(R.id.spinner2);
//        spinner3=(Spinner)findViewById(R.id.spinner3);
//		spinner1.setPrompt("省");
//		spinner2.setPrompt("城市");		
//		spinner3.setPrompt("地区");
//		
//        initSpinner1();
//    }
    
   public City_cn(Context context){
    	this.context=context;
    }
    public List<MyListItem> initSpinner1(){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
	 	try {
	        String sql = "select * from province";  
	        Cursor cursor = db.rawQuery(sql,null);
			cursor.moveToFirst();
			while (!cursor.isLast()){
				String code=cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[]=cursor.getBlob(2);
				String name=new String(bytes,"gbk");
				MyListItem myListItem=new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code=cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[]=cursor.getBlob(2);
			String name=new String(bytes,"gbk");
			MyListItem myListItem=new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
		if (db!=null){
			db.close();
		}


	 	
//	 	MyAdapter myAdapter = new MyAdapter(context,list);
//	 	spinner1.setAdapter(myAdapter);
	 	return  list;
	}
    public List<MyListItem> initSpinner2(String pcode){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from city where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
//	 	MyAdapter myAdapter = new MyAdapter(context,list);
//	 	spinner2.setAdapter(myAdapter);
	 	return  list;
	}
    public List<MyListItem> initSpinner3(String pcode){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from district where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	return  list;
//	 	MyAdapter myAdapter = new MyAdapter(context,list);
//	 	spinner3.setAdapter(myAdapter);
	}
    
}