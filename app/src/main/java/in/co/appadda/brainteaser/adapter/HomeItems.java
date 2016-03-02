package in.co.appadda.brainteaser.adapter;

public class HomeItems {
    private String mName;
    private int mThumbnail;
    private int nameColor;
    private int thumbnailColor;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public int getnColor() {
        return nameColor;
    }

    public void setnColor(int nameColor) {
        this.nameColor = nameColor;
    }

    public int gettColor() {
        return thumbnailColor;
    }

    public void settColor(int thumbnailColor) {
        this.thumbnailColor = thumbnailColor;
    }
}