/*
*
* Copyright (c) Microsoft Corporation.
* All rights reserved.
*
* This code is licensed under the MIT License.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files(the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions :
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*
*/
package com.microsoft.mipsdksample;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.informationprotection.*;
import com.microsoft.informationprotection.file.IFileProfile;
import com.microsoft.informationprotection.file.LabelingOptions;
import com.microsoft.informationprotection.file.ProtectionSettings;
import com.microsoft.informationprotection.internal.callback.FileHandlerObserver;
import com.microsoft.informationprotection.file.FileEngineSettings;
import com.microsoft.informationprotection.file.FileProfileSettings;
import com.microsoft.informationprotection.file.IFileEngine;
import com.microsoft.informationprotection.file.IFileHandler;
import com.microsoft.informationprotection.internal.gen.Error;
import com.microsoft.informationprotection.internal.gen.ErrorType;
import com.microsoft.informationprotection.internal.gen.ProtectionDescriptorBuilder;
import com.microsoft.informationprotection.internal.protection.ProtectionEngineSettings;
import com.microsoft.informationprotection.internal.protection.ProtectionProfileSettings;
import com.microsoft.informationprotection.policy.*;
import com.microsoft.informationprotection.policy.action.*;
import com.microsoft.informationprotection.protection.IProtectionEngine;
import com.microsoft.informationprotection.protection.IProtectionProfile;
import com.microsoft.mipsdksample.constant.ContentBitsMask;
import com.microsoft.mipsdksample.file.FileOptions;
import com.nimbusds.oauth2.sdk.util.StringUtils;

public class Action {

    AuthDelegateImpl authDelegate;

    IFileProfile fileProfile;
    IFileEngine fileEngine;

    IPolicyProfile policyProfile;
    IPolicyEngine policyEngine;

    IProtectionProfile protectionProfile;
    IProtectionEngine protectionEngine;

    List<Label> labels = null;
    MipContext mipContext;
    String userName;
    String currentUserEmail;

    public static final List<ActionType> defaultSupportActions = Arrays.asList(
            ActionType.AddContentFooter,
            ActionType.AddContentHeader,
            ActionType.AddWatermark,
            ActionType.Metadata,
            ActionType.Custom,
            ActionType.ProtectAdhoc,
            ActionType.ProtectByTemplate,
            ActionType.ProtectDoNotForward,
            ActionType.RemoveProtection,
            ActionType.Justify
    );


    public Action(ApplicationInfo appInfo, String userName, MipComponent... mipComponents) throws InterruptedException, ExecutionException
    {
        this.userName = userName;
        authDelegate = new AuthDelegateImpl(appInfo);
        
        // Initialize MIP For SDK components.
        for (MipComponent mipComponent : mipComponents) {
            MIP.initialize(mipComponent, null);
        }

        // Create MIP Configuration
        // MIP Configuration can be used to set various delegates, feature flags, and other SDK behavior. 
        MipConfiguration mipConfiguration = new MipConfiguration(appInfo, "mip_data", LogLevel.TRACE, false);
        
        // Create MipContext from MipConfiguration
        mipContext = MIP.createMipContext(mipConfiguration);

        for (MipComponent mipComponent : mipComponents) {
            mipWorkingThread(mipComponent);
        }
    }

    private void mipWorkingThread(MipComponent mipComponent) {
        try {
            switch (mipComponent) {
                case FILE:
                    if (fileProfile == null) {
                        fileProfile = createFileProfile();
                    }
                    fileEngine = createFileEngine();
                    break;

                case POLICY:
                    if (policyProfile == null) {
                        policyProfile = createPolicyProfile();
                    }
                    policyEngine = createPolicyEngine();
                    break;

                case PROTECTION:
                    if (protectionProfile == null) {
                        protectionProfile = createProtectionProfile();
                    }
                    protectionEngine = createProtectionEngine();
                    break;

                default:
                    throw new IllegalArgumentException("Invalid MIP Component");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IFileProfile createFileProfile() throws InterruptedException, ExecutionException
    {
        // The ConsentDelegate is required for all FileProfiles, but fires only when connecting to AD RMS.
        ConsentDelegate consentDelegate = new ConsentDelegate();

        // Create FileProfileSettings, passing in to LoadFileProfileAsync() and getting the result. 
        FileProfileSettings fileProfileSettings = new FileProfileSettings(mipContext, CacheStorageType.ON_DISK, consentDelegate);        
        Future<IFileProfile> fileProfileFuture = MIP.loadFileProfileAsync(fileProfileSettings);
        return fileProfileFuture.get();
    }

    private IFileEngine createFileEngine() throws InterruptedException, ExecutionException
    {                    
        // Create the file engine, passing in the username as the first parameter.
        // This sets the engineId to the username, making it easier to load the cached engine. 
        // Using cached engines reduces service road trips and will use cached use licenses for protected content.
        FileEngineSettings engineSettings = new FileEngineSettings(userName, authDelegate, "", "en-US");
     
        // Uncomment to set a functionality filter. These filters are useful for filtering or including labels based on protection type.
        // The example below will result in removal of labels with user-defined permissions. 
        //engineSettings.configureFunctionality(FunctionalityFilterType.CUSTOM, false);      
          
        // Set the user identity for the engine. This aids in service discovery.
        engineSettings.setIdentity(new Identity(userName));                
   
        // Add the engine and get the result. 
        Future<IFileEngine> fileEngineFuture = fileProfile.addEngineAsync(engineSettings);
        return fileEngineFuture.get();
    }

    private IPolicyProfile createPolicyProfile() throws ExecutionException, InterruptedException {
        PolicyProfileSettings settings = new PolicyProfileSettings(mipContext, CacheStorageType.ON_DISK_ENCRYPTED);
        Future<IPolicyProfile> policyProfileFuture = MIP.loadPolicyProfileAsync(settings);
        return policyProfileFuture.get();
    }
    /**
     * 初始化策略引擎
     *
     * @return
     */
    private IPolicyEngine createPolicyEngine() throws ExecutionException, InterruptedException {
        PolicyEngineSettings settings = new PolicyEngineSettings(userName, authDelegate, "", "en-US");
        settings.setCloudEndpointBaseUrl("");
        settings.setIdentity(new Identity(userName));

        Future<IPolicyEngine> policyEngineFuture = policyProfile.addEngineAsync(settings);
        return policyEngineFuture.get();
    }

    private IProtectionProfile createProtectionProfile() throws ExecutionException, InterruptedException {
        ProtectionProfileSettings settings = new ProtectionProfileSettings(mipContext, CacheStorageType.ON_DISK_ENCRYPTED, new ConsentDelegate());
        Future<IProtectionProfile> protectionProfileFuture = MIP.loadProtectionProfileAsync(settings);
        return protectionProfileFuture.get();
    }

    private IProtectionEngine createProtectionEngine() throws ExecutionException, InterruptedException {
        ProtectionEngineSettings settings = new ProtectionEngineSettings(userName, authDelegate, "", "en-US");
        settings.setIdentity(new Identity(userName));
        Future<IProtectionEngine> protectionEngineFuture = protectionProfile.addEngineAsync(settings);
        return protectionEngineFuture.get();
    }

    private IFileHandler createFileHandler(FileOptions options, IFileEngine engine) throws InterruptedException, ExecutionException
    {
        // Create a FileHandler. FileHandlers are used to perform all file-specific operations. 
        FileHandlerObserver observer = new FileHandlerObserver();
        Future<IFileHandler> handlerFuture = engine.createFileHandlerAsync(options.InputFilePath, options.InputFilePath, options.GenerateChangeAuditEvent, observer, null);
        return handlerFuture.get();
    }

    private IPolicyHandler createPolicyHandler(IPolicyEngine engine, boolean isAuditDiscoveryEnabled) {
        return engine.createPolicyHandler(isAuditDiscoveryEnabled);
    }

    public void ListLabels()
    {
        // Use the FileEngine to get all labels for the user and display on screen. 
        if (null != fileEngine) {
            labels = (List<Label>) fileEngine.getSensitivityLabels();
	        labels.forEach(label -> { 
	            System.out.println(label.getName() + " : " + label.getId());
	            if (label.getChildren().size() > 0) {
	                label.getChildren().forEach(child -> {                
	                    System.out.println("\t" + child.getName() + " : " + child.getId());
	                });
	            }
	        });
		} else {
            labels = policyEngine.getSensitivityLabels();
            labels.forEach(label -> {
                System.out.println(label.getName() + " : " + label.getId());
                if(label.getChildren().size() > 0)
                {
                    label.getChildren().forEach(child -> {
                        System.out.println("\t" + child.getName() + " : " + child.getId());
                    });
                }
            });
            Label defaultSensitivityLabel = policyEngine.getDefaultSensitivityLabel("");
            if (null != defaultSensitivityLabel) {
                System.out.println("\t" + defaultSensitivityLabel.getName() + " : " + defaultSensitivityLabel.getId());
            }
        }
    }

    public boolean SetLabel(FileOptions options) throws InterruptedException, ExecutionException
    {

        LabelMainInfo labelInfo = new LabelMainInfo();
        labelInfo.setLabelid(options.LabelId);

        getExtension(options.LabelId, labelInfo);

        com.microsoft.informationprotection.internal.gen.ProtectionDescriptor protectionDes = null;
        if (StringUtils.isNotBlank(labelInfo.getTemplateId())) {
            if (null == protectionEngine) {
                mipWorkingThread(MipComponent.PROTECTION);
            }
            protectionDes = createProtectionDescriptor(labelInfo.getLabelId());
        }

        // Create a new FileHandler for the specified file and options.        
        IFileHandler fileHandler = createFileHandler(options, fileEngine);

        // LabelingOptions is used to set specific attributes about the labeling operation.
        // If the labeling operations throws a JustificationRequiredException, use the 
        // setJustificationMessage() and isDowngradeJustified() then retry. 
        LabelingOptions labelingOptions = new LabelingOptions();

        ProtectionSettings protectionSettings = new ProtectionSettings();
        protectionSettings.setDelegatedUserEmail(currentUserEmail);

        Label label = getLabelFromCache(labels, options.LabelId);
        if (label == null) {
            return false;
        }

        // Attempt to set the label on the FileHandler.
        // The ProtectionSettings object can be used to write protection as another user
        // or to change the pfile extension behavior.                         
        fileHandler.setLabel(label, labelingOptions, protectionSettings);

        // Check to see if handler has been modified. If not, skip commit.
        boolean result = false;
        if(fileHandler.isModified())
        {
            // Commit the result. Will return false if no changes were made. 
            // Given that it's gated on the isModified() property, this should always be true.
            result = fileHandler.commitAsync(options.OutputFilePath).get();
        }
        return result;
    }

    public void getExtension(String labelId, LabelMainInfo outInfo) {
        if (outInfo == null) {
            return;
        }

        ExecutionStateOptions options = new ExecutionStateOptions();
        Label label = getLabelFromCache(labels, labelId);
        if (label == null || !label.isActive()) {
            return;
        }

        outInfo.setLabelName(label.getName());
        outInfo.setLabelToolTip(label.getTooltip());
        options.setLabel(label);
        options.setAssignmentMethod(AssignmentMethod.AUTO);

        computeAction(options, outInfo);

        setLabelInfoData(outInfo);

    }

    public ContentLabel GetLabel(FileOptions options) throws InterruptedException, ExecutionException
    {
        // Create a FileHandler then get the label from the handler. 
        IFileHandler fileHandler = createFileHandler(options, fileEngine);
        return fileHandler.getLabel();
    }

    public ProtectionDescriptor GetProtection(FileOptions options) throws InterruptedException, ExecutionException
    {
        // Create a FileHandler then get the protection descriptor from the handler. 
        IFileHandler fileHandler = createFileHandler(options, fileEngine);
        return fileHandler.getProtection().getProtectionDescriptor();
    }


    static Label getLabelFromCache(List<Label> labels, String labelID)
    {
        Label finedLabel = null;
        if (StringUtils.isBlank(labelID))
            return null;
        if (labels.size() == 0)
            return null;

        int nLabelIndex = 0;
        for (Label label : labels) {
            if (label.getId().equals(labelID)) {
                return label;
            }

            List<Label> children = label.getChildren();
            if (children.size() > 0) {
                finedLabel = getLabelFromCache(children, labelID);
                if (finedLabel != null)
                    return finedLabel;
            }
        }
        return null;
    }

    public void computeAction(ExecutionStateOptions options, LabelMainInfo outInfo) {
        try {
            if (null == policyEngine) {
                mipWorkingThread(MipComponent.POLICY);
            }
            if (policyEngine == null)
                return;

            IPolicyHandler policyHandler = createPolicyHandler(policyEngine, false);

            List<com.microsoft.informationprotection.policy.action.Action> actions = new ArrayList<>();
            defaultSupportActions.forEach(actionType -> {
                options.setSupportedActions(actionType);
                ExecutionStateImpl state = new ExecutionStateImpl(options);
                actions.addAll(policyHandler.computeActions(state));
            });

            /*
             * fixme 此处 actions 包含了多个重复的 Metadata ActionType
             *       猜测原因：每次请求都会默认返回 metadata，重复请求导致
             */

            actions.forEach(action -> {
                switch (action.getActionType()) {
                    case Metadata:
                        MetadataAction metadataAction = (MetadataAction) action;
                        Map<String, String> map = new HashMap<>();
                        metadataAction.getMetadataToAdd().forEach(entry -> map.put(entry.getKey(), entry.getValue()));
                        outInfo.setMapMatedata(map);
                        break;
                    case AddWatermark:
                        AddWatermarkAction watermarkAction = (AddWatermarkAction) action;
                        WatermarkInfo info = new WatermarkInfo(watermarkAction);
                        outInfo.setWatermarkInfo(info);
                        outInfo.nSetingType |= ContentBitsMask.WATERMARK;
                        break;
                    case AddContentHeader:
                        AddContentHeaderAction headerAction = (AddContentHeaderAction) action;
                        HeaderInfo headerInfo = new HeaderInfo(headerAction);
                        outInfo.setHeaderInfo(headerInfo);
                        outInfo.nSetingType |= ContentBitsMask.CONTENT_HEADER;
                        break;
                    case AddContentFooter:
                        AddContentFooterAction footerAction = (AddContentFooterAction) action;
                        FooterInfo footerInfo = new FooterInfo(footerAction);
                        outInfo.setFooterInfo(footerInfo);
                        outInfo.nSetingType |= ContentBitsMask.CONTENT_FOOTER;
                        break;
                    case ProtectByTemplate:
                        ProtectByTemplateAction templateAction = (ProtectByTemplateAction) action;
                        outInfo.setTemplateId(templateAction.templateId);
                        break;
                    default:
                        break;
                }
            });
        } catch (Error mipErr) {
            String errMsg = mipErr.getMessage();
            ErrorType type = mipErr.GetErrorType();
            System.out.println("computeAction Error: " + errMsg + " type: " + type);
            mipErr.printStackTrace();
        } catch (Exception e) {
            System.out.println("computeAction Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setLabelInfoData(LabelMainInfo outInfo) {
        outInfo.getMapMatedata().forEach((key, value) -> {
            if (key.contains("ContentBits")) {
                outInfo.setnContentBits(Integer.parseInt(value));
            }
        });
    }

    public com.microsoft.informationprotection.internal.gen.ProtectionDescriptor createProtectionDescriptor(String templateId) {
        if (StringUtils.isNotBlank(templateId)) {
            return ProtectionDescriptorBuilder.CreateFromTemplate(templateId).Build();
        }
        return null;
    }

}
