package com.microsoft.mipsdksample;

import com.microsoft.informationprotection.LabelInfo;
import com.microsoft.mipsdksample.constant.LabelProtectionType;

import java.util.HashMap;
import java.util.Map;

public class LabelMainInfo extends LabelInfo {

    private String labelName;
    private String labelToolTip;
    private String templateId;
    private WatermarkInfo watermarkInfo;
    private HeaderInfo headerInfo;
    private FooterInfo footerInfo;
    public int nSetingType = 0;
    private int nContentBits = 0; // CONTENT_HEADER = 0X1, CONTENT_FOOTER = 0X2, WATERMARK = 0X4, ENCRYPT = 0x8
    private Map<String, String> mapMatedata = new HashMap<>();
    private LabelProtectionType nTpye = LabelProtectionType.None;   //用于标识标签的保护类型


    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelToolTip() {
        return labelToolTip;
    }

    public void setLabelToolTip(String labelToolTip) {
        this.labelToolTip = labelToolTip;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public WatermarkInfo getWatermarkInfo() {
        return watermarkInfo;
    }

    public void setWatermarkInfo(WatermarkInfo watermarkInfo) {
        this.watermarkInfo = watermarkInfo;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(HeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    public FooterInfo getFooterInfo() {
        return footerInfo;
    }

    public void setFooterInfo(FooterInfo footerInfo) {
        this.footerInfo = footerInfo;
    }

    public int getnSetingType() {
        return nSetingType;
    }

    public void setnSetingType(int nSetingType) {
        this.nSetingType = nSetingType;
    }

    public int getnContentBits() {
        return nContentBits;
    }

    public void setnContentBits(int nContentBits) {
        this.nContentBits = nContentBits;
    }

    public Map<String, String> getMapMatedata() {
        return mapMatedata;
    }

    public void setMapMatedata(Map<String, String> mapMatedata) {
        this.mapMatedata = mapMatedata;
    }

    public LabelProtectionType getnTpye() {
        return nTpye;
    }

    public void setnTpye(LabelProtectionType nTpye) {
        this.nTpye = nTpye;
    }

}
