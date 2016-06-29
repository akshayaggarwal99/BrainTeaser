package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.backendless.Backendless;
import com.backendless.exceptions.BackendlessFault;

import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginSuccessActivity extends AppCompatActivity
{
  private Button logoutButton;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
//    setContentView( R.layout.login_success );

    initUI();
  }

  private void initUI()
  {
//    logoutButton = (Button) findViewById( R.id.logoutButton );
//
//    logoutButton.setOnClickListener( new View.OnClickListener()
//    {
//      @Override
//      public void onClick( View view )
//      {
//        onLogoutButtonClicked();
//      }
//    } );
  }

  private void onLogoutButtonClicked()
  {
    Backendless.UserService.logout( new DefaultCallback<Void>( this )
    {
      @Override
      public void handleResponse( Void response )
      {
        super.handleResponse( response );
        startActivity( new Intent( LoginSuccessActivity.this, LoginActivity.class ) );
        finish();
      }

      @Override
      public void handleFault( BackendlessFault fault )
      {
        if( fault.getCode().equals( "3023" ) ) // Unable to logout: not logged in (session expired, etc.)
          handleResponse( null );
        else
          super.handleFault( fault );
      }
    } );

  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
  }
}