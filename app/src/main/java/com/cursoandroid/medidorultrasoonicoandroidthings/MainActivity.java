package com.cursoandroid.medidorultrasoonicoandroidthings;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    private final String TAG = MainActivity.class.getCanonicalName();
    /*
    private static final String ECHO_PIN_NAME = "BCM20";
    private static final String TRIGGER_PIN_NAME = "BCM16"; //antes 21
    private static final int INTERVALO_ENTRE_LECTURAS = 3000;
    private Gpio mEcho;
    private Gpio mTrigger;
    int hazAlgo;
    */

    private void sendCommandArduino(ArduinoUart uart, String command) {
        Log.d(TAG, "Mandado a Arduino: " + command);
        uart.escribir(command);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.w(TAG, "Error en sleep()", e);
        }
        String s = uart.leer();
        Log.d(TAG, "Recibido de Arduino: " + s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());
        ArduinoUart uart = new ArduinoUart("UART0", 115200);
        sendCommandArduino(uart, "H");
        sendCommandArduino(uart, "D");

        /*
        PeripheralManager manager = PeripheralManager.getInstance();
        try {
            mEcho = manager.openGpio(ECHO_PIN_NAME);
            mEcho.setDirection(Gpio.DIRECTION_IN);
            mEcho.setEdgeTriggerType(Gpio.EDGE_BOTH);
            mEcho.setActiveType(Gpio.ACTIVE_HIGH);


            mTrigger = manager.openGpio(TRIGGER_PIN_NAME);
            mTrigger.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            llamarLeerDistancia();
        } catch (IOException e) {
            Log.e(TAG, "Error en PeripheralIO API", e);
        }
        */
    }

    /*
    private void llamarLeerDistancia() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    leerDistancia();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    llamarLeerDistancia();
                }

            }
        }, INTERVALO_ENTRE_LECTURAS);
    }

    protected void leerDistancia() throws IOException, InterruptedException {
        Log.i(TAG, "leerDistancia()");
        mTrigger.setValue(false);
        Thread.sleep(0, 2000); // 2 mseg
        mTrigger.setValue(true);
        Thread.sleep(0, 10000); //10 msec
        mTrigger.setValue(false);
        while (!mEcho.getValue()) {
            hazAlgo = 0;
        }
        long tiempoIni = System.nanoTime();
        while (mEcho.getValue()) {
            hazAlgo = 1;
        }
        long tiempoFin = System.nanoTime();
        long anchoPulso = tiempoFin - tiempoIni;
        double distancia = (anchoPulso / 1000.0) / 58.23; //cm
        Log.i(TAG, "distancia (Android Things): " + distancia + " cm");
        //return distancia;
    }
    */

}
