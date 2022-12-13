package com.fall.fallout.utils.serviceLocation;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.SensorEvent;
import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class FallDetection {

    private static final String TAG = "FallDetection";
    private final int sam = 25;
    private final int tu = 3;
    private final int fs = 50;
    private int Range;

    private int countt = 0;
    private Float Data_Array[][] = new Float[sam][3];

    private int k;
    private float landa;
    private int sig;
    private float Buffer[];
    private boolean Sensorr = false;

    private float l_x[], l_y[], l_z[], s_x[], s_y[], s_z[], mu_x[], mu_y[], mu_z[], sigma_x[], sigma_y[], sigma_z[];
    private float r1_x = 0, r1_y = 0, r1_z = 0, r2_x = 0, r2_y = 0, r2_z = 0;

    private final int a = 60;
    private float b = (float) 10.5;
    private float d = (float) .1;
    private int n = 6;
    private float Raps = 0;
    private float Rap = 0;
    private float mRaps = 0;
    private float dist = 0;
    private float dists = 0;
    private float mDists = 0;
    private float counter = 0;
    private boolean fallcheck = false;
    private Context context;

    Interpreter interpreter;

    private final MutableLiveData<Boolean> isFallDetect = new MutableLiveData<>();
    private boolean isOneTrue = false;


    public FallDetection(Context context , int rang) {
        this.context = context;

        this.Range = rang;


        //init file .tflit
        try {
            interpreter = new Interpreter(LoadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //init variables fall
        k = fs * tu;
        landa = k / sam;
        sig = (int) (landa / 2) + 1;
        Buffer = new float[12 * sig + 6];
        l_x = new float[sig];
        l_y = new float[sig];
        l_z = new float[sig];
        s_x = new float[sig];
        s_y = new float[sig];
        s_z = new float[sig];
        mu_x = new float[sig];
        mu_y = new float[sig];
        mu_z = new float[sig];
        sigma_x = new float[sig];
        sigma_y = new float[sig];
        sigma_z = new float[sig];
        sig -= 1;
        Arrays.fill(Buffer, 0);

    }





    private MappedByteBuffer LoadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("2550_flip06_alliter.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }





    public void detectFall(SensorEvent event) {

        if (countt != sam) {
            Data_Array[countt][0] = event.values[0];
            Data_Array[countt][1] = event.values[1];
            Data_Array[countt][2] = event.values[2];
            countt++;
        } else {
            String Linear = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Pdfs" + "/SVM_Linear.sav";
            String Quadric = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Pdfs" + "/SVM_Quadric.sav";

            for (int row = 0; row < Data_Array.length; row++) {
                for (int column = 0; column < Data_Array[row].length; column++) {
                    Data_Array[row][column] = (Data_Array[row][column] * 20) / Range;
                }
            }


            //sum new data


            for (int i = 0; i < Data_Array.length; i++) {
                l_x[sig] = l_x[sig] + Data_Array[i][0];
                l_y[sig] = l_y[sig] + Data_Array[i][1];
                l_z[sig] = l_z[sig] + Data_Array[i][2];

                s_x[sig] = (float) (s_x[sig] + Math.pow(Data_Array[i][0], 2));
                s_y[sig] = (float) (s_y[sig] + Math.pow(Data_Array[i][1], 2));
                s_z[sig] = (float) (s_z[sig] + Math.pow(Data_Array[i][2], 2));
            }
            float[] features = new float[12];

            //update r1 and r2
            r1_x = r1_x + l_x[sig] - l_x[0];
            r1_y = r1_y + l_y[sig] - l_y[0];
            r1_z = r1_z + l_z[sig] - l_z[0];

            r2_x = r2_x + s_x[sig] - s_x[0];
            r2_y = r2_y + s_y[sig] - s_y[0];
            r2_z = r2_z + s_z[sig] - s_z[0];
            //calculate Mu and Sigma
            mu_x[sig] = (2 * r1_x) / k;
            mu_y[sig] = (2 * r1_y) / k;
            mu_z[sig] = (2 * r1_z) / k;


            sigma_x[sig] = (float) Math.sqrt((r2_x - (2 * (r1_x * r1_x) / k)) / ((k / 2) - 1));
            sigma_y[sig] = (float) Math.sqrt((r2_y - (2 * (r1_y * r1_y) / k)) / ((k / 2) - 1));
            sigma_z[sig] = (float) Math.sqrt((r2_z - (2 * (r1_z * r1_z) / k)) / ((k / 2) - 1));


            //build feature vector
            features[0] = sigma_x[0];
            features[1] = mu_x[0];
            features[2] = sigma_x[sig];
            features[3] = mu_x[sig];

            features[4] = sigma_y[0];
            features[5] = mu_y[0];
            features[6] = sigma_y[sig];
            features[7] = mu_y[sig];

            features[8] = sigma_z[0];
            features[9] = mu_z[0];
            features[10] = sigma_z[sig];
            features[11] = mu_z[sig];


            float result[][] = new float[1][1];
            interpreter.run(features, result);

            //Log.i("Result : ", Math.round(result[0][0]) + "");

            if (Math.round(result[0][0]) == 1) { //this part of mean user fall

                //fall detect
                //Log.i(TAG, "fillSensorVariable: 1");

                //A "true" value must be returned here
                if(!isOneTrue){
                    isOneTrue = true;
                    isFallDetect.setValue(true);
                }

            } else {

                //no fall
                //Log.i(TAG, "fillSensorVariable: 0");
                isOneTrue = false;
                isFallDetect.setValue(false);
            }
            //shift buffers
            shiftArray(sigma_x);
            shiftArray(mu_x);
            shiftArray(s_x);
            shiftArray(l_x);

            shiftArray(sigma_y);
            shiftArray(mu_y);
            shiftArray(s_y);
            shiftArray(l_y);

            shiftArray(sigma_z);
            shiftArray(mu_z);
            shiftArray(s_z);
            shiftArray(l_z);

            countt = 0;
        }

    }


    public LiveData<Boolean> getIsFallDetect() {
        return isFallDetect;
    }

    public static void shiftArray(float[] input) {

        for (int i = 0; i < input.length - 1; i++) {
            input[i] = input[i + 1];
        }
        input[(input.length - 1)] = 0;

    }




}
