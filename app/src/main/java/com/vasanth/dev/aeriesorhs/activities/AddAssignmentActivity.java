package com.vasanth.dev.aeriesorhs.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;

public class AddAssignmentActivity extends AppCompatActivity {
    private int class_index = 0;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Intent intent = getIntent();
        class_index = intent.getIntExtra("class_index", -1);

        dropdown = (Spinner)findViewById(R.id.categoryDropDown);
        String[] items = DataStorageAndParsing.classesAsArrayList.get(class_index).getCategories().toArray(new String[DataStorageAndParsing.classesAsArrayList.get(class_index).getCategories().size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void addAssignmentConfirm(View view) {
        String name = ((EditText)findViewById(R.id.assignmentEditTextName)).getText().toString();
        float whatYouGot = Integer.parseInt(((EditText)findViewById(R.id.assignmentWhatIGot)).getText().toString());
        float total = Float.parseFloat(((EditText)findViewById(R.id.assignmentTotal)).getText().toString());
        if(dropdown.getSelectedItem() != null)
            DataStorageAndParsing.classesAsArrayList.get(class_index).addAssignment(name, whatYouGot, total, whatYouGot/total, true, dropdown.getSelectedItem().toString());
        else
            DataStorageAndParsing.classesAsArrayList.get(class_index).addAssignment(name, whatYouGot, total, whatYouGot/total, true);
        Intent intent = new Intent(this, ClassActivity.class);
        intent.putExtra("class_index", class_index);
        this.startActivity(intent);
    }
}
