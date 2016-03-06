package in.co.appadda.brainteaser.data.api.model;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;

public class Future<T> implements AsyncCallback<T>
{
  private T result;
  private RuntimeException exception;
  private boolean complete;
  private boolean fault;

  public synchronized T get() throws InterruptedException
  {
    while( !complete )
    {
      wait();
    }

    if( fault )
    {
      throw exception;
    }

    return result;
  }

  public synchronized void set( T t )
  {
    result = t;
    complete = true;
    notifyAll();
  }

  public synchronized void fault( RuntimeException e )
  {
    exception = e;
    fault = true;
    complete = true;
    notifyAll();
  }

  public boolean isComplete()
  {
    return complete;
  }

  public boolean isFault()
  {
    return fault;
  }

  @Override
  public void handleResponse( T response )
  {
    set( response );
  }

  @Override
  public void handleFault( BackendlessFault fault )
  {
    fault( new BackendlessException( fault.getCode(), fault.getMessage() ) );
  }
}