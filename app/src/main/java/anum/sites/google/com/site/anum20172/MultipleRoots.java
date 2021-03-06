package anum.sites.google.com.site.anum20172;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

public class MultipleRoots extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_roots);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTable();
            }});
    }

    protected void createTable() {
        final TextView multipleRootsResultView = (TextView) findViewById(R.id.multiple_roots_result);
        final EditText functionEdit = (EditText)findViewById(R.id.textfunction);
        final EditText functionPEdit = (EditText)findViewById(R.id.textfunctionp);
        final EditText functionDPEdit = (EditText)findViewById(R.id.textfunctiondp);
        final EditText xnEdit = (EditText) findViewById(R.id.xnnumberMultipleRoots);
        final EditText tolEdit = (EditText) findViewById(R.id.tolnumberMultipleRoots);
        final EditText iterEdit = (EditText) findViewById(R.id.iternumberMultipleRoots);

        Double xn = Double.parseDouble(xnEdit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());
        String fn = functionEdit.getText().toString();
        String fnp = functionPEdit.getText().toString();
        String fndp = functionDPEdit.getText().toString();

        DecimalFormat df = new DecimalFormat( "#########0.00E00" );

        // Creating function analyzer
        try {
            Expression f = new ExpressionBuilder(fn)
                    .variables("x").build();

            Expression fp = new ExpressionBuilder(fnp)
                    .variables("x").build();

            Expression fdp = new ExpressionBuilder(fndp)
                    .variables("x").build();

            // Creating the table to input the results from the method
            TableLayout table = (TableLayout) findViewById(R.id.newton_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView titerView = new TextView(this);
            TextView txnView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView tfxpView = new TextView(this);
            TextView tfxdpView = new TextView(this);
            TextView terrorView = new TextView(this);

            titerView.setBackgroundResource(R.drawable.table_border_title);
            txnView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            tfxpView.setBackgroundResource(R.drawable.table_border_title);
            tfxdpView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            titerView.setText(" Iter ");
            txnView.setText(" xn ");
            tfxView.setText(" f(xn) ");
            tfxpView.setText(" f'(xn) ");
            tfxdpView.setText(" f''(xn) ");
            terrorView.setText(" Error ");

            ttr.addView(titerView);
            ttr.addView(txnView);
            ttr.addView(tfxView);
            ttr.addView(tfxpView);
            ttr.addView(tfxdpView);
            ttr.addView(terrorView);

            table.addView(ttr, 0);

            if (iter > 0) {
                double fx = f.setVariable("x", xn).evaluate();
                double fxp = fp.setVariable("x", xn).evaluate();
                double fxpp = fdp.setVariable("x", xn).evaluate();
                double xna = xn;
                int cont = 1;
                double error = tol + 1.0f;
                double den = 1.0f;

                TableRow tr0 = new TableRow(this);
                TableRow.LayoutParams lp0 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tr0.setLayoutParams(lp0);

                TextView iter0View = new TextView(this);
                TextView xn0View = new TextView(this);
                TextView fx0View = new TextView(this);
                TextView fxp0View = new TextView(this);
                TextView fxdp0View = new TextView(this);
                TextView error0View = new TextView(this);

                iter0View.setBackgroundResource(R.drawable.table_border_title);
                xn0View.setBackgroundResource(R.drawable.table_border_title);
                fx0View.setBackgroundResource(R.drawable.table_border_title);
                fxp0View.setBackgroundResource(R.drawable.table_border_title);
                fxdp0View.setBackgroundResource(R.drawable.table_border_title);
                error0View.setBackgroundResource(R.drawable.table_border_title);

                iter0View.setText(" 0 ");
                xn0View.setText(" " + xn + " ");
                fx0View.setText(" " + String.valueOf(df.format(fx ))+ " ");
                fxp0View.setText(" " + String.valueOf(df.format(fxp ))+ " ");
                fxdp0View.setText(" " + String.valueOf(df.format(fxpp ))+ " ");
                error0View.setText("  ");

                tr0.addView(iter0View);
                tr0.addView(xn0View);
                tr0.addView(fx0View);
                tr0.addView(fxp0View);
                tr0.addView(fxdp0View);
                tr0.addView(error0View);

                table.addView(tr0, cont);

                while ((fx != 0) && (error > tol) && (cont < iter) && (den != 0)) {
                    xna = xn;
                    den = (fxp * fxp - fx * fxpp);
                    xn = xn - (fx * fxp) / den;
                    fx = f.setVariable("x", xn).evaluate();
                    fxp = fp.setVariable("x", xn).evaluate();
                    fxpp = fdp.setVariable("x", xn).evaluate();
                    error = Math.abs(xn - xna);

                    TableRow tr = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    ttr.setLayoutParams(lp);

                    TextView iterView = new TextView(this);
                    TextView xnView = new TextView(this);
                    TextView fxView = new TextView(this);
                    TextView fxpView = new TextView(this);
                    TextView fxdpView = new TextView(this);
                    TextView errorView = new TextView(this);

                    iterView.setBackgroundResource(R.drawable.table_border_title);
                    xnView.setBackgroundResource(R.drawable.table_border_title);
                    fxView.setBackgroundResource(R.drawable.table_border_title);
                    fxpView.setBackgroundResource(R.drawable.table_border_title);
                    fxdpView.setBackgroundResource(R.drawable.table_border_title);
                    errorView.setBackgroundResource(R.drawable.table_border_title);

                    iterView.setText(" " + String.valueOf(cont) + " ");
                    xnView.setText(" " + String.valueOf(xn) + " ");
                    fxView.setText(" " + String.valueOf(df.format(fx)) + " ");
                    fxpView.setText(" " + String.valueOf(df.format(fxp)) + " ");
                    fxdpView.setText(" " + String.valueOf(df.format(fxpp)) + " ");
                    errorView.setText(" " + String.valueOf(df.format(error)) + " ");

                    tr.addView(titerView);
                    tr.addView(txnView);
                    tr.addView(tfxView);
                    tr.addView(tfxpView);
                    tr.addView(tfxdpView);
                    tr.addView(terrorView);

                    table.addView(tr, 0);

                    cont++;
                }
                if (fx == 0) multipleRootsResultView.setText("Root in Xn = " + xn);
                if (error < tol) {
                    multipleRootsResultView.setText("In Xn = " + xn + " is an approximation to a root with an error of " + error);
                    return;
                }
                if (den == 0) {
                    multipleRootsResultView.setText("Division by 0");
                    return;
                }
                multipleRootsResultView.setText("Surpassed number of iterations.");
                return;
            }
            multipleRootsResultView.setText("Number of iterations must be higher than 0.");

        } catch (RuntimeException e) {
            multipleRootsResultView.setText(e.getMessage());
        }
    }
}
