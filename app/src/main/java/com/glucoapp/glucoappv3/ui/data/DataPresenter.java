package com.glucoapp.glucoappv3.ui.data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.domain.usecases.GlucosaUseCase;
import com.glucoapp.domain.usecases.impl.GlucosaUseCaseImpl;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Data;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;
import com.glucoapp.glucoappv3.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class DataPresenter extends BasePresenter implements Data.Presenter {

    Data.View view;

    GlucosaUseCase glucosaUseCase;

    /* Objetos de hardware */
    Map<String, String> bluetoothDevicesMap = new HashMap<>();
    List<String> bluetoothDevicesList = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter;

    /* Bluetooth */
    public static Handler handler;
    public static BluetoothSocket mmSocket;
    public static ConnectedThread connectedThread;
    public static CreateConnectThread createConnectThread;

    private final static int CONNECTING_STATUS = 1;
    private final static int MESSAGE_READ = 2;

    public DataPresenter() {
        glucosaUseCase = new GlucosaUseCaseImpl(this);
    }

    /* Implementación de métodos */
    @Override
    public void setView(Data.View view) {
        this.view = view;
    }

    @Override
    public void setListBtDevices(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            view.btAdapterFailed();
        } else if (!bluetoothAdapter.isEnabled()) {
            view.btDisabled();
        } else {
            Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
            bluetoothDevicesList.add(context.getString(R.string.conecta));
            if (bluetoothDevices.size() > Constants.CERO_VALUE) {
                for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
                    /*if(bluetoothDevice.getName().equals(Constants.BLUETOOTH_NAME)){*/
                        bluetoothDevicesMap.put(bluetoothDevice.getName(), bluetoothDevice.getAddress());
                        bluetoothDevicesList.add(bluetoothDevice.getName());
                  /*  }*/
                }
                view.setListBtDevices(bluetoothDevicesList);
            } else {
                view.btNotPairedDevices();
            }
        }
    }

    @Override
    public void connectDevice(String deviceString) {
        view.showLoading();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String deviceMac = bluetoothDevicesMap.get(deviceString);
        createConnectThread = new CreateConnectThread(bluetoothAdapter, deviceMac);
        createConnectThread.start();

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case CONNECTING_STATUS:
                        switch(msg.arg1){
                            case 1:
                                view.hideLoading();
                                view.glucometerEnabled();
                                break;
                            case -1:
                                view.hideLoading();
                                view.onError("Error al conectar con dispositivo");
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino
                        Glucosa glucosa = new Gson().fromJson(arduinoMsg, Glucosa.class);
                        glucosa.setFecha(Utils.getDateTime());
                        view.saveEnabled(glucosa);
                }
            }
        };
    }

    @Override
    public void updateGlucoData() {
        glucosaUseCase.updateGlucoData();
    }

    @Override
    public void saveGlucoData(Glucosa glucosa) {
        view.showLoading();
        glucosaUseCase.saveGlucoData(glucosa);
    }

    @Override
    public void onSuccessUpdateGlucoData(Glucosa glucosa){
        view.saveEnabled(glucosa);
    }

    @Override
    public void onSuccessSaveGlucoData() {
        view.onSuccessSaveGlucoData();
        view.hideLoading();
    }

    @Override
    public void onError(String error){
        view.onError(error);
        view.hideLoading();
    }

    /* ============================ Thread to Create Bluetooth Connection =================================== */
    public static class CreateConnectThread extends Thread {

        public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address) {

            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
            BluetoothSocket tmp = null;
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();

            try {
                tmp = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);

            } catch (IOException e) {
                Log.e(TAG, "Error al crear socket", e);
            }
            mmSocket = tmp;
        }

        public void run() {

            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
                Log.e("Status", "Dispositivo conectado");
                handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                    Log.e("Status", "No se puede conectar con dispositivo");
                    handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                } catch (IOException closeException) {
                    Log.e(TAG, "No se puede conectar el socket", closeException);
                }
                return;
            }

            connectedThread = new ConnectedThread(mmSocket);
            connectedThread.run();
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    /* =============================== Thread for Data Transfer =========================================== */
    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes = 0;
            while (true) {
                try {
                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;
                    if (buffer[bytes] == '\n'){
                        readMessage = new String(buffer,0,bytes);
                        Log.e("Arduino Message",readMessage);
                        handler.obtainMessage(MESSAGE_READ,readMessage).sendToTarget();
                        bytes = 0;
                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(String input) {
            byte[] bytes = input.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error","Unable to send message",e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


}
