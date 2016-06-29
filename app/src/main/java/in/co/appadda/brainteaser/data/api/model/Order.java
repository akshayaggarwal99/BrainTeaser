package in.co.appadda.brainteaser.data.api.model;

import java.util.ArrayList;
import java.util.List;

public class Order
{
  private int orderNumber;
  private String orderName;
  private List<OrderItem> orderItems;

  public String getOrderName()
  {
    return orderName;
  }

  public void setOrderName( String orderName )
  {
    this.orderName = orderName;
  }

  public int getOrderNumber()
  {
    return orderNumber;
  }

  public void setOrderNumber( int orderNumber )
  {
    this.orderNumber = orderNumber;
  }

  public void addOrderItem( OrderItem orderItem )
  {
    if( orderItems == null )
      orderItems = new ArrayList<OrderItem>();

    orderItems.add( orderItem );
  }

  public List<OrderItem> getOrderItems()
  {
    return orderItems;
  }

  public void setOrderItems( List<OrderItem> orderItems )
  {
    this.orderItems = orderItems;
  }
}
