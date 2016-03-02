package in.co.appadda.brainteaser.adapter;

/**
 * Created by dewangankisslove on 01-03-2016.
 */
public class OptionsItems {
    private String number;
    private String option;
    private int right;

    public OptionsItems() {
        super();
    }

    public OptionsItems(String number, String option) {
        super();
        this.number = number;
        this.option = option;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
