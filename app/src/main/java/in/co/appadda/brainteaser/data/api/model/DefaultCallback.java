package in.co.appadda.brainteaser.data.api.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import in.co.appadda.brainteaser.activity.MainActivity;

public class DefaultCallback<T> extends BackendlessCallback<T> {
    private Context context;
    private ProgressDialog progressDialog;

    public DefaultCallback(Context context) {
        this.context = context;
        progressDialog = ProgressDialog.show(context, "", "Loading...", true);
    }

    @Override
    public void handleResponse(T response) {
        progressDialog.cancel();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        progressDialog.cancel();
        Toast.makeText(context, "Network Problem", Toast.LENGTH_LONG).show();
    }
}