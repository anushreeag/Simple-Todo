package com.cosmic.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
    public static final String ITEM_NAME = "Item";

    EditText etitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemValue = getIntent().getExtras().get(ITEM_NAME).toString();
        etitem = (EditText) findViewById(R.id.etitem);
        etitem.setText(itemValue);

    }

    public void finishFunc(View v){
        Intent data = new Intent();
        data.putExtra(ITEM_NAME,etitem.getText().toString());
        setResult(Activity.RESULT_OK,data);
        finish();
    }
}
