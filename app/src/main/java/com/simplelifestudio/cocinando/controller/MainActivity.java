package com.simplelifestudio.cocinando.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.simplelifestudio.cocinando.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private ArrayList<String> itemList;
    private ArrayAdapter adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listViewItemClick();
    }

    public void init(){
        listview = findViewById(R.id.Dev_list_view);
        itemList = new ArrayList<>();
        itemList.add("Home");
        itemList.add("Busqueda Receta");
        itemList.add("Lista Receta");
        itemList.add("Login");
        itemList.add("Mercado");
        itemList.add("Perfil");
        itemList.add("Receta Detalle");
        itemList.add("Splash");
        itemList.add("Top");
        itemList.add("Video");
        adpter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,itemList);
        listview.setAdapter(adpter);
    }

    public void listViewItemClick(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: startActivity(new Intent(MainActivity.this,Home.class));
                        break;
                    case 1: startActivity(new Intent(MainActivity.this,BusquedaReceta.class));
                        break;
                    case 2: startActivity(new Intent(MainActivity.this,ListaReceta.class));
                        break;
                    case 3: startActivity(new Intent(MainActivity.this,Login.class));
                        break;
                    case 4: startActivity(new Intent(MainActivity.this,Mercado.class));
                        break;
                    case 5: startActivity(new Intent(MainActivity.this,Perfil.class));
                        break;
                    case 6: startActivity(new Intent(MainActivity.this,RecetaDetalle.class));
                        break;
                    case 7: startActivity(new Intent(MainActivity.this,Splash.class));
                        break;
                    case 8: startActivity(new Intent(MainActivity.this,Top.class));
                        break;
                    case 9: startActivity(new Intent(MainActivity.this,Video.class));
                        break;
                    default:
                        Toast.makeText(MainActivity.this,"Invalido",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}