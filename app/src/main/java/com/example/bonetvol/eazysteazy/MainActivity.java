package com.example.bonetvol.eazysteazy;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.renderscript.Double2;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    final double perLbSea = 1.19;
    final double perLbAir = 2.89;
    boolean airDelivery = false;
    final double doorDelivery = 20;
    double priceBlank = 14.5;
    double priceWithGraphics;
    Integer quantity = -1;
    Integer colors = 0;
    Currency Kurs = new Test();
    double kurs = Kurs.click();
    TextView eachText;
    TextView totalText;
    public static final String apishechka = "http://apilayer.net/api/live?access_key=a158456551a829bcfa8fa137341e071e&currencies=UAH&format=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting
        eachText = (TextView) this.findViewById(R.id.resultEach);
        totalText = (TextView) this.findViewById(R.id.resultTotal);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculate(View view) {
        try {
            colors = Integer.parseInt(((EditText) findViewById(R.id.colorsNumber)).getText().toString());
        } catch (Exception e) {
            colors = 0;
        }
        try {
            quantity = Integer.parseInt(((EditText) findViewById(R.id.quantity)).getText().toString());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), String.format("Please provide data!"), Toast.LENGTH_LONG).show();
        }

        //check if air delivery was chosen
        ToggleButton delivery = (ToggleButton) findViewById(R.id.toggler);
        airDelivery = delivery.isChecked() ? true : false;
        //Log.i("AIR", String.valueOf(airDelivery));


        //each color setup price
        double setUpColor = (quantity < 50) ? 35 : 30;

        //each deck price with printing or blank
        if (colors != 0) {
            priceWithGraphics = priceBlank + (setUpColor * colors) / quantity + colors * 0.5 + 0.5;
        } else {
            priceWithGraphics = priceBlank;
        }

        if (quantity != -1) {
            double deliveryCost = deliveryCalculator(quantity, airDelivery);
            double priceWithDelivery = priceWithGraphics + deliveryCost / quantity;

            double priceInUah = Double.parseDouble(new DecimalFormat("####.##").format(priceWithDelivery * kurs));
            double totalAll = Double.parseDouble(new DecimalFormat("####.##").format(priceWithDelivery * quantity));

            eachText.setText(String.valueOf(priceInUah));
            totalText.setText(String.valueOf(totalAll));
        }
    }

    public double deliveryCalculator(int quantity, boolean airDelivery) {
        int boxesTwenty;
        int boxesTen = 0;
        if (quantity % 20 == 0) {
            boxesTwenty = quantity / 20;
        } else {
            boxesTwenty = quantity / 20;
            boxesTen = 1;
        }
        if (!airDelivery) {
            return (perLbSea * 84 + doorDelivery) * boxesTwenty + (perLbSea * 42 + doorDelivery) * boxesTen;
        } else {
            return (perLbAir * 84 + doorDelivery) * boxesTwenty + (perLbAir * 42 + doorDelivery) * boxesTen;
        }
    }
}








