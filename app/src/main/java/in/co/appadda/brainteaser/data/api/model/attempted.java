package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class attempted
{
  private String correct;
  private String que_type;
  private java.util.Date created;
  private Integer point;
  private String user_id;
  private String username;
  private String que_id;
  private java.util.Date updated;
  private String objectId;
  private String ownerId;
  public String getCorrect()
  {
    return correct;
  }

  public void setCorrect( String correct )
  {
    this.correct = correct;
  }

  public String getQue_type()
  {
    return que_type;
  }

  public void setQue_type( String que_type )
  {
    this.que_type = que_type;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public Integer getPoint()
  {
    return point;
  }

  public void setPoint( Integer point )
  {
    this.point = point;
  }

  public String getUser_id()
  {
    return user_id;
  }

  public void setUser_id( String user_id )
  {
    this.user_id = user_id;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public String getQue_id()
  {
    return que_id;
  }

  public void setQue_id( String que_id )
  {
    this.que_id = que_id;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

                                                    
  public attempted save()
  {
    return Backendless.Data.of( attempted.class ).save( this );
  }

  public Future<attempted> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attempted> future = new Future<attempted>();
      Backendless.Data.of( attempted.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<attempted> callback )
  {
    Backendless.Data.of( attempted.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( attempted.class ).remove( this );
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
      Backendless.Data.of( attempted.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( attempted.class ).remove( this, callback );
  }

  public static attempted findById( String id )
  {
    return Backendless.Data.of( attempted.class ).findById( id );
  }

  public static Future<attempted> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attempted> future = new Future<attempted>();
      Backendless.Data.of( attempted.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<attempted> callback )
  {
    Backendless.Data.of( attempted.class ).findById( id, callback );
  }

  public static attempted findFirst()
  {
    return Backendless.Data.of( attempted.class ).findFirst();
  }

  public static Future<attempted> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attempted> future = new Future<attempted>();
      Backendless.Data.of( attempted.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<attempted> callback )
  {
    Backendless.Data.of( attempted.class ).findFirst( callback );
  }

  public static attempted findLast()
  {
    return Backendless.Data.of( attempted.class ).findLast();
  }

  public static Future<attempted> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<attempted> future = new Future<attempted>();
      Backendless.Data.of( attempted.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<attempted> callback )
  {
    Backendless.Data.of( attempted.class ).findLast( callback );
  }

  public static BackendlessCollection<attempted> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( attempted.class ).find( query );
  }

  public static Future<BackendlessCollection<attempted>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<attempted>> future = new Future<BackendlessCollection<attempted>>();
      Backendless.Data.of( attempted.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<attempted>> callback )
  {
    Backendless.Data.of( attempted.class ).find( query, callback );
  }
}