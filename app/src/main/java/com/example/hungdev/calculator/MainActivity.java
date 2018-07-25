package com.example.hungdev.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements CalculatorFragment.OnFragmentInteractionListener{
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private CalculatorFragment calculatorFragment;
    private String mResultSave;
    public static SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = PreferenceSingleton.getSharedPreferences(getApplication());
        editor = PreferenceSingleton.getEditor(getApplication());
        loadFragment();
    }

    private void loadFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        calculatorFragment = new CalculatorFragment();
        fragmentTransaction.add(R.id.fragment, calculatorFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuClear:
                Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show();
                CalculatorFragment calculatorFragment = (CalculatorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                calculatorFragment.clearResult();
                return true;
            case R.id.menuSave:
                PreferenceSingleton.getEditor(getApplication()).putString(PreferenceSingleton.PREFERENCES_OBJECT_KEY, mResultSave);
                PreferenceSingleton.getEditor(getApplication()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFragmentInteraction(String result) {
        mResultSave = result;
    }
}
