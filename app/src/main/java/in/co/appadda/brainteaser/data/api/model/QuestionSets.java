package in.co.appadda.brainteaser.data.api.model;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class QuestionSets {
    private int img;
    private String setNo;
    private String totalNoQue;
    private String UserStatus;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public QuestionSets(int img, String setNo, String totalNoQue, String UserStatus) {
        this.setNo = setNo;
        this.totalNoQue = totalNoQue;
        this.UserStatus = UserStatus;
        this.img = img;

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
