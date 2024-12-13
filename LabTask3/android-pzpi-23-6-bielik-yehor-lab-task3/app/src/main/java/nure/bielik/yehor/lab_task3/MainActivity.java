package nure.bielik.yehor.lab_task3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private double value = 0;
    private double storedValue = 0;
    private String currentOperation = "";
    private TextView calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        calculation = findViewById(R.id.calculation);
        Button clear = findViewById(R.id.clear);
        Button changeSign = findViewById(R.id.changeSign);
        Button backspace = findViewById(R.id.backspace);
        Button division = findViewById(R.id.division);
        Button seven = findViewById(R.id.seven);
        Button eight = findViewById(R.id.eight);
        Button nine = findViewById(R.id.nine);
        Button multiplication = findViewById(R.id.multiplication);
        Button four = findViewById(R.id.four);
        Button five = findViewById(R.id.five);
        Button six = findViewById(R.id.six);
        Button minus = findViewById(R.id.minus);
        Button one = findViewById(R.id.one);
        Button two = findViewById(R.id.two);
        Button three = findViewById(R.id.three);
        Button plus = findViewById(R.id.plus);
        Button zero = findViewById(R.id.zero);
        Button equals = findViewById(R.id.equals);

        List<Button> numbersButtonsList = Arrays.asList(
                nine,
                eight,
                seven,
                six,
                five,
                four,
                three,
                two,
                one,
                zero
        );

        for (Button numButton : numbersButtonsList) {
            numButton.setOnClickListener(view -> {
                int number = Integer.parseInt(numButton.getText().toString());
                value = value * 10 + number;
                updateCalculation();
            });
        }

        clear.setOnClickListener(view -> {
            value = 0;
            storedValue = 0;
            currentOperation = "";
            updateCalculation();
        });

        backspace.setOnClickListener(view -> {
            String valueString = calculation.getText().toString();
            if (!valueString.isEmpty()) {
                valueString = valueString.substring(0, valueString.length() - 1);
            }

            if (valueString.equals(".")) {
                valueString = "";
            }

            value = valueString.isEmpty() ? 0 : Double.parseDouble(valueString);
            updateCalculation();
        });



        changeSign.setOnClickListener(view -> {
            value = -value;
            updateCalculation();
        });

        plus.setOnClickListener(view -> setOperation("+"));
        minus.setOnClickListener(view -> setOperation("-"));
        multiplication.setOnClickListener(view -> setOperation("*"));
        division.setOnClickListener(view -> setOperation("/"));

        equals.setOnClickListener(view -> {
            if (!currentOperation.isEmpty()) {
                switch (currentOperation) {
                    case "+":
                        value = storedValue + value;
                        break;
                    case "-":
                        value = storedValue - value;
                        break;
                    case "*":
                        value = storedValue * value;
                        break;
                    case "/":
                        if (value != 0) {
                            value = storedValue / value;
                        } else {
                            calculation.setText("Infinity");
                            resetCalculator();
                            return;
                        }
                        break;
                }
                currentOperation = "";
                storedValue = 0;
                updateCalculation();
            }
        });
    }

    private void setOperation(String operation) {
        if (currentOperation.isEmpty()) {
            storedValue = value;
            value = 0;
        }
        currentOperation = operation;
        updateCalculation();
    }

    private void updateCalculation() {
        if (value == (long) value) {
            calculation.setText(String.valueOf((long) value));
        } else {
            calculation.setText(String.valueOf(value));
        }
    }

    private void resetCalculator() {
        value = 0;
        storedValue = 0;
        currentOperation = "";
    }

}
