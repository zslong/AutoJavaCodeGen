package me.autojava.model;

/**
 * Created by shilong.zhang on 2018/2/2.
 */
public class DcsField {
    private int fid;
    private String name;
    private String description;
    private boolean editable;
    private boolean needCompare;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isNeedCompare() {
        return needCompare;
    }

    public void setNeedCompare(boolean needCompare) {
        this.needCompare = needCompare;
    }

    @Override
    public String toString() {
        return "DcsField{" +
                "fid=" + fid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", editable=" + editable +
                ", needCompare=" + needCompare +
                '}';
    }
}
