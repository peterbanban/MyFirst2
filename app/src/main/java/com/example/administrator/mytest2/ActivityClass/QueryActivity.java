package com.example.administrator.mytest2.ActivityClass;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mytest2.GeneralClass.Book;
import com.example.administrator.mytest2.GeneralClass.MyDataBaseHelper;
import com.example.administrator.mytest2.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDataBaseHelper dbHelp;
    EditText editInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();

        dbHelp=new MyDataBaseHelper(this,"BookStore.db",null,1);

        Button btnAddQurey= (Button) findViewById(R.id.btn_addQuery);
        Button btnAdd= (Button) findViewById(R.id.btn_add);
        Button btnDel= (Button) findViewById(R.id.btn_del);
        Button btnUpdate= (Button) findViewById(R.id.btn_update);
        Button btnSelect= (Button) findViewById(R.id.btn_select);


        Button btnAddQurey1= (Button) findViewById(R.id.btn_addQuery1);
        Button btnAdd1= (Button) findViewById(R.id.btn_add1);
        Button btnDel1= (Button) findViewById(R.id.btn_del1);
        Button btnUpdate1= (Button) findViewById(R.id.btn_update1);
        Button btnSelect1= (Button) findViewById(R.id.btn_select1);
        Button btnNext= (Button) findViewById(R.id.btn_next1);
        editInput= (EditText) findViewById(R.id.edit_input);
        btnAddQurey.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        btnAddQurey1.setOnClickListener(this);
        btnAdd1.setOnClickListener(this);
        btnDel1.setOnClickListener(this);
        btnSelect1.setOnClickListener(this);
        btnUpdate1.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_addQuery:
                dbHelp.getWritableDatabase();
                Toast.makeText(this,"成功创建数据库",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_add:
                SQLiteDatabase db=dbHelp.getWritableDatabase();
                ContentValues values =new ContentValues();
                values.put("name","hu");
                values.put("author","hu");
                values.put("pages",100);
                values.put("price",100);
                db.insert("Book",null,values);
                values.clear();
                Toast.makeText(this,"成功添加数据",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_update:
                 SQLiteDatabase db1=dbHelp.getWritableDatabase();
                ContentValues values1=new ContentValues();
                values1.put("price",200);
                db1.update("Book",values1,"name=?",new String []{"hu"});
                break;
            case R.id.btn_del:
                SQLiteDatabase db2=dbHelp.getWritableDatabase();
                db2.delete("Book","name=?",new String[]{"hu"});
                break;
            case R.id.btn_select:
                SQLiteDatabase db3=dbHelp.getReadableDatabase();
                Cursor cursor=db3.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                  do {
                       String name=cursor.getString(cursor.getColumnIndex("name"));
                       double price=cursor.getInt(cursor.getColumnIndex("price"));
                       editInput.setText("name="+name+"price="+price);
                  }while(cursor.moveToNext());

                }
                cursor.close();
                break;
            case R.id.btn_addQuery1 :
                LitePal.getDatabase();
                Toast.makeText(this, "成功通过LitePal创建数据库", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add1:
                Book book=new Book();
                book.setAuthor("qian");
                book.setPages(120);
                book.setName("qian");
                book.setPrice(300);
                book.save();
                Book book0=new Book();
                book.setAuthor("xu");
                book.setPages(125);
                book.setName("xu");
                book.setPrice(600);
                book0.save();
                break;
            case R.id.btn_update1:
                Book book1=new Book();
                 book1.setPrice(500);
                break;
            case  R.id.btn_del1:
                DataSupport.deleteAll(Book.class,"name=?","xu");
                break;
            case R.id.btn_select1:
                List<Book> book2= DataSupport.findAll(Book.class);
                editInput.setText(book2.get(0).getName()+""+book2.get(0).getPrice()+book2.get(1).getName()+""+book2.get(1).getPrice());
                break;
            case R.id.btn_next1:
                Intent intent =new Intent(QueryActivity.this, ChoosePictureActivity.class);
                startActivity(intent);
        }

    }
}
