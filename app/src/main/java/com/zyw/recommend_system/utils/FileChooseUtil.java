package com.zyw.recommend_system.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * 此类用于选择文件返回，除了可以选择图片，还能选择视频，以及其他类型文件
 */

public class FileChooseUtil {

    private Context context;
    private static FileChooseUtil util = null;

    private FileChooseUtil(Context context) {
        this.context = context;
    }

    public static FileChooseUtil getInstance(Context context) {
        if (util == null) {
            util = new FileChooseUtil(context);
        }
        return util;
    }

    /**
     * 对外接口  获取uri对应的路径
     *
     * @param uri
     * @return
     */
    public String getChooseFileResultPath(Uri uri) {
        String chooseFilePath = null;
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            chooseFilePath = uri.getPath();
            Toast.makeText(context, chooseFilePath, Toast.LENGTH_SHORT).show();
            return chooseFilePath;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            chooseFilePath = getPath(context, uri);
        } else {//4.4以下下系统调用方法
            chooseFilePath = getRealPathFromURI(uri);
        }
        return chooseFilePath;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    private String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        //视频的uri：content://com.android.providers.media.documents/document/video%3A1079910
        // DocumentProvider
        //判断是不是要用系统的内容提供器啦，如果不是，就是我用的WPS的内容提供器就不会跳进去
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            //
            if (isExternalStorageDocument(uri)) {
                //通过uri获得文件ID
                Log.e("FileChooseUtil", "getPath:+uri "+uri);
                final String docId = DocumentsContract.getDocumentId(uri);
                Log.e("FileChooseUtil", "getPath:+docId "+docId);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
              //  安卓 8.0选择download文件夹 需要处理 ，id返回的是字符串，不能用Long.valueOf(id)
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//                return getDataColumn(context, contentUri, null, null);
                final String id = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        Log.e("FileChooseUtil", "getPath: contentUri"+contentUri );
                        return getDataColumn(context, contentUri, null, null);
                    } catch (NumberFormatException e) {
                        return null;
                    }

                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                Log.e("contentUri", "getPath: Mediauri"+uri );
                final String docId = DocumentsContract.getDocumentId(uri);
                Log.e("contentUri", "getPath:Media docId"+docId );
                //docId是 video:1079910 我也不知道它怎么搞的
                final String[] split = docId.split(":");
                //split = ["video", "1079910"]
                //type很明显是 video
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                } else if ("audio".equals(type)) {
                    //contentUri = content://media/external/video/media
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                }

                final String selection = "_id=?";
                //selectionArgs = 1079910 视频的id
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }

        }
        // MediaStore (and general) 如果我用的第三方的其他app的内容提供器，就不是系统的内容提供器，会调过来这里
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);

        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            uri.getPath();

        }
        return null;
    }

    //uri相当于某张表 projection表示查询的列名 selection像where 表示要约束的条件 selectionArgs 为where中的占位符提供具体值 sortOrder为查询结果排序方式
    // 查询到返回一个cursor对象
    //这里  content://media/external/video/media 查的是这个表
    //但如果是第三方的话，content://cn.wps.moffice_eng.fileprovider/external_storage_root/Android/data/cn.wps.moffice_eng/.Cloud/cn/636705769/f/a89cbab2-bbeb-47ec-b874-51d0c55c50bd/theatre.txt uri是这个，说明一个uri一个表？
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        //查找data列  cursor =
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                //moveToFirst 数据指针移动到第一行，遍历查询每一行数据  寻找_data列的column的index
                final int column_index = cursor.getColumnIndexOrThrow(column);
                String res;
                res = cursor.getString(column_index);
                return res;
            }
        } finally {
            if (cursor != null)
                cursor.close(); //用完关闭
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
