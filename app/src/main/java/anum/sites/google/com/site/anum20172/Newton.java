package anum.sites.google.com.site.anum20172;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

public class Newton extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTable();
            }});
    }

    protected void createTable() {
        final TextView newtonResultView = (TextView)findViewById(R.id.newton_result);

        final EditText functionEdit = (EditText)findViewById(R.id.textfunction);
        final EditText functionPEdit = (EditText)findViewById(R.id.textfunctionP);
        final EditText xnEdit = (EditText)findViewById(R.id.xnnumberNewton);
        final EditText tolEdit = (EditText)findViewById(R.id.tolnumberNewton);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberNewton);

        String fn = functionEdit.getText().toString();
        String fnp = functionPEdit.getText().toString();
        Double xn = Double.parseDouble(xnEdit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());

        DecimalFormat df = new DecimalFormat( "#########0.00E00" );

        // Creating function analyzer
        try {
            Expression f = new ExpressionBuilder(fn)
                    .variables("x").build();

            Expression fp = new ExpressionBuilder(fnp)
                    .variables("x").build();

            // Creating the table to input the results from the method
            TableLayout table = (TableLayout)findViewById(R.id.newton_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView titerView = new TextView(this);
            TextView txnView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView tfxpView = new TextView(this);
            TextView terrorView = new TextView(this);

            titerView.setBackgroundResource(R.drawable.table_border_title);
            txnView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            tfxpView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            titerView.setText(" Iter ");
            txnView.setText(" xn ");
            tfxView.setText(" f(xn) ");
            tfxpView.setText(" f'(xn) ");
            terrorView.setText(" Error ");

            ttr.addView(titerView);
            ttr.addView(txnView);
            ttr.addView(tfxView);
            ttr.addView(tfxpView);
            ttr.addView(terrorView);

            table.addView(ttr,0);

            if (iter > 0) {
                double fx = f.setVariable("x", xn).evaluate();
                double fxp = fp.setVariable("x", xn).evaluate();
                int cont = 1;
                double error = tol + 1.0f;
                double xna = xn;
                while ((fx != 0) && (fxp != 0) && (error > tol) && (cont < iter)) {

                    TableRow tr = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(lp);

                    TextView iterView = new TextView(this);
                    TextView xnView = new TextView(this);
                    TextView fxView = new TextView(this);
                    TextView fxpView = new TextView(this);
                    TextView errorView = new TextView(this);

                    iterView.setBackgroundResource(R.drawable.table_border);
                    xnView.setBackgroundResource(R.drawable.table_border);
                    fxView.setBackgroundResource(R.drawable.table_border);
                    fxpView.setBackgroundResource(R.drawable.table_border);
                    errorView.setBackgroundResource(R.drawable.table_border);

                    iterView.setText(" " + String.valueOf(cont) + " ");
                    xnView.setText(" " + String.valueOf(xn) + " ");
                    fxView.setText(" " + String.valueOf(df.format(fx)) + " ");
                    fxpView.setText(" " + String.valueOf(fxp) + " ");
                    errorView.setText(" " + String.valueOf(df.format(error)) + " ");

                    tr.addView(iterView);
                    tr.addView(xnView);
                    tr.addView(fxView);
                    tr.addView(fxpView);
                    tr.addView(errorView);

                    table.addView(tr,cont);

                    xna = xn;
                    xn = xn - (fx / fxp);
                    fx = f.setVariable("x", xn).evaluate();
                    fxp = fp.setVariable("x", xn).evaluate();
                    error = Math.abs(xn - xna);
                    cont++;
                }
                if (fx == 0) {
                    newtonResultView.setText("Root in Xn = " + xn);
                    return;
                }
                if (error < tol) {
                    newtonResultView.setText("In Xn = " + xn + " is an approximation to a root with an error of " + error);
                    return;
                }
                if (fxp == 0) {
                    newtonResultView.setText("The derivative equals 0");
                    return;
                }
                newtonResultView.setText("Surpassed number of iterations.");
                return;
            }
            newtonResultView.setText("Number of iterations must be higher than 0.");
            return;
        } catch (RuntimeException e) {
            newtonResultView.setText(e.getMessage());
        }
    }
}
