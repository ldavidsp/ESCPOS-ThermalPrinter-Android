package com.dantsu.escposprinter.connection.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dantsu.escposprinter.exceptions.EscPosConnectionException;

import java.util.Locale;

public class BluetoothPrintersConnections extends BluetoothConnections {

  /**
   * Easy way to get the first bluetooth printer paired / connected.
   *
   * @return a EscPosPrinterCommands instance
   */
  @Nullable
  public static BluetoothConnection selectFirstPaired() {
    BluetoothPrintersConnections printers = new BluetoothPrintersConnections();
    BluetoothConnection[] bluetoothPrinters = printers.getList();

    Log.e("BluetoothPrintersConn: ", "selectFirstPaired: " + bluetoothPrinters);

    if (bluetoothPrinters != null) {
      for (BluetoothConnection printer : bluetoothPrinters) {
        try {
          return printer.connect();
        } catch (EscPosConnectionException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * Get a list of bluetooth printers.
   *
   * @return an array of EscPosPrinterCommands
   */
  @SuppressLint("MissingPermission")
  @Nullable
  public BluetoothConnection[] getList() {
    BluetoothConnection[] bluetoothDevicesList = super.getList();

    if (bluetoothDevicesList == null) {
      return null;
    }

        int i = 0;
        BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
        for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
            BluetoothDevice device = bluetoothConnection.getDevice();
            printersTmp[i++] = new BluetoothConnection(device);
        }
        BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
        System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);
        return bluetoothPrinters;
    }

}
