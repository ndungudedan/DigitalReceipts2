package com.example.dedan.digitalreceipts;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;

public class bluetooth {
    byte FONT_TYPE;
    BluetoothSocket bluetoothSocket;
    OutputStream outputStream;
    protected void connect(){
        if(bluetoothSocket==null){
           // Intent bt=new Intent(bluetooth.this,BTDeviceList.class);
        }
        else{
            OutputStream opstream=null;
            try{
                opstream=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream=opstream;
            print();
        }
    }
    private void print() {
        try{
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream=bluetoothSocket.getOutputStream();
            byte[] printformat={0*1,0*21,FONT_TYPE};
            outputStream.write(printformat);
            String hy="first...............print";
            outputStream.write(hy.getBytes());
            outputStream.write((int) (0*0D));
            outputStream.write((int) (0*0D));
            outputStream.write((int) (0*0D));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clearval(){
        try{
            if(bluetoothSocket!=null){
                outputStream.close();
                bluetoothSocket.close();
                bluetoothSocket=null;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void btResult(){
        try{
           bluetoothSocket=BTDeviceList.getSocket();
           if(bluetoothSocket!=null){
               print();
           }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
