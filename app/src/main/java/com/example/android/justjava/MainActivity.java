package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        //Figure out if user has checked the whipped cream box
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if user has checked the chocolate box
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //Save the name the user entered
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String enteredName = nameField.getText().toString();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, enteredName);
        //displayMessage(priceMessage) was used to display order summary on screen. No longer used. Saved for reference.

        //This will pull up email app with pertinent info when Order button is pressed
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject_line, enteredName));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        //To prevent the app from crashing , verifying there is an app that can handle the request
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        quantity = quantity + 1;
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.over_100_coffees_toast);
        int duration = Toast.LENGTH_SHORT;

        //If quantity is 100 or less then display the quantity, otherwise display 100 and a toast warning
        if (quantity <= 100) {
            displayQuantity(quantity);
        } else {
            quantity = 100;
            displayQuantity(quantity);
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void decrement(View view){
        quantity = quantity - 1;
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.less_than_1_coffee_toast);
        int duration = Toast.LENGTH_SHORT;

        //If quantity is at least 1 then display this quantity, otherwise display 1 and a toast warning
        if (quantity >= 1){
        displayQuantity(quantity);
    } else {
            quantity = 1;
            displayQuantity(quantity);
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen. Not used. Saved for reference
     *
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
     */

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the customer wants whipped cream
     * @param hasChocolate is whether or not the customer wants chocolate
     *@return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        //Price of one cup of coffee
        int basePrice = 5;

        //Add $1 if customer has added whipped cream
        if (hasWhippedCream){
            basePrice += 1;
        }

        //Add $2 if customer has added chocolate
        if (hasChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;
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
        String orderSummary = getString(R.string.order_summary_name, enteredName);
        orderSummary += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        orderSummary += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        orderSummary += "\n" + getString(R.string.order_summary_quantity, quantity);
        orderSummary += "\n" + getString(R.string.order_summary_price, price);
        orderSummary += "\n" + getString(R.string.thank_you); //Displaying text assigned by resource id thank_you
        return orderSummary;
    }
}
