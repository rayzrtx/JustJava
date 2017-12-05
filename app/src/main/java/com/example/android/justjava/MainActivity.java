package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String enteredName = nameField.getText().toString();
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, enteredName);
        displayMessage(priceMessage);
    }

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order.
     *
     *@return total price
     */
    private int calculatePrice() {

        return quantity * 5;
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @param hasWhippedCream states whether the user wants whipped cream added to their coffee or not
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String enteredName) {
        String orderSummary = "Name: " + enteredName +
                "\nAdd Whipped Cream? " + hasWhippedCream +
                "\nAdd Chocolate? " + hasChocolate +
                "\nNumber of coffees: " + quantity +
                "\nTotal: $" + price +
                "\nThank You!";
        return orderSummary;
    }
}
