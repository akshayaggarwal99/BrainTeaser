package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class contestboard
{
  private String username;
  private String type_info;
  private String objectId;
  private String ownerId;
  private String attemptque;
  private String user_id;
  private String rightans;
  private java.util.Date created;
  private String profileurl;
  private java.util.Date updated;
  private String total_points;
  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public String getType_info()
  {
    return type_info;
  }

  public void setType_info( String type_info )
  {
    this.type_info = type_info;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getAttemptque()
  {
    return attemptque;
  }

  public void setAttemptque( String attemptque )
  {
    this.attemptque = attemptque;
  }

  public String getUser_id()
  {
    return user_id;
  }

  public void setUser_id( String user_id )
  {
    this.user_id = user_id;
  }

  public String getRightans()
  {
    return rightans;
  }

  public void setRightans( String rightans )
  {
    this.rightans = rightans;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getProfileurl()
  {
    return profileurl;
  }

  public void setProfileurl( String profileurl )
  {
    this.profileurl = profileurl;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getTotal_points()
  {
    return total_points;
  }

  public void setTotal_points( String total_points )
  {
    this.total_points = total_points;
  }

                                                    
  public contestboard save()
  {
    return Backendless.Data.of( contestboard.class ).save( this );
  }

  public Future<contestboard> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestboard> future = new Future<contestboard>();
      Backendless.Data.of( contestboard.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<contestboard> callback )
  {
    Backendless.Data.of( contestboard.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( contestboard.class ).remove( this );
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
      Backendless.Data.of( contestboard.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( contestboard.class ).remove( this, callback );
  }

  public static contestboard findById( String id )
  {
    return Backendless.Data.of( contestboard.class ).findById( id );
  }

  public static Future<contestboard> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestboard> future = new Future<contestboard>();
      Backendless.Data.of( contestboard.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<contestboard> callback )
  {
    Backendless.Data.of( contestboard.class ).findById( id, callback );
  }

  public static contestboard findFirst()
  {
    return Backendless.Data.of( contestboard.class ).findFirst();
  }

  public static Future<contestboard> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestboard> future = new Future<contestboard>();
      Backendless.Data.of( contestboard.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<contestboard> callback )
  {
    Backendless.Data.of( contestboard.class ).findFirst( callback );
  }

  public static contestboard findLast()
  {
    return Backendless.Data.of( contestboard.class ).findLast();
  }

  public static Future<contestboard> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestboard> future = new Future<contestboard>();
      Backendless.Data.of( contestboard.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<contestboard> callback )
  {
    Backendless.Data.of( contestboard.class ).findLast( callback );
  }

  public static BackendlessCollection<contestboard> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( contestboard.class ).find( query );
  }

  public static Future<BackendlessCollection<contestboard>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<contestboard>> future = new Future<BackendlessCollection<contestboard>>();
      Backendless.Data.of( contestboard.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<contestboard>> callback )
  {
    Backendless.Data.of( contestboard.class ).find( query, callback );
  }
}