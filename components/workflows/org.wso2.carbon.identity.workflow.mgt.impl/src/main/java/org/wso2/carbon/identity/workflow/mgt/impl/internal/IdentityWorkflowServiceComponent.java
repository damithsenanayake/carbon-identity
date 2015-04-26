/*
 * Copyright (c) 2015 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.workflow.mgt.impl.internal;


import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.identity.workflow.mgt.WorkflowRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.AddUserWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.ChangeCredentialWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.DeleteClaimWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.DeleteMultipleClaimsWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.DeleteUserWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.SetMultipleClaimsWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.SetUserClaimWFRequestHandler;
import org.wso2.carbon.identity.workflow.mgt.impl.userstore.UserStoreActionListener;
import org.wso2.carbon.user.core.listener.UserOperationEventListener;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.ConfigurationContextService;

/**
 * @scr.component name="identity.workflow" immediate="true"
 * @scr.reference name="user.realmservice.default" interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 * @scr.reference name="config.context.service"
 * interface="org.wso2.carbon.utils.ConfigurationContextService"
 * cardinality="1..1" policy="dynamic"  bind="setConfigurationContextService"
 * unbind="unsetConfigurationContextService"
 */
public class IdentityWorkflowServiceComponent {
    private static RealmService realmService;
    private static ConfigurationContextService configurationContextService;

    private static BundleContext bundleContext;

    public static RealmService getRealmService() {
        return realmService;
    }

    protected void setRealmService(RealmService realmService) {
        IdentityWorkflowServiceComponent.realmService = realmService;
    }

    public static ConfigurationContextService getConfigurationContextService() {
        return configurationContextService;
    }

    protected void setConfigurationContextService(ConfigurationContextService contextService) {
        IdentityWorkflowServiceComponent.configurationContextService = contextService;
    }

    protected void activate(ComponentContext context) {
        bundleContext = context.getBundleContext();
        bundleContext.registerService(UserOperationEventListener.class.getName(), new UserStoreActionListener(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new AddUserWFRequestHandler(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new DeleteUserWFRequestHandler(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new ChangeCredentialWFRequestHandler(),
                null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new SetUserClaimWFRequestHandler(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new DeleteClaimWFRequestHandler(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(),
                new DeleteMultipleClaimsWFRequestHandler(), null);
        bundleContext.registerService(WorkflowRequestHandler.class.getName(), new SetMultipleClaimsWFRequestHandler()
                , null);
    }

    protected void unsetRealmService(RealmService realmService) {
        IdentityWorkflowServiceComponent.realmService = null;
    }

    protected void unsetConfigurationContextService(ConfigurationContextService contextService) {
        IdentityWorkflowServiceComponent.configurationContextService = null;
    }
}
