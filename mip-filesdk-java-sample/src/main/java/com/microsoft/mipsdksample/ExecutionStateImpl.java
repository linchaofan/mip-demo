package com.microsoft.mipsdksample;

import com.microsoft.informationprotection.AssignmentMethod;
import com.microsoft.informationprotection.Label;
import com.microsoft.informationprotection.ProtectionDescriptor;
import com.microsoft.informationprotection.internal.utils.Pair;
import com.microsoft.informationprotection.policy.ExecutionState;
import com.microsoft.informationprotection.policy.MetadataEntry;
import com.microsoft.informationprotection.policy.action.ActionType;

import java.util.Arrays;
import java.util.List;

public class ExecutionStateImpl extends ExecutionState {

    private final ExecutionStateOptions options;

    public ExecutionStateImpl(ExecutionStateOptions options) {
        this.options = options;
    }

    @Override
    public Label getNewLabel() {
        return options.getLabel();
    }

    @Override
    public AssignmentMethod getNewLabelAssignmentMethod() {
        return options.getAssignmentMethod();
    }

    @Override
    public ProtectionDescriptor getProtectionDescriptor() {
        return options.getProtectionDescriptor();
    }

    @Override
    public String getContentFormat() {
        return options.getContentFormat();
    }

    @Override
    public String getContentIdentifier() {
        return options.getContentIdentifier();
    }

    @Override
    public ActionType getSupportedActions() {
        return options.getSupportedActions();
    }

    @Override
    public Pair<Boolean, String> isDowngradeJustified() {
        return options.getDowngradeJustified();
    }

    @Override
    public List<MetadataEntry> getContentMetadata(List<String> list, List<String> list1) {
        return options.getContentMetadata();
    }
}
