package com.example.persistent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=findViewById(R.id.button);
        TextView texto=findViewById(R.id.editText);
        try {
            OutputStream os= openFileOutput("dades.txt",MODE_PRIVATE);

            String msg= "Hola";

            os.write(msg.getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualiza();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualiza();
            }
        });
        EditText editText=(EditText) texto ;
        editText.addTextChangedListener(new TextChangedListener<EditText>(editText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                escribe();
            }
        });




    }

    private void escribe(){
        try {
            OutputStream os= openFileOutput("dades.txt",MODE_PRIVATE);
            TextView texto=findViewById(R.id.editText);
            String msg= String.valueOf(texto.getText());

            os.write(msg.getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualiza(){
        try {
            TextView textito=findViewById(R.id.textView);
            textito.setText("");
            File path = getFilesDir();

            File file = new File(path,"dades.txt");
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            textito.setText(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }
}