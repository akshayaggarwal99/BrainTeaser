package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class contestque
{
  private Integer point;
  private Integer id;
  private String option_one;
  private String objectId;
  private String option_four;
  private String ownerId;
  private String option_three;
  private java.util.Date updated;
  private String option_two;
  private String que_id;
  private String answer;
  private java.util.Date created;
  private String que;
  public Integer getPoint()
  {
    return point;
  }

  public void setPoint( Integer point )
  {
    this.point = point;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public String getOption_one()
  {
    return option_one;
  }

  public void setOption_one( String option_one )
  {
    this.option_one = option_one;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOption_four()
  {
    return option_four;
  }

  public void setOption_four( String option_four )
  {
    this.option_four = option_four;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getOption_three()
  {
    return option_three;
  }

  public void setOption_three( String option_three )
  {
    this.option_three = option_three;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getOption_two()
  {
    return option_two;
  }

  public void setOption_two( String option_two )
  {
    this.option_two = option_two;
  }

  public String getQue_id()
  {
    return que_id;
  }

  public void setQue_id( String que_id )
  {
    this.que_id = que_id;
  }

  public String getAnswer()
  {
    return answer;
  }

  public void setAnswer( String answer )
  {
    this.answer = answer;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getQue()
  {
    return que;
  }

  public void setQue( String que )
  {
    this.que = que;
  }

                                                    
  public contestque save()
  {
    return Backendless.Data.of( contestque.class ).save( this );
  }

  public Future<contestque> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestque> future = new Future<contestque>();
      Backendless.Data.of( contestque.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<contestque> callback )
  {
    Backendless.Data.of( contestque.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( contestque.class ).remove( this );
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
      Backendless.Data.of( contestque.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( contestque.class ).remove( this, callback );
  }

  public static contestque findById( String id )
  {
    return Backendless.Data.of( contestque.class ).findById( id );
  }

  public static Future<contestque> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestque> future = new Future<contestque>();
      Backendless.Data.of( contestque.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<contestque> callback )
  {
    Backendless.Data.of( contestque.class ).findById( id, callback );
  }

  public static contestque findFirst()
  {
    return Backendless.Data.of( contestque.class ).findFirst();
  }

  public static Future<contestque> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestque> future = new Future<contestque>();
      Backendless.Data.of( contestque.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<contestque> callback )
  {
    Backendless.Data.of( contestque.class ).findFirst( callback );
  }

  public static contestque findLast()
  {
    return Backendless.Data.of( contestque.class ).findLast();
  }

  public static Future<contestque> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<contestque> future = new Future<contestque>();
      Backendless.Data.of( contestque.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<contestque> callback )
  {
    Backendless.Data.of( contestque.class ).findLast( callback );
  }

  public static BackendlessCollection<contestque> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( contestque.class ).find( query );
  }

  public static Future<BackendlessCollection<contestque>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<contestque>> future = new Future<BackendlessCollection<contestque>>();
      Backendless.Data.of( contestque.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<contestque>> callback )
  {
    Backendless.Data.of( contestque.class ).find( query, callback );
  }
}