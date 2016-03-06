package in.co.appadda.brainteaser.data.api.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

import java.io.Serializable;

public class aptitude implements Serializable {
    private String questions;
    private java.util.Date created;
    private String option_three;
    private String explanation;
    private String option_two;
    private String option_four;
    private java.util.Date updated;
    private String ownerId;
    private String answer;
    private Integer _id;
    private String option_one;
    private String objectId;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getOption_three() {
        return option_three;
    }

    public void setOption_three(String option_three) {
        this.option_three = option_three;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getOption_two() {
        return option_two;
    }

    public void setOption_two(String option_two) {
        this.option_two = option_two;
    }

    public String getOption_four() {
        return option_four;
    }

    public void setOption_four(String option_four) {
        this.option_four = option_four;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getOption_one() {
        return option_one;
    }

    public void setOption_one(String option_one) {
        this.option_one = option_one;
    }

    public String getObjectId() {
        return objectId;
    }


    public aptitude save() {
        return Backendless.Data.of(aptitude.class).save(this);
    }

    public Future<aptitude> saveAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<aptitude> future = new Future<aptitude>();
            Backendless.Data.of(aptitude.class).save(this, future);

            return future;
        }
    }

    public void saveAsync(AsyncCallback<aptitude> callback) {
        Backendless.Data.of(aptitude.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(aptitude.class).remove(this);
    }

    public Future<Long> removeAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of(aptitude.class).remove(this, future);

            return future;
        }
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(aptitude.class).remove(this, callback);
    }

    public static aptitude findById(String id) {
        return Backendless.Data.of(aptitude.class).findById(id);
    }

    public static Future<aptitude> findByIdAsync(String id) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<aptitude> future = new Future<aptitude>();
            Backendless.Data.of(aptitude.class).findById(id, future);

            return future;
        }
    }

    public static void findByIdAsync(String id, AsyncCallback<aptitude> callback) {
        Backendless.Data.of(aptitude.class).findById(id, callback);
    }

    public static aptitude findFirst() {
        return Backendless.Data.of(aptitude.class).findFirst();
    }

    public static Future<aptitude> findFirstAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<aptitude> future = new Future<aptitude>();
            Backendless.Data.of(aptitude.class).findFirst(future);

            return future;
        }
    }

    public static void findFirstAsync(AsyncCallback<aptitude> callback) {
        Backendless.Data.of(aptitude.class).findFirst(callback);
    }

    public static aptitude findLast() {
        return Backendless.Data.of(aptitude.class).findLast();
    }

    public static Future<aptitude> findLastAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<aptitude> future = new Future<aptitude>();
            Backendless.Data.of(aptitude.class).findLast(future);

            return future;
        }
    }

    public static void findLastAsync(AsyncCallback<aptitude> callback) {
        Backendless.Data.of(aptitude.class).findLast(callback);
    }

    public static BackendlessCollection<aptitude> find(BackendlessDataQuery query) {
        return Backendless.Data.of(aptitude.class).find(query);
    }

    public static Future<BackendlessCollection<aptitude>> findAsync(BackendlessDataQuery query) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<BackendlessCollection<aptitude>> future = new Future<BackendlessCollection<aptitude>>();
            Backendless.Data.of(aptitude.class).find(query, future);

            return future;
        }
    }

    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<aptitude>> callback) {
        Backendless.Data.of(aptitude.class).find(query, callback);
    }
}