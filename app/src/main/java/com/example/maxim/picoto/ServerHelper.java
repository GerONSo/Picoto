package com.example.maxim.picoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by maxim on 25.03.18.
 */

public class ServerHelper {



    public interface OnImageResult{
        void onImageResult(Bitmap image);
    }

    private static ServerSocket server;
    private static int port=9856;
    private static String ip="194.87.103.212";
    private static OnImageResult callback;

    public static void sendImage(Bitmap image,int styleType,OnImageResult c) throws IOException {
        new AsyncSendImage().execute(new AsyncTaskData(image,styleType));
        callback=c;
    }

    static class AsyncTaskData{
        private Bitmap image;
        private int position;

        public AsyncTaskData(Bitmap image, int position) {
            this.image = image;
            this.position = position;
        }

        public Bitmap getImage() {
            return image;
        }

        public int getPosition() {
            return position;
        }


    }

    static class AsyncSendImage extends AsyncTask<AsyncTaskData,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(AsyncTaskData... data)  {
            Bitmap image=data[0].getImage();
            int styleType=data[0].getPosition();
            Bitmap imageResult=null;
            try {
                ByteArrayOutputStream stream=new ByteArrayOutputStream();

                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte imageBytes[]=stream.toByteArray();
                stream.close();
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                Log.d("style", String.valueOf(image.getByteCount()));
                Socket socket=new Socket(ip,port);
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());



                int i;
                oos.writeObject(styleType);
                while(true) {
                    i=bais.read();
                    if(i==-1) break;
                    oos.writeObject(i);

                }
                oos.writeObject(-1);
                bais.close();
                //Log.d("sss","1112");
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());

                ByteArrayOutputStream baos=new ByteArrayOutputStream();


                while(true){
                    try{
                        i=(int)ois.readObject();
                        //System.out.println(i);
                        if(i==-1) break;
                        baos.write(i);
                    } catch (EOFException e){
                        break;
                    }

                }

                byte imageOutputBytes[]=baos.toByteArray();
                System.out.println(imageBytes.length+" "+imageOutputBytes.length);
                imageResult=BitmapFactory.decodeByteArray(imageOutputBytes,0,imageOutputBytes.length);
                baos.close();
                ois.close();
                oos.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("returnImage", String.valueOf(imageResult));
            return imageResult;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            callback.onImageResult(bitmap);

        }
    }

}
