package in.co.appadda.brainteaser.data.api.model;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class QuestionSets {
    private int setLogo;
    private String setNo;
    private String totalNoQue;
    private String UserStatus;

    public QuestionSets() {
        super();
    }

    public int getSetLogo() {
        return setLogo;
    }

    public void setSetLogo(int setLogo) {
        this.setLogo = setLogo;
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
