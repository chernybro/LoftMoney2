package com.cherny.loftmoney;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int costId = getIntent().getIntExtra("COST_ID", 0);
        Log.e("TAG", "Cost Id = " + costId);
    }
}
   /* <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorPrimary"
        tools:context=".SplashActivity">


<ImageView
        android:id="@+id/logoView"
                android:layout_width="174dp"
                android:layout_height="153dp"
                android:layout_gravity="center"
                android:src="@drawable/ic_basic"
                app:layout_constraintEnd_toStartOf="parent"
                app:layout_constraintStart_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"/>

<androidx.appcompat.widget.AppCompatButton
        android:id="@+id/loginButtonView"
        android:layout_width="170dp"
        android:layout_height="48dp"
        android:text="@string/welcome_login"
        android:fontFamily="sans-serif-medium"
        android:textColor="@color/colorPrimaryDark"
        android:background="@drawable/default_button_background"
        app:layout_constraintTop_toBottomOf="@id/logoView"
        app:layout_constraintEnd_toStartOf="parent"
        app:layout_constraintStart_toEndOf="parent"
        android:layout_marginTop="37dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
*/