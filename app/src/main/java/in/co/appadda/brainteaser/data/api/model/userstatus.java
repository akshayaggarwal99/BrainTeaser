package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class userstatus
{
  private String ownerId;
  private String userId;
  private java.util.Date created;
  private java.util.Date updated;
  private String status;
  private String objectId;
  public String getOwnerId()
  {
    return ownerId;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId( String userId )
  {
    this.userId = userId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus( String status )
  {
    this.status = status;
  }

  public String getObjectId()
  {
    return objectId;
  }

                                                    
  public userstatus save()
  {
    return Backendless.Data.of( userstatus.class ).save( this );
  }

  public Future<userstatus> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<userstatus> future = new Future<userstatus>();
      Backendless.Data.of( userstatus.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<userstatus> callback )
  {
    Backendless.Data.of( userstatus.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( userstatus.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( userstatus.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( userstatus.class ).remove( this, callback );
  }

  public static userstatus findById( String id )
  {
    return Backendless.Data.of( userstatus.class ).findById( id );
  }

  public static Future<userstatus> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<userstatus> future = new Future<userstatus>();
      Backendless.Data.of( userstatus.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<userstatus> callback )
  {
    Backendless.Data.of( userstatus.class ).findById( id, callback );
  }

  public static userstatus findFirst()
  {
    return Backendless.Data.of( userstatus.class ).findFirst();
  }

  public static Future<userstatus> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<userstatus> future = new Future<userstatus>();
      Backendless.Data.of( userstatus.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<userstatus> callback )
  {
    Backendless.Data.of( userstatus.class ).findFirst( callback );
  }

  public static userstatus findLast()
  {
    return Backendless.Data.of( userstatus.class ).findLast();
  }

  public static Future<userstatus> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<userstatus> future = new Future<userstatus>();
      Backendless.Data.of( userstatus.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<userstatus> callback )
  {
    Backendless.Data.of( userstatus.class ).findLast( callback );
  }

  public static BackendlessCollection<userstatus> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( userstatus.class ).find( query );
  }

  public static Future<BackendlessCollection<userstatus>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<userstatus>> future = new Future<BackendlessCollection<userstatus>>();
      Backendless.Data.of( userstatus.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<userstatus>> callback )
  {
    Backendless.Data.of( userstatus.class ).find( query, callback );
  }
}