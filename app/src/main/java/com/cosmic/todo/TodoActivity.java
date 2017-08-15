package com.cosmic.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends Activity {

    ListView lv ;
    EditText et;
    List<String> items = new ArrayList<String>();
    ArrayAdapter<String> todoAdp;

    public static final String TAG = "TodoActivity";
    public static final int REQUEST_CODE = 1;
    public static final String ITEM_NAME = "Item";
    int position = 0;
    StringBuilder item_latest_value =  new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lv = (ListView) findViewById(R.id.todoList);
        et = (EditText) findViewById(R.id.editTodo);
        populateList();

        todoAdp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lv.setAdapter(todoAdp);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

                items.remove(pos);
                writeItemstoFile();
                lv.setAdapter(todoAdp);
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent();
                position = pos;
                intent.setClass(getApplicationContext(),EditItemActivity.class);
                intent.putExtra(ITEM_NAME,items.get(pos));
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==Activity.RESULT_OK){
            if(requestCode==REQUEST_CODE){
                Bundle b = data.getExtras();
                item_latest_value.append(b.getString(ITEM_NAME));
                items.remove(position);
                items.add(position,item_latest_value.toString());
                writeItemstoFile();
                lv.setAdapter(todoAdp);
                item_latest_value.setLength(0);
            }
        }
    }

    public void addTodoItem(View v){
        items.add(et.getText().toString());
        todoAdp.notifyDataSetChanged();
        et.setText("");
        writeItemstoFile();
    }


    private void readItemfromFile(){
        File fileDir = getFilesDir();
        File file = new File(fileDir,"items.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(IOException e){
            Log.e(TAG,"array problem");

        }

    }

    private void writeItemstoFile() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "items.txt");
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
            Log.e(TAG, "array problem");

        }
    }


    void populateList(){
      /*  items.add("item1");
        items.add("item2");
        items.add("item3");
        items.add("item4");*/
        readItemfromFile();

    }


}
