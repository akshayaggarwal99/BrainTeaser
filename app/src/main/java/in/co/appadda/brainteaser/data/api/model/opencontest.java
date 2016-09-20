package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class opencontest
{
  private java.util.Date created;
  private String objectId;
  private Integer contestnumber;
  private String start_time;
  private java.util.Date updated;
  private String dates;
  private String ownerId;
  private String end_time;
  public java.util.Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Integer getContestnumber()
  {
    return contestnumber;
  }

  public void setContestnumber( Integer contestnumber )
  {
    this.contestnumber = contestnumber;
  }

  public String getStart_time()
  {
    return start_time;
  }

  public void setStart_time( String start_time )
  {
    this.start_time = start_time;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getDates()
  {
    return dates;
  }

  public void setDates( String dates )
  {
    this.dates = dates;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getEnd_time()
  {
    return end_time;
  }

  public void setEnd_time( String end_time )
  {
    this.end_time = end_time;
  }

                                                    
  public opencontest save()
  {
    return Backendless.Data.of( opencontest.class ).save( this );
  }

  public Future<opencontest> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<opencontest> future = new Future<opencontest>();
      Backendless.Data.of( opencontest.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<opencontest> callback )
  {
    Backendless.Data.of( opencontest.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( opencontest.class ).remove( this );
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
      Backendless.Data.of( opencontest.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( opencontest.class ).remove( this, callback );
  }

  public static opencontest findById( String id )
  {
    return Backendless.Data.of( opencontest.class ).findById( id );
  }

  public static Future<opencontest> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<opencontest> future = new Future<opencontest>();
      Backendless.Data.of( opencontest.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<opencontest> callback )
  {
    Backendless.Data.of( opencontest.class ).findById( id, callback );
  }

  public static opencontest findFirst()
  {
    return Backendless.Data.of( opencontest.class ).findFirst();
  }

  public static Future<opencontest> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<opencontest> future = new Future<opencontest>();
      Backendless.Data.of( opencontest.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<opencontest> callback )
  {
    Backendless.Data.of( opencontest.class ).findFirst( callback );
  }

  public static opencontest findLast()
  {
    return Backendless.Data.of( opencontest.class ).findLast();
  }

  public static Future<opencontest> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<opencontest> future = new Future<opencontest>();
      Backendless.Data.of( opencontest.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<opencontest> callback )
  {
    Backendless.Data.of( opencontest.class ).findLast( callback );
  }

  public static BackendlessCollection<opencontest> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( opencontest.class ).find( query );
  }

  public static Future<BackendlessCollection<opencontest>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<opencontest>> future = new Future<BackendlessCollection<opencontest>>();
      Backendless.Data.of( opencontest.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<opencontest>> callback )
  {
    Backendless.Data.of( opencontest.class ).find( query, callback );
  }
}