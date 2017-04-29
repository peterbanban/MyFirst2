package com.example.administrator.mytest2.ContentProviderClass;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.administrator.mytest2.GeneralClass.MyDataBaseHelper;

public class MyContentProvider extends ContentProvider {
    public static final  int BOOK_DIR=0;
    public static final  int BOOK_ITEM=1;
    public static final String AUTHORITY="com.example.administrator.mytest2.ContentProviderClass";
    public static UriMatcher uriMatcher;
    private MyDataBaseHelper dbHelper;
    static {
        //uriMatch用于对表查找进行匹配
        uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper=new MyDataBaseHelper(getContext(),"BookStore.db",null,2);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //查询数据
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor =db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                cursor=db.query("Book",projection,"id=?",new String[]{"bookId"},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book";

            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book";

            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId=db.insert("Book",null,values);
                //用parse将内容URI解析为uri对象，uri对象开头都为"content://",结尾为id
                uriReturn =Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int deleteRows=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deleteRows= db.delete("Book",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);//将uri按"/"进行分割去id部分
                deleteRows= db.delete("Book","id=?",new String[]{bookId});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int updatedRows=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                updatedRows=db.update("Book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                updatedRows=db.update("Book",values,"id=?",new String[]{bookId});
                break;
            default:
                break;

        }
        return updatedRows;
    }
}
