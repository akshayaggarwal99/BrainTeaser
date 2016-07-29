package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class attemptedcontest
{
  private String objectId;
  private String que_id;
  private String que_type;
  private String correct;
  private Integer point;
  private java.util.Date created;
  private String ownerId;
  private java.util.Date updated;
  private String username;
  private String user_id;
  public String getObjectId()
  {
    return objectId;
  }

  public String getQue_id()
  {
    return que_id;
  }

  public void setQue_id( String que_id )
  {
    this.que_id = que_id;
  }

  public String getQue_type()
  {
    return que_type;
  }

  public void setQue_type( String que_type )
  {
    this.que_type = que_type;
  }

  public String getCorrect()
  {
    return correct;
  }

  public void setCorrect( String correct )
  {
    this.correct = correct;
  }

  public Integer getPoint()
  {
    return point;
  }

  public void setPoint( Integer point )
  {
    this.point = point;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public String getUser_id()
  {
    return user_id;
  }

  public void setUser_id( String user_id )
  {
    this.user_id = user_id;
  }

                                                    
  public attemptedcontest save()
  {
    return Backendless.Data.of( attemptedcontest.class ).save( this );
  }

  public Future<attemptedcontest> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attemptedcontest> future = new Future<attemptedcontest>();
      Backendless.Data.of( attemptedcontest.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<attemptedcontest> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( attemptedcontest.class ).remove( this );
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
      Backendless.Data.of( attemptedcontest.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).remove( this, callback );
  }

  public static attemptedcontest findById( String id )
  {
    return Backendless.Data.of( attemptedcontest.class ).findById( id );
  }

  public static Future<attemptedcontest> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attemptedcontest> future = new Future<attemptedcontest>();
      Backendless.Data.of( attemptedcontest.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<attemptedcontest> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).findById( id, callback );
  }

  public static attemptedcontest findFirst()
  {
    return Backendless.Data.of( attemptedcontest.class ).findFirst();
  }

  public static Future<attemptedcontest> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attemptedcontest> future = new Future<attemptedcontest>();
      Backendless.Data.of( attemptedcontest.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<attemptedcontest> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).findFirst( callback );
  }

  public static attemptedcontest findLast()
  {
    return Backendless.Data.of( attemptedcontest.class ).findLast();
  }

  public static Future<attemptedcontest> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attemptedcontest> future = new Future<attemptedcontest>();
      Backendless.Data.of( attemptedcontest.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<attemptedcontest> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).findLast( callback );
  }

  public static BackendlessCollection<attemptedcontest> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( attemptedcontest.class ).find( query );
  }

  public static Future<BackendlessCollection<attemptedcontest>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<attemptedcontest>> future = new Future<BackendlessCollection<attemptedcontest>>();
      Backendless.Data.of( attemptedcontest.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<attemptedcontest>> callback )
  {
    Backendless.Data.of( attemptedcontest.class ).find( query, callback );
  }
}