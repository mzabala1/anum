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

public class Bisection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bisection);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTable();
            }});
    }

    protected void createTable() {
        final TextView bisectionResultView = (TextView)findViewById(R.id.bisection_result);

        final EditText functionEdit = (EditText)findViewById(R.id.textfunction);
        final EditText xiEdit = (EditText)findViewById(R.id.xinumberBisec);
        final EditText xsEdit = (EditText)findViewById(R.id.xsnumberBisec);
        final EditText tolEdit = (EditText)findViewById(R.id.tolnumberBisec);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberBisec);

        String fn = functionEdit.getText().toString();
        Double xi = Double.parseDouble(xiEdit.getText().toString());
        Double xs = Double.parseDouble(xsEdit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());


        DecimalFormat df = new DecimalFormat( "#########0.00E00" );

        // Creating function analyzer
        try {
            Expression e = new ExpressionBuilder(fn)
                    .variables("x").build();

            // Creating the table to input the results from the method
            TableLayout table = (TableLayout)findViewById(R.id.bisection_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView titerView = new TextView(this);
            TextView txiView = new TextView(this);
            TextView txsView = new TextView(this);
            TextView txmView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView terrorView = new TextView(this);

            titerView.setBackgroundResource(R.drawable.table_border_title);
            txiView.setBackgroundResource(R.drawable.table_border_title);
            txsView.setBackgroundResource(R.drawable.table_border_title);
            txmView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            titerView.setText(" Iter ");
            txiView.setText(" Xi ");
            txsView.setText(" Xs ");
            txmView.setText(" Xm ");
            tfxView.setText(" f(xm) ");
            terrorView.setText(" Error ");

            ttr.addView(titerView);
            ttr.addView(txiView);
            ttr.addView(txsView);
            ttr.addView(txmView);
            ttr.addView(tfxView);
            ttr.addView(terrorView);

            table.addView(ttr,0);

            double err = 5.0f + tol;
            int cnt;
            double yi = e.setVariable("x", xi).evaluate();
            double ys = e.setVariable("x", xs).evaluate();
            if ((yi * ys) < 0) {
                if (iter > 0) {
                    double xm = (xi + xs) / 2;
                    double ym = e.setVariable("x", xm).evaluate();
                    double erra = xm;
                    cnt = 1;
                    while ((ym != 0) && (err > tol) && (cnt < iter)) {
                        TableRow tr = new TableRow(this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        tr.setLayoutParams(lp);
                        TextView iterView = new TextView(this);
                        TextView xiView = new TextView(this);
                        TextView xsView = new TextView(this);
                        TextView xmView = new TextView(this);
                        TextView fxView = new TextView(this);
                        TextView errorView = new TextView(this);

                        iterView.setBackgroundResource(R.drawable.table_border);
                        xiView.setBackgroundResource(R.drawable.table_border);
                        xsView.setBackgroundResource(R.drawable.table_border);
                        xmView.setBackgroundResource(R.drawable.table_border);
                        fxView.setBackgroundResource(R.drawable.table_border);
                        errorView.setBackgroundResource(R.drawable.table_border);

                        iterView.setText(" " + String.valueOf(cnt) + " ");
                        xiView.setText(" " + String.valueOf(xi) + " ");
                        xsView.setText(" " + String.valueOf(xs) + " ");
                        xmView.setText(" " + String.valueOf(xm) + " ");
                        fxView.setText(" " + String.valueOf(df.format(ym)).toString() + " ");
                        errorView.setText(" " + String.valueOf(df.format(err)).toString() + " ");

                        tr.addView(iterView);
                        tr.addView(xiView);
                        tr.addView(xsView);
                        tr.addView(xmView);
                        tr.addView(fxView);
                        tr.addView(errorView);

                        table.addView(tr,cnt);

                        if ((yi * ym) < 0) {
                            xs = xm;
                            ys = ym;
                        } else {
                            xi = xm;
                            yi = ym;
                        }
                        xm = (xi + xs) / 2;
                        ym = e.setVariable("x", xm).evaluate();
                        err = Math.abs(xm - erra);
                        erra = xm;
                        cnt++;
                    }
                    if (ym == 0) {
                        bisectionResultView.setText("Root in Xn" + xm);
                        return;
                    }
                    if (err < tol) {
                        bisectionResultView.setText("In Xm = " + xm + " is an approximation to a root with an error of " + err);
                        return;
                    }
                    bisectionResultView.setText("Surpassed the number of iterations");
                    return;
                } else {
                    bisectionResultView.setText("Number of iterations must be higher than 0.");
                }
            }
            if (yi == 0){
                bisectionResultView.setText("There's a root in Xi = " + xi);
                return;
            }
            if (ys == 0){
                bisectionResultView.setText("There's a root in Xs" + xs);
                return;
            }
            TextView res = new TextView(this);
            res.setText("Invalid interval inputted");
            table.addView(res);
        } catch (RuntimeException e) {
            bisectionResultView.setText(e.getMessage());
        }
    }
}
