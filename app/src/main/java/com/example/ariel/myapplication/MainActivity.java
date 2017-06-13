package com.example.ariel.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    List<String> names;
    private int i = 0;
    MyAdapter myAdaptaer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        ///datos
        names = new ArrayList<String>(){{
            add("Alejandro");
            add("Eduardo");
            add("Fernando");
            add("Tomas");
        }} ;
        //adaptador basico
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        //seteamos el adaptador con nuestro list view
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clickeado: " + names.get(position), Toast.LENGTH_LONG).show();
            }
        });


        //enlazamos con nuestro adaptador personalizado

         myAdaptaer = new MyAdapter(this,R.layout.listitem,names);
        listView.setAdapter(myAdaptaer);

        registerForContextMenu(listView);

    }

    ///inflamos menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.action_menu_bar, menu);
        return true;
    }
    ///manejaamos opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                this.names.add("Añadido n°"+(++i));
                this.myAdaptaer.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
    //inflamos el layout del contxt menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Item: "+ names.get(info.position));
        inflater.inflate(R.menu.context_menu, menu);
    }
    ///manejamos evenos click en el context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId())
        {
            case R.id.delete_item:
                names.remove(info.position);
                this.myAdaptaer.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
}
