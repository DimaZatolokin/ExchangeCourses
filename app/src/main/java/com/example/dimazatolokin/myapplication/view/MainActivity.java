package com.example.dimazatolokin.myapplication.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dimazatolokin.myapplication.ExchangeRatesApp;
import com.example.dimazatolokin.myapplication.R;
import com.example.dimazatolokin.myapplication.presenter.BasePresenter;
import com.example.dimazatolokin.myapplication.presenter.PresenterManager;
import com.example.dimazatolokin.myapplication.view.adapter.HistoryAdapter;

public class MainActivity extends AppCompatActivity implements BaseView {

    private static final String SHOW_HISTORY = "show_history";

    private Spinner currencyFirst;
    private Spinner currencySecond;
    private LinearLayout layoutValues;
    private TextView txtFirstCurrency;
    private TextView txtSecondCurrency;
    private TextView txtSecondValue;
    private TextView txtSpinnerOne;
    private TextView txtSpinnerTwo;
    private Button btnHistory;
    private RecyclerView recyclerView;

    private BasePresenter presenter;
    private PresenterManager presenterManager;
    private ProgressDialog progressDialog;
    private boolean isShowHistory = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenterManager = ((ExchangeRatesApp) getApplication()).getPresenterManager();

        presenter = presenterManager.getPresenter(this);

        currencyFirst = (Spinner) findViewById(R.id.spinner_first_currency);
        currencySecond = (Spinner) findViewById(R.id.spinner_second_currency);
        layoutValues = (LinearLayout) findViewById(R.id.layout_value);
        txtFirstCurrency = (TextView) findViewById(R.id.txt_currency_first);
        txtSecondCurrency = (TextView) findViewById(R.id.txt_currency_second);
        txtSecondValue = (TextView) findViewById(R.id.txt_currency_second_value);
        txtSpinnerOne = (TextView) findViewById(R.id.txt_spinner_one);
        txtSpinnerTwo = (TextView) findViewById(R.id.txt_spinner_two);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        btnHistory = (Button) findViewById(R.id.btn_history);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (savedInstanceState != null) {
            isShowHistory = savedInstanceState.getBoolean(SHOW_HISTORY);
            if (isShowHistory) {
                showHistory();
            } else {
                hideHistory();
            }
        }

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowHistory) {
                    hideHistory();
                } else {
                    showHistory();
                }
            }
        });

        txtSpinnerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpinnerOne.setVisibility(View.GONE);
                currencyFirst.callOnClick();
            }
        });

        txtSpinnerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpinnerTwo.setVisibility(View.GONE);
                currencySecond.callOnClick();
            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, presenter.getCurrencyNames());
        currencyFirst.setAdapter(adapter);
        currencySecond.setAdapter(adapter);

        if (savedInstanceState != null) {
            if (presenter.getFirstSpinnerPosition() >= 0) {
                currencyFirst.setVerticalScrollbarPosition(presenter.getFirstSpinnerPosition());
                txtSpinnerOne.setVisibility(View.GONE);
            }
            if (presenter.getSecondSpinnerPosition() >= 0) {
                currencySecond.setVerticalScrollbarPosition(presenter.getSecondSpinnerPosition());
                txtSpinnerTwo.setVisibility(View.GONE);
            }
            fillValuesBlock();
        }

        findViewById(R.id.txt_between).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideHistory();
                fillValuesBlock();
            }
        });

        currencyFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setFirstSpinnerPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currencySecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setSecondSpinnerPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showHistory() {
        isShowHistory = true;
        recyclerView.setVisibility(View.VISIBLE);
        HistoryAdapter adapter = new HistoryAdapter(this, presenter.getHistory());
        recyclerView.setAdapter(adapter);
        btnHistory.setText(getString(R.string.hide_history));
    }

    private void hideHistory() {
        isShowHistory = false;
        recyclerView.setVisibility(View.GONE);
        btnHistory.setText(getString(R.string.show_history));
    }

    private void fillValuesBlock() {
        presenter.obtainCourses();
        txtFirstCurrency.setText(presenter.getCurrencyNames().get(presenter.getFirstSpinnerPosition()).toString());
        txtSecondCurrency.setText(presenter.getCurrencyNames().get(presenter.getSecondSpinnerPosition()).toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }

    @Override
    public void setCourses(String price) {
        layoutValues.setVisibility(View.VISIBLE);
        txtSecondValue.setText(price);
    }

    @Override
    public void showProgress(String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
        progressDialog = ProgressDialog.show(this, getString(R.string.app_name), message);
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog.cancel();
        }
        progressDialog = null;
    }

    @Override
    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SHOW_HISTORY, isShowHistory);
    }
}
