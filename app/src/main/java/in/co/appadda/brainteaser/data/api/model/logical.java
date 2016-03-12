package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class logical
{
  private String ownerId;
  private String option_one;
  private Integer _id;
  private Integer set_no;
  private String explanation;
  private String answer;
  private java.util.Date updated;
  private String option_two;
  private java.util.Date created;
  private String questions;
  private String option_four;
  private String objectId;
  private String option_three;
  public String getOwnerId()
  {
    return ownerId;
  }

  public String getOption_one()
  {
    return option_one;
  }

  public void setOption_one( String option_one )
  {
    this.option_one = option_one;
  }

  public Integer get_id()
  {
    return _id;
  }

  public void set_id( Integer _id )
  {
    this._id = _id;
  }

  public Integer getSet_no()
  {
    return set_no;
  }

  public void setSet_no( Integer set_no )
  {
    this.set_no = set_no;
  }

  public String getExplanation()
  {
    return explanation;
  }

  public void setExplanation( String explanation )
  {
    this.explanation = explanation;
  }

  public String getAnswer()
  {
    return answer;
  }

  public void setAnswer( String answer )
  {
    this.answer = answer;
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

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getQuestions()
  {
    return questions;
  }

  public void setQuestions( String questions )
  {
    this.questions = questions;
  }

  public String getOption_four()
  {
    return option_four;
  }

  public void setOption_four( String option_four )
  {
    this.option_four = option_four;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOption_three()
  {
    return option_three;
  }

  public void setOption_three( String option_three )
  {
    this.option_three = option_three;
  }

                                                    
  public logical save()
  {
    return Backendless.Data.of( logical.class ).save( this );
  }

  public Future<logical> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<logical> future = new Future<logical>();
      Backendless.Data.of( logical.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<logical> callback )
  {
    Backendless.Data.of( logical.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( logical.class ).remove( this );
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
      Backendless.Data.of( logical.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( logical.class ).remove( this, callback );
  }

  public static logical findById( String id )
  {
    return Backendless.Data.of( logical.class ).findById( id );
  }

  public static Future<logical> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<logical> future = new Future<logical>();
      Backendless.Data.of( logical.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<logical> callback )
  {
    Backendless.Data.of( logical.class ).findById( id, callback );
  }

  public static logical findFirst()
  {
    return Backendless.Data.of( logical.class ).findFirst();
  }

  public static Future<logical> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<logical> future = new Future<logical>();
      Backendless.Data.of( logical.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<logical> callback )
  {
    Backendless.Data.of( logical.class ).findFirst( callback );
  }

  public static logical findLast()
  {
    return Backendless.Data.of( logical.class ).findLast();
  }

  public static Future<logical> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<logical> future = new Future<logical>();
      Backendless.Data.of( logical.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<logical> callback )
  {
    Backendless.Data.of( logical.class ).findLast( callback );
  }

  public static BackendlessCollection<logical> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( logical.class ).find( query );
  }

  public static Future<BackendlessCollection<logical>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<logical>> future = new Future<BackendlessCollection<logical>>();
      Backendless.Data.of( logical.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<logical>> callback )
  {
    Backendless.Data.of( logical.class ).find( query, callback );
  }
}