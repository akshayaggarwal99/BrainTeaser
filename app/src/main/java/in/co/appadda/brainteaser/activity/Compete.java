package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.Order;
import in.co.appadda.brainteaser.data.api.model.OrderItem;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 08-06-2016.
 */
public class Compete extends AppCompatActivity {

    ImageView home, leaderboard, topics, assesment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);

        Backendless.initApp(getBaseContext(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        home = (ImageView) findViewById(R.id.home);
        leaderboard = (ImageView) findViewById(R.id.leaderboard);
        topics = (ImageView) findViewById(R.id.topics);
        assesment = (ImageView) findViewById(R.id.assesment);

        assesment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compete.this,LoginActivity.class));
            }
        });

        Order order = new Order();

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setName("camera");
        orderItem1.setQuantity(1);
        orderItem1.setPrice(99);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setName("shoe");
        orderItem2.setQuantity(10);
        orderItem2.setPrice(19);

        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);
        order.setOrderName("shirts");
        order.setOrderNumber(1);

        //Backendless.Data.of( Order.class ).save( order );
        Backendless.Data.of(Order.class).save(order, new AsyncCallback<Order>() {
            public void handleResponse(Order response) {
                // new Contact instance has been saved
            }

            public void handleFault(BackendlessFault fault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compete.this, Leaderboard.class));
            }
        });

        topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Compete.this, Topics.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
