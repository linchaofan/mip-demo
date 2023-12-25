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

import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.InteractiveRequestParameters;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.Prompt;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.informationprotection.ApplicationInfo;
import com.microsoft.informationprotection.IAuthDelegate;
import com.microsoft.informationprotection.Identity;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

public class AuthDelegateImpl implements IAuthDelegate {

    private static String CLIENT_ID = "";
    private static String AUTHORITY = "";
    private static Set<String> SCOPE = Collections.singleton("");

    public AuthDelegateImpl(ApplicationInfo appInfo)
    {
        CLIENT_ID = appInfo.getApplicationId();
    }

    @Override
    public String acquireToken(Identity userName, String authority, String resource, String claims) {
        if(resource.endsWith("/")){
            SCOPE = Collections.singleton(resource + ".default");        
        }
        else {
            SCOPE = Collections.singleton(resource + "/.default");        
        }

        AUTHORITY = authority;
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlQxU3QtZExUdnlXUmd4Ql82NzZ1OGtyWFMtSSIsImtpZCI6IlQxU3QtZExUdnlXUmd4Ql82NzZ1OGtyWFMtSSJ9.eyJhdWQiOiJodHRwczovL3N5bmNzZXJ2aWNlLm8zNjVzeW5jc2VydmljZS5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yOThjNDhkMC1mNTU3LTQ1NjEtYjU0ZC0xYmZmZWQxZDZiNzQvIiwiaWF0IjoxNzAzMjEyMjE5LCJuYmYiOjE3MDMyMTIyMTksImV4cCI6MTcwMzI5NTMxOSwiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhWQUFBQWxWTHRJU3ZHZFdiUUMxWm9SeXFPTWwrWlc5dTBpY1NVSkVoS2xqSW5QWUhRR2RTRjA2WUdZNlI0T0JuM05TMEEiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiYzMwN2EwODktZDc3Yi00YTEwLTliNzYtYmU3MTIxYzZlNDAwIiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiMTI5LjIyNy41Ny4yMjIiLCJuYW1lIjoiVG9tIiwib2lkIjoiYjlkMWE3ZTEtOTFjMy00MmYxLTgyMDAtZTE0MmYyNGVhNzdjIiwicHVpZCI6IjEwMDMzRkZGOTc2N0UxODYiLCJyaCI6IjAuQVQwQTBFaU1LVmYxWVVXMVRSdl83UjFyZEM1UERJZTJoVU5OdmRwdTJhVjV0eVU5QUk0LiIsInNjcCI6IlVuaWZpZWRQb2xpY3kuVXNlci5SZWFkIiwic3ViIjoiVWlGT3RIc2NocjdBMU9BUHVBSkpWVXFMRjlRcElCb3RTUHVOUXlqazlmdyIsInRpZCI6IjI5OGM0OGQwLWY1NTctNDU2MS1iNTRkLTFiZmZlZDFkNmI3NCIsInVuaXF1ZV9uYW1lIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXBuIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXRpIjoieGVZQ2xwZ202RUNZZWxaVmFKelVBUSIsInZlciI6IjEuMCJ9.kNHnaIE19clxLgvCFI27lFVaFl-8GQsZQCEf8xm7w80FuN1zCxFX0zwJVGOWK4qubSw844eNKD6CXeY8MjcA6aNta-jEP8ua6i7rK5-og3aFM6VvSVWD-4sSmpxeyBkZ7w5pOJjDE2SVZD38mdwVMDTCb62T7O_Oj7jQtrrsz-WubT_21XpO5RF9_rWZ1n3bUxuE2xzWp0DNPQdsa6hr9Gw6AUYHPV4WHxjCvRhDegGuAQn5zFhMQ0QDxZQ9Cpt6gQUXVNHbqe36yWKqFRRlv_fVeqZKQHeuith1CgiRKc3JS9saF-rxgPL3iHKX-xJHQvptNthPEOV1iDQGjtm2hQ";
//        try {
//            token = acquireTokenInteractive().accessToken();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return token;
    }

    
    private static IAuthenticationResult acquireTokenInteractive() throws Exception {

        // Load token cache from file and initialize token cache aspect. The token cache will have
        // dummy data, so the acquireTokenSilently call will fail.
        TokenCacheAspect tokenCacheAspect = new TokenCacheAspect("D:\\AIP\\sample_cache.json");

        PublicClientApplication pca = PublicClientApplication.builder(CLIENT_ID)
                .authority(AUTHORITY)
                .setTokenCacheAccessAspect(tokenCacheAspect)
                .build();

        Set<IAccount> accountsInCache = pca.getAccounts().join();
        // Take first account in the cache. In a production application, you would filter
        // accountsInCache to get the right account for the user authenticating.
        IAccount account = accountsInCache.iterator().next();

        IAuthenticationResult result;
        try {
            SilentParameters silentParameters =
                    SilentParameters
                            .builder(SCOPE, account)
                            .build();

            // try to acquire token silently. This call will fail since the token cache
            // does not have any data for the user you are trying to acquire a token for
            result = pca.acquireTokenSilently(silentParameters).join();
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {

                InteractiveRequestParameters parameters = InteractiveRequestParameters
                        .builder(new URI("http://localhost"))
                        .prompt(Prompt.SELECT_ACCOUNT) // Change this value to avoid repeated auth prompts. 
                        .scopes(SCOPE)
                        .build();

                // Try to acquire a token interactively with system browser. If successful, you should see
                // the token and account information printed out to console
                result = pca.acquireToken(parameters).join();
            } else {
                // Handle other exceptions accordingly
                throw ex;
            }
        }
        return result;
    }
}
