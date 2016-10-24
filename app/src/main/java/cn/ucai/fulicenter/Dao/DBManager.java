package cn.ucai.fulicenter.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.fulicenter.bean.UserAvatar;

/**
 * Created by 11039 on 2016/10/24.
 */
public class DBManager {
    private static DBManager dbManager=new DBManager();
    private static DBOpenHelper mHelper;
    void onInit(Context context){
        mHelper=new DBOpenHelper(context);
    }
    public static synchronized DBManager getInstance(){
        return dbManager;
    }
    public synchronized void closeDB(){
        if(mHelper!=null){
            mHelper.closeDB();
        }
    }

    public synchronized boolean saveUser(UserAvatar user){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME,user.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATAR_ID,user.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATAR_TYPE,user.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATAR_PATH,user.getMavatarPath());
        values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX,user.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME,user.getMavatarLastUpdateTime());
        if(db.isOpen()){
            return db.replace(UserDao.TABLE_USER_NAME,null,values)!=-1;
        }
        return false;
    }
    public synchronized UserAvatar getUser(String userName){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql="select * from "+UserDao.TABLE_USER_NAME+" where "+UserDao.USER_COLUMN_NAME +" =?";
        Cursor c = db.rawQuery(sql, new String[]{userName});
        while(c.moveToNext()){
            UserAvatar user = new UserAvatar();
            user.setMuserName(userName);
            user.setMuserNick(c.getString(c.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(c.getInt(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarType(c.getInt(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return null;
    }
    public synchronized boolean updateUser(UserAvatar user){
        int result=-1;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String where=UserDao.USER_COLUMN_NAME+" =?";
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        if(db.isOpen()){
            result=db.update(UserDao.TABLE_USER_NAME,values,where,new String[]{user.getMuserName()});
        }
        return result>0;
    }
}
