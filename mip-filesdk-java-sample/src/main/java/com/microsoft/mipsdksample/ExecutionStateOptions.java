package com.microsoft.mipsdksample;

import com.microsoft.informationprotection.AssignmentMethod;
import com.microsoft.informationprotection.DataState;
import com.microsoft.informationprotection.Label;
import com.microsoft.informationprotection.ProtectionDescriptor;
import com.microsoft.informationprotection.internal.ActionSource;
import com.microsoft.informationprotection.internal.utils.Pair;
import com.microsoft.informationprotection.policy.MetadataEntry;
import com.microsoft.informationprotection.policy.action.ActionType;

import java.util.List;

public class ExecutionStateOptions {

    private Label label;

    private AssignmentMethod assignmentMethod = AssignmentMethod.STANDARD;

    private ProtectionDescriptor protectionDescriptor;

    private String contentFormat;

    private String contentIdentifier;

    private ActionType supportedActions;

    private Pair<Boolean, String> downgradeJustified = new Pair<>(false, "");

    private List<MetadataEntry> contentMetadata;


    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public AssignmentMethod getAssignmentMethod() {
        return assignmentMethod;
    }

    public void setAssignmentMethod(AssignmentMethod assignmentMethod) {
        this.assignmentMethod = assignmentMethod;
    }

    public ProtectionDescriptor getProtectionDescriptor() {
        return protectionDescriptor;
    }

    public void setProtectionDescriptor(ProtectionDescriptor protectionDescriptor) {
        this.protectionDescriptor = protectionDescriptor;
    }

    public String getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    public String getContentIdentifier() {
        return contentIdentifier;
    }

    public void setContentIdentifier(String contentIdentifier) {
        this.contentIdentifier = contentIdentifier;
    }

    public ActionType getSupportedActions() {
        return supportedActions;
    }

    public void setSupportedActions(ActionType supportedActions) {
        this.supportedActions = supportedActions;
    }

    public Pair<Boolean, String> getDowngradeJustified() {
        return downgradeJustified;
    }

    public void setDowngradeJustified(Pair<Boolean, String> downgradeJustified) {
        this.downgradeJustified = downgradeJustified;
    }

    public List<MetadataEntry> getContentMetadata() {
        return contentMetadata;
    }

    public void setContentMetadata(List<MetadataEntry> contentMetadata) {
        this.contentMetadata = contentMetadata;
    }
}
