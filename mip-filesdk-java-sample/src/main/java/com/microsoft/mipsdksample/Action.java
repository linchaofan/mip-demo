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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.informationprotection.*;
import com.microsoft.informationprotection.file.IFileProfile;
import com.microsoft.informationprotection.file.LabelingOptions;
import com.microsoft.informationprotection.file.ProtectionSettings;
import com.microsoft.informationprotection.internal.FunctionalityFilterType;
import com.microsoft.informationprotection.internal.callback.FileHandlerObserver;
import com.microsoft.informationprotection.file.FileEngineSettings;
import com.microsoft.informationprotection.file.FileProfileSettings;
import com.microsoft.informationprotection.file.IFileEngine;
import com.microsoft.informationprotection.file.IFileHandler;
import com.microsoft.informationprotection.internal.gen.PolicyProfile;
import com.microsoft.informationprotection.internal.utils.Pair;
import com.microsoft.informationprotection.policy.*;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.microsoft.informationprotection.policy.action.ActionType;

public class Action {

    AuthDelegateImpl authDelegate;    
    IFileProfile fileProfile;
    IFileEngine fileEngine;
    IPolicyProfile policyProfile;
    IPolicyEngine policyEngine;
    List<Label> labels = null;
    MipContext mipContext;
    String userName;

    public Action(ApplicationInfo appInfo, String userName, boolean usePolicyEngine) throws InterruptedException, ExecutionException
    {
        this.userName = userName;
        authDelegate = new AuthDelegateImpl(appInfo);
        
        // Initialize MIP For File SDK components.        
        MIP.initialize(usePolicyEngine? MipComponent.POLICY: MipComponent.FILE, null);

        // Create MIP Configuration
        // MIP Configuration can be used to set various delegates, feature flags, and other SDK behavior. 
        MipConfiguration mipConfiguration = new MipConfiguration(appInfo, "mip_data", LogLevel.TRACE, false);
        
        // Create MipContext from MipConfiguration
        mipContext = MIP.createMipContext(mipConfiguration);
        
        if (usePolicyEngine) {
            policyProfile = createPolicyProfile();
            policyEngine = createPolicyEngine();
        } else {
            // Create the FileProfile and Engine.
            fileProfile = CreateFileProfile();
            fileEngine = CreateFileEngine(fileProfile);
        }
    }

    private IFileProfile CreateFileProfile() throws InterruptedException, ExecutionException
    {
        // The ConsentDelegate is required for all FileProfiles, but fires only when connecting to AD RMS.
        ConsentDelegate consentDelegate = new ConsentDelegate();

        // Create FileProfileSettings, passing in to LoadFileProfileAsync() and getting the result. 
        FileProfileSettings fileProfileSettings = new FileProfileSettings(mipContext, CacheStorageType.ON_DISK, consentDelegate);        
        Future<IFileProfile> fileProfileFuture = MIP.loadFileProfileAsync(fileProfileSettings);
        IFileProfile fileProfile = fileProfileFuture.get();
        return fileProfile;    
    }

    private IFileEngine CreateFileEngine(IFileProfile profile) throws InterruptedException, ExecutionException
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
        IFileEngine fileEngine = fileEngineFuture.get();
        return fileEngine;
    }

    private IPolicyProfile createPolicyProfile() throws ExecutionException, InterruptedException {
        PolicyProfileSettings settings = new PolicyProfileSettings(mipContext, CacheStorageType.ON_DISK_ENCRYPTED);
        Future<IPolicyProfile> policyProfileFuture = MIP.loadPolicyProfileAsync(settings);
        IPolicyProfile policyProfile = policyProfileFuture.get();
        return policyProfile;
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
        IPolicyEngine policyEngine = policyEngineFuture.get();
        return policyEngine;
    }

    private IFileHandler CreateFileHandler(FileOptions options, IFileEngine engine) throws InterruptedException, ExecutionException
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
	        Collection<Label> labels = fileEngine.getSensitivityLabels();
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
        // Create a new FileHandler for the specified file and options.        
        IFileHandler fileHandler = CreateFileHandler(options, fileEngine);

        // LabelingOptions is used to set specific attributes about the labeling operation.
        // If the labeling operations throws a JustificationRequiredException, use the 
        // setJustificationMessage() and isDowngradeJustified() then retry. 
        LabelingOptions labelingOptions = new LabelingOptions();                    
        labelingOptions.setAssignmentMethod(options.AssignmentMethod);

        //labelingOptions.isDowngradeJustified(true);
        //labelingOptions.setJustificationMessage("My Justification Message");

        Label label = fileEngine.getLabelById(options.LabelId);

        // Attempt to set the label on the FileHandler.
        // The ProtectionSettings object can be used to write protection as another user
        // or to change the pfile extension behavior.                         
        fileHandler.setLabel(label, labelingOptions, new ProtectionSettings());

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

    public ContentLabel GetLabel(FileOptions options) throws InterruptedException, ExecutionException
    {
        // Create a FileHandler then get the label from the handler. 
        IFileHandler fileHandler = CreateFileHandler(options, fileEngine);
        return fileHandler.getLabel();
    }

    public ProtectionDescriptor GetProtection(FileOptions options) throws InterruptedException, ExecutionException
    {
        // Create a FileHandler then get the protection descriptor from the handler. 
        IFileHandler fileHandler = CreateFileHandler(options, fileEngine);
        return fileHandler.getProtection().getProtectionDescriptor();
    }


    static Label GetLabelFromCache(List<Label> labels, String labelID)
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
                finedLabel = GetLabelFromCache(children, labelID);
                if (finedLabel != null)
                    return finedLabel;
            }
        }
        return null;
    }
    public boolean SetLabel(String labelId, boolean IsAuditDiscoveryEnabled) throws InterruptedException, ExecutionException
    {
        Label curLabel = GetLabelFromCache(labels, labelId);//policyEngine.getLabelById(labelId);
        if (curLabel == null || !curLabel.isActive()) return false;

        ExecutionStateOptions options = new ExecutionStateOptions();
        options.setLabel(curLabel);
        options.setAssignmentMethod(AssignmentMethod.AUTO);
        stateOptions.setSupportedActions(ActionType.AddWatermark);

        String m_labelId = labelId;
        String m_labelName = curLabel.getName();
        String m_labelTooltip = curLabel.getTooltip();
        /*
        std::string m_templateId;
        WatermarkInfo m_WatermarkInfo;
        HeaderInfo m_HeaderInfo;
        FooterInfo m_FooterInfo;
        int m_nSetingType = 0;
        int m_nContentBits = 0;     // CONTENT_HEADER = 0X1, CONTENT_FOOTER = 0X2, WATERMARK = 0X4, ENCRYPT = 0x8
        std::map<std::string, std::string> m_mapMatedata;
        LabelProtectionType m_nType = LabelProtectionType::None; //用于标识标签的保护类型
        */

        Collection<com.microsoft.informationprotection.policy.action.Action> sss = ComputeAction(options, IsAuditDiscoveryEnabled);

        return true;
    }
    Collection<com.microsoft.informationprotection.policy.action.Action> ComputeAction(ExecutionStateOptions options, boolean IsAuditDiscoveryEnabled)
    {
        try {
            if (null == policyEngine) {
                policyProfile = createPolicyProfile();
                policyEngine = createPolicyEngine();
            }
            if (policyEngine == null)
                return null;

            IPolicyHandler policyHandler = createPolicyHandler(policyEngine, IsAuditDiscoveryEnabled);
            ExecutionStateImpl state = new ExecutionStateImpl(options);

            Collection<com.microsoft.informationprotection.policy.action.Action> actions = policyHandler.computeActions(state);
            if (/*options.generateAuditEvent && */actions.size() == 0) {//TODO
                policyHandler.notifyCommittedActions(state);
            }
            return actions;
        } catch (Exception e) {
            System.out.println("ComputeAction Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
