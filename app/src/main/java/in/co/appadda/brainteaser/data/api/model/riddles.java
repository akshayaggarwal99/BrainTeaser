package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class riddles
{
  private java.util.Date updated;
  private String objectId;
  private String solution;
  private String question;
  private String ownerId;
  private java.util.Date created;
  private Integer _id;
  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getSolution()
  {
    return solution;
  }

  public void setSolution( String solution )
  {
    this.solution = solution;
  }

  public String getQuestion()
  {
    return question;
  }

  public void setQuestion( String question )
  {
    this.question = question;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public Integer get_id()
  {
    return _id;
  }

  public void set_id( Integer _id )
  {
    this._id = _id;
  }

                                                    
  public riddles save()
  {
    return Backendless.Data.of( riddles.class ).save( this );
  }

  public Future<riddles> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<riddles> future = new Future<riddles>();
      Backendless.Data.of( riddles.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<riddles> callback )
  {
    Backendless.Data.of( riddles.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( riddles.class ).remove( this );
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
      Backendless.Data.of( riddles.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( riddles.class ).remove( this, callback );
  }

  public static riddles findById( String id )
  {
    return Backendless.Data.of( riddles.class ).findById( id );
  }

  public static Future<riddles> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<riddles> future = new Future<riddles>();
      Backendless.Data.of( riddles.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<riddles> callback )
  {
    Backendless.Data.of( riddles.class ).findById( id, callback );
  }

  public static riddles findFirst()
  {
    return Backendless.Data.of( riddles.class ).findFirst();
  }

  public static Future<riddles> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<riddles> future = new Future<riddles>();
      Backendless.Data.of( riddles.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<riddles> callback )
  {
    Backendless.Data.of( riddles.class ).findFirst( callback );
  }

  public static riddles findLast()
  {
    return Backendless.Data.of( riddles.class ).findLast();
  }

  public static Future<riddles> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<riddles> future = new Future<riddles>();
      Backendless.Data.of( riddles.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<riddles> callback )
  {
    Backendless.Data.of( riddles.class ).findLast( callback );
  }

  public static BackendlessCollection<riddles> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( riddles.class ).find( query );
  }

  public static Future<BackendlessCollection<riddles>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<riddles>> future = new Future<BackendlessCollection<riddles>>();
      Backendless.Data.of( riddles.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<riddles>> callback )
  {
    Backendless.Data.of( riddles.class ).find( query, callback );
  }
}