package com.example.izabe.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceHandler extends AsyncTask<String, Void, String> {

    // okienko dialogowe, które każe użytkownikowi czekać
    private ProgressDialog dialog = new ProgressDialog(MainActivity.context);

    // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
    // mamy w niej dostęp do elementów UI
    @Override
    protected void onPreExecute() {
        // wyświetlamy okienko dialogowe każące czekać
        dialog.setMessage("Czekaj...");
        dialog.show();
    }

    // główna operacja, która wykona się w osobnym wątku
    // nie ma w niej dostępu do elementów UI
    @Override
    protected String doInBackground(String... urls) {

        try {
            Toast.makeText(MainActivity.context,"jestem w doInBackground",Toast.LENGTH_SHORT).show();
            // zakładamy, że jest tylko jeden URL
            URL url = new URL("https://postman-echo.com");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);

            // zezwolenie na wysyłanie danych
            connection.setDoOutput(true);
            // ustawienie typu wysyłanych danych
            connection.setRequestProperty("Content-Type",
                    "application/json");
            // ustawienie metody
            connection.setRequestMethod("GET");

            // stworzenie obiektu do wysłania
            JSONObject data = new JSONObject();
            //data.put("name", ((EditText) findViewById(R.id.req_name)).getText().toString());
            data.put("foo1", "bar1");
            data.put("foo2", "bar2");

            // wysłanie obiektu
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream(),
                            "UTF-8"));
            writer.write(data.toString());
            writer.close();

            //////////////////////////////////////////
            // na tym etapie obiekt został wysłany
            // i dostaliśmy odpowiedź serwera
            //////////////////////////////////////////

            // sprawdzenie kodu odpowiedzi, 200 = OK
            if (connection.getResponseCode() != 200) {
                throw new Exception("Bad Request");
            }

            if(connection.getResponseCode() == 200)
                Toast.makeText(MainActivity.context,"wysłano",Toast.LENGTH_SHORT).show();

            // pobranie odpowiedzi serwera
            InputStream in = new BufferedInputStream(
                    connection.getInputStream());

            // konwersja InputStream na String
            // wynik będzie przekazany do metody onPostExecute()
            return streamToString(in);

        } catch (Exception e) {
            // obsłuż wyjątek
            Log.d("wyjatek", e.toString());
            return null;
        }

    }

    // metoda wykonuje się po zakończeniu metody głównej,
    // której wynik będzie przekazany;
    // w tej metodzie mamy dostęp do UI
    @Override
    protected void onPostExecute(String result) {

        // chowamy okno dialogowe
        dialog.dismiss();

        try {
            // reprezentacja obiektu JSON w Javie
            JSONObject json = new JSONObject(result);

            // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
            ((TextView)MainActivity.view.findViewById(R.id.response_id)).setText("id: "
                    + json.optString("id"));
            ((TextView) MainActivity.view.findViewById(R.id.response_name)).setText("name: "
                    + json.optString("name"));

        } catch (Exception e) {
            // obsłuż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }
    }



    // konwersja z InputStream do String
    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

        } catch (IOException e) {
            // obsłuż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }

        return stringBuilder.toString();
    }
}