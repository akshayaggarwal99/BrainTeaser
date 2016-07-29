package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class allque
{
  private String question;
  private String objectId;
  private java.util.Date created;
  private java.util.Date updated;
  private Integer point;
  private String que_id;
  private String explanation;
  private String ownerId;
  private Integer id;
  private String option_two;
  private String option_three;
  private String option_one;
  private String topic;
  private String option_four;
  private String answer;
  public String getQuestion()
  {
    return question;
  }

  public void setQuestion( String question )
  {
    this.question = question;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public Integer getPoint()
  {
    return point;
  }

  public void setPoint( Integer point )
  {
    this.point = point;
  }

  public String getQue_id()
  {
    return que_id;
  }

  public void setQue_id( String que_id )
  {
    this.que_id = que_id;
  }

  public String getExplanation()
  {
    return explanation;
  }

  public void setExplanation( String explanation )
  {
    this.explanation = explanation;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public String getOption_two()
  {
    return option_two;
  }

  public void setOption_two( String option_two )
  {
    this.option_two = option_two;
  }

  public String getOption_three()
  {
    return option_three;
  }

  public void setOption_three( String option_three )
  {
    this.option_three = option_three;
  }

  public String getOption_one()
  {
    return option_one;
  }

  public void setOption_one( String option_one )
  {
    this.option_one = option_one;
  }

  public String getTopic()
  {
    return topic;
  }

  public void setTopic( String topic )
  {
    this.topic = topic;
  }

  public String getOption_four()
  {
    return option_four;
  }

  public void setOption_four( String option_four )
  {
    this.option_four = option_four;
  }

  public String getAnswer()
  {
    return answer;
  }

  public void setAnswer( String answer )
  {
    this.answer = answer;
  }

                                                    
  public allque save()
  {
    return Backendless.Data.of( allque.class ).save( this );
  }

  public Future<allque> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<allque> future = new Future<allque>();
      Backendless.Data.of( allque.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<allque> callback )
  {
    Backendless.Data.of( allque.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( allque.class ).remove( this );
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
      Backendless.Data.of( allque.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( allque.class ).remove( this, callback );
  }

  public static allque findById( String id )
  {
    return Backendless.Data.of( allque.class ).findById( id );
  }

  public static Future<allque> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<allque> future = new Future<allque>();
      Backendless.Data.of( allque.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<allque> callback )
  {
    Backendless.Data.of( allque.class ).findById( id, callback );
  }

  public static allque findFirst()
  {
    return Backendless.Data.of( allque.class ).findFirst();
  }

  public static Future<allque> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<allque> future = new Future<allque>();
      Backendless.Data.of( allque.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<allque> callback )
  {
    Backendless.Data.of( allque.class ).findFirst( callback );
  }

  public static allque findLast()
  {
    return Backendless.Data.of( allque.class ).findLast();
  }

  public static Future<allque> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<allque> future = new Future<allque>();
      Backendless.Data.of( allque.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<allque> callback )
  {
    Backendless.Data.of( allque.class ).findLast( callback );
  }

  public static BackendlessCollection<allque> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( allque.class ).find( query );
  }

  public static Future<BackendlessCollection<allque>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<allque>> future = new Future<BackendlessCollection<allque>>();
      Backendless.Data.of( allque.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<allque>> callback )
  {
    Backendless.Data.of( allque.class ).find( query, callback );
  }
}