package cn.ucai.fulicenter.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulicenter.I;

/**
 * Created by 11039 on 2016/10/24.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static DBOpenHelper instance;
    private static final String FULICENTER_CREATE_USER_TABLE="CREATE TABLE "
            +UserDao.TABLE_USER_NAME + " ("
            +UserDao.USER_COLUMN_NAME + " TEXT PRIMARY KEY, "
            +UserDao.USER_COLUMN_NICK + " TEXT, "
            +UserDao.USER_COLUMN_AVATAR_ID +" INTEGER, "
            +UserDao.USER_COLUMN_AVATAR_TYPE +" INTEGER, "
            +UserDao.USER_COLUMN_AVATAR_PATH +" TEXT, "
            +UserDao.USER_COLUMN_AVATAR_SUFFIX +" TEXT, "
            +UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME +" TEXT);";

    public static DBOpenHelper getInstance(Context context){
        if(instance==null){
            instance=new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    public DBOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }
    //数据库名
    private static String getUserDatabaseName() {
        return I.User.TABLE_NAME+"_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(FULICENTER_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //关闭数据库
    public void closeDB(){
        if(instance!=null){
            SQLiteDatabase db = getWritableDatabase();
            db.close();
            instance=null;
        }
    }
}
