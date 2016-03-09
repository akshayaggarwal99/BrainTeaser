package in.co.appadda.brainteaser.data.api.model;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class QuestionSets {
    private String setNo;
    private String totalNoQue;
    private String UserStatus;

    public QuestionSets(String setNo, String totalNoQue, String UserStatus) {
        this.setNo = setNo;
        this.totalNoQue = totalNoQue;
        this.UserStatus = UserStatus;
    }

    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    public String getTotalNoQue() {
        return totalNoQue;
    }

    public void setTotalNoQue(String totalNoQue) {
        this.totalNoQue = totalNoQue;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }
}
