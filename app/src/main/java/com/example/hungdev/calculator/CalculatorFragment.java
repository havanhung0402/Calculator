package com.example.hungdev.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class CalculatorFragment extends Fragment implements View.OnClickListener {
    private TextView mTextViewDisplays;
    private Button mButtonAC, mButtonCT, mButtonPercent, mButtonDivide, mButtonMult, mButtonAdd,
            mButtonSub, mButtonEqual, mButtonZero, mButtonDot, mButtonOne, mButtonTwo, mButtonThree,
            mButtonFour, mButtonFive, mButtonSix, mButtonSeven, mButtonEight, mButtonNine;
    private ArrayList<String> mStringArr = new ArrayList<>();
    private View viewFragment;
    private CalculatorFragment.OnFragmentInteractionListener mListener;
    private String txt = "";
    private double mResult = 0;
    private String temp;

    public CalculatorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.fragment_calculator, container, false);
        findViewByID();
        mTextViewDisplays.setText(PreferenceSingleton.getSharedPreferences(getActivity()).getString(getString(R.string.preference_object_key), "0"));
        handleClick();
        return viewFragment;
    }

    private void findViewByID() {
        mTextViewDisplays = viewFragment.findViewById(R.id.txtDisplays);
        mButtonAC = viewFragment.findViewById(R.id.buttonAC);
        mButtonCT = viewFragment.findViewById(R.id.buttonCT);
        mButtonPercent = viewFragment.findViewById(R.id.buttonPercent);
        mButtonMult = viewFragment.findViewById(R.id.buttonMult);
        mButtonDivide = viewFragment.findViewById(R.id.buttonDivide);
        mButtonAdd = viewFragment.findViewById(R.id.buttonAdd);
        mButtonSub = viewFragment.findViewById(R.id.buttonSub);
        mButtonEqual = viewFragment.findViewById(R.id.buttonEqual);
        mButtonDot = viewFragment.findViewById(R.id.buttonDot);
        mButtonZero = viewFragment.findViewById(R.id.buttonZero);
        mButtonOne = viewFragment.findViewById(R.id.buttonOne);
        mButtonTwo = viewFragment.findViewById(R.id.buttonTwo);
        mButtonThree = viewFragment.findViewById(R.id.buttonThree);
        mButtonFour = viewFragment.findViewById(R.id.buttonFour);
        mButtonFive = viewFragment.findViewById(R.id.buttonFive);
        mButtonSix = viewFragment.findViewById(R.id.buttonSix);
        mButtonSeven = viewFragment.findViewById(R.id.buttonSeven);
        mButtonEight = viewFragment.findViewById(R.id.buttonEight);
        mButtonNine = viewFragment.findViewById(R.id.buttonNine);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CalculatorFragment.OnFragmentInteractionListener) {
            mListener = (CalculatorFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void handleClick() {
        mButtonAC.setOnClickListener(this);
        //mButtonCT.setOnClickListener(this);
        //mButtonPercent.setOnClickListener(this);
        mButtonMult.setOnClickListener(this);
        mButtonDivide.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonSub.setOnClickListener(this);
        mButtonEqual.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);
        mButtonZero.setOnClickListener(this);
        mButtonOne.setOnClickListener(this);
        mButtonTwo.setOnClickListener(this);
        mButtonThree.setOnClickListener(this);
        mButtonFour.setOnClickListener(this);
        mButtonFive.setOnClickListener(this);
        mButtonSix.setOnClickListener(this);
        mButtonSeven.setOnClickListener(this);
        mButtonEight.setOnClickListener(this);
        mButtonNine.setOnClickListener(this);
    }

    private ArrayList<String> toStringArray(String mInfix) {

        ArrayList arr = new ArrayList();
        String mElement = "";
        System.out.println(mInfix.length());
        for (Character character : mInfix.toCharArray()) {
            System.out.println(character);
            if (Character.isDigit(character) || character == '.') {
                mElement = mElement + character.toString();
            } else {
                if (!mElement.isEmpty()) {
                    arr.add(mElement);
                }
                arr.add(character.toString());
                mElement = "";
            }
        }

        if (Character.isDigit(mInfix.charAt(mInfix.length() - 1))) {
            arr.add(mElement);
        }
        System.out.println(arr);
        return arr;
    }

    private byte getPriorityLevel(String mOperator) {
        if (mOperator.equals("x") || mOperator.equals("/")) {
            return 3;
        } else if (mOperator.equals("+") || mOperator.equals("-")) {
            return 2;
        }
        return 1;
    }

    private boolean isNumber(String s) {
        ArrayList mNumbers = new ArrayList(10);
        boolean isNumber = false;
        for (int i = 0; i <= 9; i++) {
            mNumbers.add(i + "");
        }
        for(Character c : s.toCharArray()){
            if(mNumbers.contains(c.toString()) || c == '.'){
                isNumber = true;
            }else {
                isNumber = false;
            }
        }
        return isNumber;
    }

    private boolean isOperator(String s) {
        String[] operator = {"x", "/", "+", "-"};
        for (int i = 0; i < operator.length; i++) {
            if (s.equals(operator[i])) {
                return true;
            }
        }
        return false;
    }

    Stack infixToPostfix(ArrayList<String> exPression) {
        Stack<String> mOperators = new Stack();
        Stack<String> mPostfix = new Stack();
        for (String str : exPression) {
            System.out.println(str);
            if (isNumber(str)) {
                mPostfix.push(str);
                System.out.println("Postfix: " + mPostfix);
            } else if ((str.equals("+")) || (str.equals("-")) || (str.equals("x")) || (str.equals("/"))) {
                if (mOperators.isEmpty()) {
                    mOperators.push(str);
                } else {
                    while (!mOperators.isEmpty() && (getPriorityLevel(str) <= getPriorityLevel((String) mOperators.peek()))) {
                        mPostfix.push(mOperators.peek());
                        mOperators.pop();
                    }
                    mOperators.push(str);
                }
            } else if (str.equals("(")) {
                mOperators.push(str);
            } else if (str.equals(")")) {
                while (!mOperators.isEmpty()) {
                    System.out.println(mOperators);
                    if (mOperators.peek().equals("(")) {
                        mOperators.pop();
                        break;
                    } else {
                        mPostfix.push(mOperators.peek());
                        mOperators.pop();
                    }
                }
            }
        }
        while (!mOperators.isEmpty()) {
            mPostfix.push(mOperators.peek());
            mOperators.pop();
        }
        System.out.println("Postfix: " + mPostfix);
        System.out.println("Operators: " + mOperators);

        return mPostfix;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (v.getId()) {
            case R.id.buttonEqual:
                String mExpresstion = mTextViewDisplays.getText().toString();
                getResult(infixToPostfix(toStringArray(mExpresstion)));
                mResult = 0;
                break;
            case R.id.buttonAC:
                clearResult();
                break;
            default:
                txt = txt + button.getText().toString();
                mTextViewDisplays.setText(txt);
                break;
        }
    }

    private void getResult(Stack<String> stack) {
        for (int i = 0; i < stack.size(); i++) {
            if (isOperator(stack.get(i))) {
                mResult = calculate(stack.get(i - 2), stack.get(i - 1), stack.get(i));

                stack.add(i + 1, mResult + "");
                stack.remove(i);
                stack.remove(i - 1);
                stack.remove(i - 2);
                i = 0;
            }
        }
        temp = mResult+"";
        mTextViewDisplays.setText(mResult + "");
    }

    private double calculate(String s1, String s2, String operator) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        System.out.print(queue.peek());
        switch (operator) {
            case "+":
                return Double.parseDouble(s1) + Double.parseDouble(s2);
            case "-":
                return Double.parseDouble(s1) - Double.parseDouble(s2);
            case "x":
                return Double.parseDouble(s1) * Double.parseDouble(s2);
            case "/":
                return Double.parseDouble(s1) / Double.parseDouble(s2);

        }
        return 0;
    }

    void clearResult() {
        mTextViewDisplays.setText("0");
        txt = "";
    }
    void saveResult(){
        PreferenceSingleton.getEditor(getActivity()).putString(PreferenceSingleton.PREFERENCES_OBJECT_KEY, temp);
        PreferenceSingleton.getEditor(getActivity()).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String result);
    }
}
