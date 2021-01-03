package com.theluckydev.fst.outils.multipleselectspinner;

public class StateVO {
    private String title;
    private boolean selected;

    public StateVO(String nom){
        this.title = nom;
        this.selected = false;
    }

    public StateVO(String nom, boolean select){
        this.title = nom;
        this.selected = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}