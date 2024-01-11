package com.microsoft.mipsdksample;

import com.microsoft.informationprotection.internal.policy.ContentMarkAlignment;
import com.microsoft.informationprotection.policy.action.AddContentHeaderAction;

public class HeaderInfo {

    private String text = "";
    private String name = "";
    private String fontName = "";
    private String fontColor = "";
    private int fontSize = 0;
    private ContentMarkAlignment align = ContentMarkAlignment.CENTER;
    private int margin = 0;

    public HeaderInfo() {}

    public HeaderInfo(AddContentHeaderAction action) {
        this.text = action.text;
        this.name = action.uiElementName;
        this.fontName = action.fontName;
        this.fontColor = action.fontColor;
        this.fontSize = action.fontSize;
        this.align = action.alignment;
        this.margin = action.margin;
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public ContentMarkAlignment getAlign() {
        return align;
    }

    public void setAlign(ContentMarkAlignment align) {
        this.align = align;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
}
