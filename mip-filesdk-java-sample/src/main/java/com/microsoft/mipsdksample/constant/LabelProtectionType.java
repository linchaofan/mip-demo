package com.microsoft.mipsdksample.constant;

public enum LabelProtectionType {

    None(-1),            /**< None >**/
    TemplateBased(0),    /**< Handle was created from a template */
    Custom(1);           /**< Handle was created ad hoc */

    private int value;

    LabelProtectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
