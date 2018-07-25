package com.example.hungdev.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;



public class CalculatorFragment extends Fragment implements View.OnClickListener{
    private TextView mTextViewDisplays;
    private Button mButtonAC, mButtonCT, mButtonPercent, mButtonDivide, mButtonMult, mButtonAdd,
            mButtonSub, mButtonEqual, mButtonZero,mButtonDot, mButtonOne, mButtonTwo, mButtonThree,
            mButtonFour, mButtonFive, mButtonSix, mButtonSeven, mButtonEight, mButtonNine;
    private String mOperand;
    private String mOperator;
    private String mResult;
    private Set<String> mNumbers;
    private Set<String> mOperators;
    private View viewFragment;
    private CalculatorFragment.OnFragmentInteractionListener mListener;

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
        mButtonCT.setOnClickListener(this);
        mButtonPercent.setOnClickListener(this);
        mButtonMult.setOnClickListener(this);
        mButtonDivide.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonSub.setOnClickListener(this);
        mButtonEqual.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);
        mButtonOne.setOnClickListener(this);
        mButtonTwo.setOnClickListener(this);
        mButtonThree.setOnClickListener(this);
        mButtonFour.setOnClickListener(this);
        mButtonFive.setOnClickListener(this);
        mButtonSix .setOnClickListener(this);
        mButtonSeven.setOnClickListener(this);
        mButtonEight.setOnClickListener(this);
        mButtonNine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        String value = clicked.getText().toString();

        if (isNumerical(value) || value.equals(".")) {
            if (!isDefaultResult(mTextViewDisplays.getText().toString())) {
                value = mTextViewDisplays.getText().toString() + value;
            }
        } else if (isOperator(value)) {
            mOperand = mTextViewDisplays.getText().toString();
            mOperator = value;
            value = "0";
        } else if (isClear(value)) {
            value = getString(R.string.result_default);
        } else if(value.equals("=")){
            double a = Double.parseDouble(mOperand), b = Double
                    .parseDouble(mTextViewDisplays.getText().toString());

            if (mOperator.equals("+")) {
                value = Double.toString(a + b);
                mListener.onFragmentInteraction(value.toString());
            }else if(mOperator.equals("-")){
                value = Double.toString(a-b);
                mListener.onFragmentInteraction(value.toString());
            }else if(mOperator.equals("x")){
                value = Double.toString(a * b);
                mListener.onFragmentInteraction(value.toString());
            }else if(mOperator.equals("/")){
                value = Double.toString(a / b);
                mListener.onFragmentInteraction(value.toString());
            }

            mOperator = null;
            mOperand = null;
        }

        mTextViewDisplays.setText(value);
    }

    private void initNumbers() {
        mNumbers = new HashSet<String>();
        for (int i = 0; i < 10; i++) {
            mNumbers.add(Integer.toString(i));
        }
    }


    private void initOperators() {
        mOperators = new HashSet<String>();
        String[] ops = { "+", "-", "x", "/" };
        for (String operator : ops) {
            mOperators.add(operator);
        }
    }

    private boolean isClear(String value) {
        return value.equals(getString(R.string.buttonClear));
    }


    private boolean isOperator(String value) {
        if (mOperators == null) {
            initOperators();
        }
        return mOperators.contains(value);
    }


    private boolean isDefaultResult(String value) {
        return value.equals(getString(R.string.result_default));
    }


    private boolean isNumerical(String value) {
        if (mNumbers == null) {
            initNumbers();
        }
        return mNumbers.contains(value);
    }

    void clearResult(){
        mTextViewDisplays.setText("0");
    }

    public static CalculatorFragment newInstance(String param) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String result);
    }
}
