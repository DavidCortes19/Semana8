package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnDescargar;
    Button btnGirar;
    ImageView imagen;
    private float currentRotation = 0; // Variable para rastrear la rotación actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDescargar = findViewById(R.id.downloadButton);
        btnGirar = findViewById(R.id.rotateButton);
        imagen = findViewById(R.id.imageView);

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descargarImagen();
            }
        });

        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girarImagen(90); // Gira la imagen 90 grados al presionar el botón
            }
        });
    }

    private void descargarImagen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = loadImageFromNetwork("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZt0h5A35LHrauX4SLMDBlYbuwBclkGxrMkKf4JxYhyRfNNPi-boQwC3na3IgY4WI3AZo&usqp=CAU");
                imagen.post(new Runnable() {
                    @Override
                    public void run() {
                        imagen.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private void girarImagen(float degrees) {
        currentRotation += degrees;
        imagen.setRotation(currentRotation);
    }

    private Bitmap loadImageFromNetwork(String imageURL) {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
