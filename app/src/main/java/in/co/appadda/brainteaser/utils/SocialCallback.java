package in.co.appadda.brainteaser.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

public abstract class SocialCallback<T> extends BackendlessCallback<T>
{
  private Context context;

  public SocialCallback( Context context )
  {
    this.context = context;
  }

  @Override
  public void handleFault( BackendlessFault fault )
  {
    Toast.makeText( context, fault.getMessage(), Toast.LENGTH_LONG ).show();
  }
}