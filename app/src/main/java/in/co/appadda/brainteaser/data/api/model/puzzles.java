package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class puzzles
{
  private String hint;
  private String question;
  private String ownerId;
  private String answer;
  private Integer _id;
  private String objectId;
  private String solution;
  private java.util.Date created;
  private java.util.Date updated;
  public String getHint()
  {
    return hint;
  }

  public void setHint( String hint )
  {
    this.hint = hint;
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

  public String getAnswer()
  {
    return answer;
  }

  public void setAnswer( String answer )
  {
    this.answer = answer;
  }

  public Integer get_id()
  {
    return _id;
  }

  public void set_id( Integer _id )
  {
    this._id = _id;
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

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

                                                    
  public puzzles save()
  {
    return Backendless.Data.of( puzzles.class ).save( this );
  }

  public Future<puzzles> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<puzzles> future = new Future<puzzles>();
      Backendless.Data.of( puzzles.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<puzzles> callback )
  {
    Backendless.Data.of( puzzles.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( puzzles.class ).remove( this );
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
      Backendless.Data.of( puzzles.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( puzzles.class ).remove( this, callback );
  }

  public static puzzles findById( String id )
  {
    return Backendless.Data.of( puzzles.class ).findById( id );
  }

  public static Future<puzzles> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<puzzles> future = new Future<puzzles>();
      Backendless.Data.of( puzzles.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<puzzles> callback )
  {
    Backendless.Data.of( puzzles.class ).findById( id, callback );
  }

  public static puzzles findFirst()
  {
    return Backendless.Data.of( puzzles.class ).findFirst();
  }

  public static Future<puzzles> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<puzzles> future = new Future<puzzles>();
      Backendless.Data.of( puzzles.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<puzzles> callback )
  {
    Backendless.Data.of( puzzles.class ).findFirst( callback );
  }

  public static puzzles findLast()
  {
    return Backendless.Data.of( puzzles.class ).findLast();
  }

  public static Future<puzzles> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<puzzles> future = new Future<puzzles>();
      Backendless.Data.of( puzzles.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<puzzles> callback )
  {
    Backendless.Data.of( puzzles.class ).findLast( callback );
  }

  public static BackendlessCollection<puzzles> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( puzzles.class ).find( query );
  }

  public static Future<BackendlessCollection<puzzles>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<puzzles>> future = new Future<BackendlessCollection<puzzles>>();
      Backendless.Data.of( puzzles.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<puzzles>> callback )
  {
    Backendless.Data.of( puzzles.class ).find( query, callback );
  }
}