package example.jocelinthomas.dictionary;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import java.io.IOException;

public class LoadDataAsync extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private DBHelper dbHelper;
    private AlertDialog alertDialog;

    public LoadDataAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyDialogAlert);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.alertdialog, null);

        alertDialogBuilder.setTitle("Loading Words..").setView(dialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        dbHelper = new DBHelper(context);
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error doinbackground" +e);
            throw new Error("Database was not created");
        }
        dbHelper.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        alertDialog.dismiss();

        //open db after async task finishes copying db
        MainActivity.openDatabase();
    }
}
