package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.backendless.Backendless;

import in.co.appadda.brainteaser.utils.DefaultCallback;
import in.co.appadda.brainteaser.R;

public class RestorePasswordActivity extends AppCompatActivity
{
  private Button restorePasswordButton;
  private EditText loginField;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.restore_password );

    initUI();
  }

  private void initUI()
  {
    restorePasswordButton = (Button) findViewById( R.id.restorePasswordButton );
    loginField = (EditText) findViewById( R.id.loginField );

    restorePasswordButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRestorePasswordButtonClicked();
      }
    } );
  }

  public void onRestorePasswordButtonClicked()
  {
    String login = loginField.getText().toString();
    Backendless.UserService.restorePassword( login, new DefaultCallback<Void>( this )
    {
      @Override
      public void handleResponse( Void response )
      {
        super.handleResponse( response );
        startActivity( new Intent( RestorePasswordActivity.this, PasswordRecoveryRequestedActivity.class ) );
        finish();
      }
    } );
  }
}