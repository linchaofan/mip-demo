package com.microsoft.mipsdksample;

import com.microsoft.informationprotection.internal.policy.WatermarkLayout;
import com.microsoft.informationprotection.policy.action.AddWatermarkAction;

public class WatermarkInfo {

    private String text = "";
    private String name = "";
    private String fontName = "";
    private String fontColor = "";
    private int nSize = 0;
    private WatermarkLayout layout = WatermarkLayout.HORIZONTAL;

    public WatermarkInfo() {
    }

    public WatermarkInfo(AddWatermarkAction action) {
        this.text = action.text;
        this.name = action.uiElementName;
        this.fontName = action.fontName;
        this.fontColor = action.fontColor;
        this.nSize = action.fontSize;
        this.layout = action.watermarklayout;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getnSize() {
        return nSize;
    }

    public void setnSize(int nSize) {
        this.nSize = nSize;
    }

    public WatermarkLayout getLayout() {
        return layout;
    }

    public void setLayout(WatermarkLayout layout) {
        this.layout = layout;
    }
}
