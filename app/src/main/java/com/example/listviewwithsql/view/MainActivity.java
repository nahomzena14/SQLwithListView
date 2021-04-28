package com.example.listviewwithsql.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.listviewwithsql.R;
import com.example.listviewwithsql.databinding.ActivityMainBinding;
import com.example.listviewwithsql.model.User;
import com.example.listviewwithsql.model.db.UserDatabaseHelper;
import com.example.listviewwithsql.util.Position;
import com.example.listviewwithsql.view.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.listviewwithsql.model.db.UserDatabaseHelper.ID_COLUMN;
import static com.example.listviewwithsql.model.db.UserDatabaseHelper.NAME_COLUMN;
import static com.example.listviewwithsql.model.db.UserDatabaseHelper.POSITION_COLUMN;
import static com.example.listviewwithsql.util.Logger.logMessage;


public class MainActivity extends AppCompatActivity implements UserAdapter.UserDelegate, AdapterView.OnItemSelectedListener {

    private SharedPreferences sharedPreferences;

    private UserDatabaseHelper dbHelper;

    private ActivityMainBinding binding;

    UserAdapter adapter = new UserAdapter(new ArrayList<>(),this);


    private List<String> options = new ArrayList<String>(Arrays.asList("ANDROID_DEVELOPER", "IOS_DEVELOPER", "MANAGER"));
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapterSpinner=  new ArrayAdapter<String>(this, R.layout.spinner_item_layout,R.id.item_text,  options);
        binding.spinner.setAdapter(adapterSpinner);
        binding.spinner.setOnItemSelectedListener(this);
        dbHelper = new UserDatabaseHelper(this);

        readDB();
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        binding.outputListview.setAdapter(adapter);

        if (sharedPreferences.getBoolean("FIRST_TIME", true))
            showWelcomeDialog();

        binding.addUserButton.setOnClickListener(view -> {

            if(checkInput()) {
                User newUser = new User(binding.usernameEdittext.getText().toString().trim(), Position.valueOf(options.get(id)));
                dbHelper.insertUser(newUser);
                readDB();
                binding.usernameEdittext.setText("");
            }
        });


    }

    private boolean checkInput() {

        if (binding.usernameEdittext.getText().toString().trim().isEmpty()){
            toastMessage("Name cannot be empty.");
            return false;
        } else if(binding.usernameEdittext.getText().toString().trim().length() < 4){
            toastMessage("Name should be at least 3 characters.");
            return false;
        }
        return true;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void readDB() {

        Cursor dbC = dbHelper.getAllUsers();
        dbC.moveToPosition(-1);
        List<User> users = new ArrayList<>();

        StringBuilder sBuilder = new StringBuilder();

        while(dbC.moveToNext()){
            String name = dbC.getString(dbC.getColumnIndex(NAME_COLUMN));
            int id = dbC.getInt(dbC.getColumnIndex(ID_COLUMN));
            String posName = dbC.getString(dbC.getColumnIndex(POSITION_COLUMN));
            User user = new User(name, id, Position.valueOf(posName));
            users.add(user);
            sBuilder.append(user.getName()).append(" : ").append(user.getPosition().name()).append("\n");
        }

        dbC.close();
        displayUsers(users);

    }

    private void displayUsers(List<User> users) {

        for(int i = 0; i < users.size(); i++)
            logMessage(users.get(i).toString());

        adapter.updateList(users);

    }

    private void showWelcomeDialog() {

        sharedPreferences.edit().putBoolean("FIRST_TIME", false).apply();
        new AlertDialog
                .Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat))
                .setMessage("Welcome to the application")
                .setNeutralButton("Thanks", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        toastMessage(options.get(i) + ", "+l);
        id = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        toastMessage("Please make a selection!");
    }

    @Override
    public void selectUser(User user) {
        Intent userIntent = new Intent(this,DetailsActivity.class);
        userIntent.putExtra("user",user);
        startActivity(userIntent);
    }
}