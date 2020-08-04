package com.cherny.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class AddItemActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;

    private Button mAddButton;

    private String mName;
    private String mPrice;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mNameEditText = findViewById(R.id.etName);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //mPrice = charSequence.toString();
                //checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mName = editable.toString();
                checkEditTextHasText();
            }
        });
        mPriceEditText = findViewById(R.id.etConsumption);
        mPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // mName = charSequence.toString();
                //checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPrice = editable.toString();
                checkEditTextHasText();
            }
        });

        mAddButton = findViewById(R.id.btnAddItem);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("name", mName).putExtra("price", mPrice));
                    finish();
                }
            }
        });
    }
    public void checkEditTextHasText() {
        mAddButton.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice));
    }

    private void checkInputs() {
        mAddButton.setEnabled(mPrice != null && !mPrice.isEmpty() && mName != null && !mName.isEmpty());
    }
}


/*@Override
            public void onClick(final View v) {

                if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("name", mName).putExtra("price", mPrice));
                    finish();
                }
            }*/


    /* @Override
    public void onClick(View v) {
        String token = getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.TOKEN_KEY, "");

        Disposable disposable = ((LoftApp) getApplication()).getMoneyApi().addMoney(token, mPrice, mName, "expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "Error " + throwable.getLocalizedMessage());
                    }
                });

        compositeDisposable.add(disposable);
    }
*/
