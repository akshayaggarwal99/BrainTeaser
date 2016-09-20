package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class leaderboard
{
  private String username;
  private Integer total_attempted_que;
  private String profileurl;
  private String que_type;
  private Integer total_right_ans;
  private Integer total_points;
  private String user_id;
  private String flag;
  private String objectId;
  private java.util.Date created;
  private String ownerId;
  private java.util.Date updated;
  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public Integer getTotal_attempted_que()
  {
    return total_attempted_que;
  }

  public void setTotal_attempted_que( Integer total_attempted_que )
  {
    this.total_attempted_que = total_attempted_que;
  }

  public String getProfileurl()
  {
    return profileurl;
  }

  public void setProfileurl( String profileurl )
  {
    this.profileurl = profileurl;
  }

  public String getQue_type()
  {
    return que_type;
  }

  public void setQue_type( String que_type )
  {
    this.que_type = que_type;
  }

  public Integer getTotal_right_ans()
  {
    return total_right_ans;
  }

  public void setTotal_right_ans( Integer total_right_ans )
  {
    this.total_right_ans = total_right_ans;
  }

  public Integer getTotal_points()
  {
    return total_points;
  }

  public void setTotal_points( Integer total_points )
  {
    this.total_points = total_points;
  }

  public String getUser_id()
  {
    return user_id;
  }

  public void setUser_id( String user_id )
  {
    this.user_id = user_id;
  }

  public String getFlag()
  {
    return flag;
  }

  public void setFlag( String flag )
  {
    this.flag = flag;
  }

  public String getObjectId()
  {
    return objectId;
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

                                                    
  public leaderboard save()
  {
    return Backendless.Data.of( leaderboard.class ).save( this );
  }

  public Future<leaderboard> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<leaderboard> future = new Future<leaderboard>();
      Backendless.Data.of( leaderboard.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<leaderboard> callback )
  {
    Backendless.Data.of( leaderboard.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( leaderboard.class ).remove( this );
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
      Backendless.Data.of( leaderboard.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( leaderboard.class ).remove( this, callback );
  }

  public static leaderboard findById( String id )
  {
    return Backendless.Data.of( leaderboard.class ).findById( id );
  }

  public static Future<leaderboard> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<leaderboard> future = new Future<leaderboard>();
      Backendless.Data.of( leaderboard.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<leaderboard> callback )
  {
    Backendless.Data.of( leaderboard.class ).findById( id, callback );
  }

  public static leaderboard findFirst()
  {
    return Backendless.Data.of( leaderboard.class ).findFirst();
  }

  public static Future<leaderboard> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<leaderboard> future = new Future<leaderboard>();
      Backendless.Data.of( leaderboard.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<leaderboard> callback )
  {
    Backendless.Data.of( leaderboard.class ).findFirst( callback );
  }

  public static leaderboard findLast()
  {
    return Backendless.Data.of( leaderboard.class ).findLast();
  }

  public static Future<leaderboard> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<leaderboard> future = new Future<leaderboard>();
      Backendless.Data.of( leaderboard.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<leaderboard> callback )
  {
    Backendless.Data.of( leaderboard.class ).findLast( callback );
  }

  public static BackendlessCollection<leaderboard> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( leaderboard.class ).find( query );
  }

  public static Future<BackendlessCollection<leaderboard>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<leaderboard>> future = new Future<BackendlessCollection<leaderboard>>();
      Backendless.Data.of( leaderboard.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<leaderboard>> callback )
  {
    Backendless.Data.of( leaderboard.class ).find( query, callback );
  }
}