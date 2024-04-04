package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    TextView workingview;
    TextView resultview;

    String workings = "";
    String formula = "";
    String tempformula = "";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();

    }

    private void initTextViews() {
        workingview = (TextView)findViewById(R.id.workingview);
        resultview = (TextView)findViewById(R.id.resultview);
    }
    private void setWorkings(String givenValue){
        workings = workings + givenValue;
        workingview.setText(workings);
    }
    public void equalsonClick(View view){
        Double result = null;
        ScriptEngine engine;
        engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        try {
            // Evaluate tempformula instead of formula
            result = (double) engine.eval(tempformula);
        } catch (ScriptException e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if (result != null) {
            // Check for division by zero
            if (Double.isInfinite(result) || Double.isNaN(result)) {
                Toast.makeText(this, "Division by zero or invalid operation", Toast.LENGTH_SHORT).show();
                resultview.setText("Error");
            } else {
                resultview.setText(String.valueOf(result.doubleValue()));
            }
        }
    }


    private void checkForPowerOf() {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i =0; i<workings.length(); i++){
            if(workings.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = workings;
        tempformula = workings;
        for(Integer index: indexOfPowers){
            changeFormula(index);
        }
    }

    private void changeFormula(Integer index) {

        String numberLeft = "";
        String numberRight = "";

        // Extract the right operand of the power operation
        for (int i = index + 1; i < workings.length(); i++) {
            if (isNumeric(workings.charAt(i)) || workings.charAt(i) == '.') {
                numberRight += workings.charAt(i);
            } else {
                break;
            }
        }

        // Extract the left operand of the power operation
        for (int i = index - 1; i >= 0; i--) {
            if (isNumeric(workings.charAt(i)) || workings.charAt(i) == '.') {
                numberLeft = workings.charAt(i) + numberLeft; // Prepend the characters to maintain order
            } else {
                break;
            }
        }

        // Construct the original power operation
        String original = numberLeft + "^" + numberRight;

        // Construct the replacement for the power operation using Math.pow
        String changed = "Math.pow(" + numberLeft + "," + numberRight + ")";

        // Replace the original power operation with the replacement in tempformula
        tempformula = tempformula.replace(original, changed);
    }


    private boolean isNumeric(char c){
        if((c<= '9' && c >= '0') || c =='.')
            return true;

        return false;

    }

    public void clearonClick(View view){
        workingview.setText("");
        workings = "";
        resultview.setText("");
        leftBracket = true;

    }

    boolean leftBracket = true;

    public void bracketsonClick(View view){

        if(leftBracket) {
            setWorkings("(");
            leftBracket = false;
        }
        else{
            setWorkings(")");
            leftBracket=true;
        }
    }

    public void powerofonClick(View view){
        setWorkings("^");

    }
    public void divisiononClick (View view){
        setWorkings("/");
        formula += "/";

    }
    public void sevenonClick(View view){
        setWorkings("7");
    }
    public void eightonClick(View view){
        setWorkings("8");
    }
    public void nineonClick(View view){
        setWorkings("9");
    }
    public void multiplicationonClick(View view){
        setWorkings("*");
    }
    public void fouronClick(View view){
        setWorkings("4");
    }
    public void fiveonclick(View view){
        setWorkings("5");
    }
    public void sixonclick(View view){
        setWorkings("6");
    }
    public void sutractonClick(View view){
        setWorkings("-");
    }
    public void oneonClick(View view){
        setWorkings("1");
    }
    public void twoonClick(View view){
        setWorkings("2");
    }
    public void threeonClick(View view){
        setWorkings("3");
    }
    public void addonClick(View view){
        setWorkings("+");
    }
    public void decimalonClick(View view){
        setWorkings(".");
    }
    public void zeroonClick(View view){
        setWorkings("0");
    }


}