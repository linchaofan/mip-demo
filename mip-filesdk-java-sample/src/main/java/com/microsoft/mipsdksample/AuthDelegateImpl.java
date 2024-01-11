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
        /*if(resource.endsWith("/")){
            SCOPE = Collections.singleton(resource + ".default");        
        }
        else {
            SCOPE = Collections.singleton(resource + "/.default");        
        }

        AUTHORITY = authority;
        try {
            token = acquireTokenInteractive().accessToken();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL3N5bmNzZXJ2aWNlLm8zNjVzeW5jc2VydmljZS5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yOThjNDhkMC1mNTU3LTQ1NjEtYjU0ZC0xYmZmZWQxZDZiNzQvIiwiaWF0IjoxNzA0OTU5NTI3LCJuYmYiOjE3MDQ5NTk1MjcsImV4cCI6MTcwNTA0MjYyNywiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhWQUFBQTIrUW1FdEplWDRVR2k3VnJKK2hPdmEzMnoxSkRFODAxVzhsUTFzSGh6d0JpRW12Q3liNGlJbkFjMzhTaWY0ajYiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiYzMwN2EwODktZDc3Yi00YTEwLTliNzYtYmU3MTIxYzZlNDAwIiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiMjIwLjI1MC40MC4zMCIsIm5hbWUiOiJUb20iLCJvaWQiOiJiOWQxYTdlMS05MWMzLTQyZjEtODIwMC1lMTQyZjI0ZWE3N2MiLCJwdWlkIjoiMTAwMzNGRkY5NzY3RTE4NiIsInJoIjoiMC5BVDBBMEVpTUtWZjFZVVcxVFJ2XzdSMXJkQzVQREllMmhVTk52ZHB1MmFWNXR5VTlBSTQuIiwic2NwIjoiVW5pZmllZFBvbGljeS5Vc2VyLlJlYWQiLCJzdWIiOiJVaUZPdEhzY2hyN0ExT0FQdUFKSlZVcUxGOVFwSUJvdFNQdU5ReWprOWZ3IiwidGlkIjoiMjk4YzQ4ZDAtZjU1Ny00NTYxLWI1NGQtMWJmZmVkMWQ2Yjc0IiwidW5pcXVlX25hbWUiOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJuSHVFb2FYUHZFNjZoNkFYYUoxTEFBIiwidmVyIjoiMS4wIn0.IdxGQUN1cT3A8Jvok-_zCKyFwwss7AU03j17zznhll9I1kKHhNCNtVYUZnqH6XukiIQOjTUlKuvij7zW4BoIaPLgMfzyliEoqOa2HYt9eWt3x88tnmHILhyv0LZe_cznT8N2A8bwRdYQ7YAOohMHXCYZGDAHXWYc6xtw07k3xqHLfRK-aoJUUCR8kw4QcZFf60QJ4KjzizJQYFgr2wB3G0wRSOJAtryBjvJtrokAXIGcxDkP3rbSn3xr0P3B3Cm3VowOEVpe-QZ91wCppb4IrYXB-0T2caLEbwj6dT_osltU_FWvmTZyw0mbm18ejGwSGwl3FzjsUwx3PaVgFGqSCQ";
        if (resource.startsWith("https://aadrm.com")) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL3N5bmNzZXJ2aWNlLm8zNjVzeW5jc2VydmljZS5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yOThjNDhkMC1mNTU3LTQ1NjEtYjU0ZC0xYmZmZWQxZDZiNzQvIiwiaWF0IjoxNzA0OTU5NTI3LCJuYmYiOjE3MDQ5NTk1MjcsImV4cCI6MTcwNTA0MjYyNywiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhWQUFBQTIrUW1FdEplWDRVR2k3VnJKK2hPdmEzMnoxSkRFODAxVzhsUTFzSGh6d0JpRW12Q3liNGlJbkFjMzhTaWY0ajYiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiYzMwN2EwODktZDc3Yi00YTEwLTliNzYtYmU3MTIxYzZlNDAwIiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiMjIwLjI1MC40MC4zMCIsIm5hbWUiOiJUb20iLCJvaWQiOiJiOWQxYTdlMS05MWMzLTQyZjEtODIwMC1lMTQyZjI0ZWE3N2MiLCJwdWlkIjoiMTAwMzNGRkY5NzY3RTE4NiIsInJoIjoiMC5BVDBBMEVpTUtWZjFZVVcxVFJ2XzdSMXJkQzVQREllMmhVTk52ZHB1MmFWNXR5VTlBSTQuIiwic2NwIjoiVW5pZmllZFBvbGljeS5Vc2VyLlJlYWQiLCJzdWIiOiJVaUZPdEhzY2hyN0ExT0FQdUFKSlZVcUxGOVFwSUJvdFNQdU5ReWprOWZ3IiwidGlkIjoiMjk4YzQ4ZDAtZjU1Ny00NTYxLWI1NGQtMWJmZmVkMWQ2Yjc0IiwidW5pcXVlX25hbWUiOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJuSHVFb2FYUHZFNjZoNkFYYUoxTEFBIiwidmVyIjoiMS4wIn0.IdxGQUN1cT3A8Jvok-_zCKyFwwss7AU03j17zznhll9I1kKHhNCNtVYUZnqH6XukiIQOjTUlKuvij7zW4BoIaPLgMfzyliEoqOa2HYt9eWt3x88tnmHILhyv0LZe_cznT8N2A8bwRdYQ7YAOohMHXCYZGDAHXWYc6xtw07k3xqHLfRK-aoJUUCR8kw4QcZFf60QJ4KjzizJQYFgr2wB3G0wRSOJAtryBjvJtrokAXIGcxDkP3rbSn3xr0P3B3Cm3VowOEVpe-QZ91wCppb4IrYXB-0T2caLEbwj6dT_osltU_FWvmTZyw0mbm18ejGwSGwl3FzjsUwx3PaVgFGqSCQ";
        } else if (resource.startsWith("https://syncservice.o365syncservice.com")) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL3N5bmNzZXJ2aWNlLm8zNjVzeW5jc2VydmljZS5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yOThjNDhkMC1mNTU3LTQ1NjEtYjU0ZC0xYmZmZWQxZDZiNzQvIiwiaWF0IjoxNzA0OTU5NTI3LCJuYmYiOjE3MDQ5NTk1MjcsImV4cCI6MTcwNTA0MjYyNywiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhWQUFBQTIrUW1FdEplWDRVR2k3VnJKK2hPdmEzMnoxSkRFODAxVzhsUTFzSGh6d0JpRW12Q3liNGlJbkFjMzhTaWY0ajYiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiYzMwN2EwODktZDc3Yi00YTEwLTliNzYtYmU3MTIxYzZlNDAwIiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiMjIwLjI1MC40MC4zMCIsIm5hbWUiOiJUb20iLCJvaWQiOiJiOWQxYTdlMS05MWMzLTQyZjEtODIwMC1lMTQyZjI0ZWE3N2MiLCJwdWlkIjoiMTAwMzNGRkY5NzY3RTE4NiIsInJoIjoiMC5BVDBBMEVpTUtWZjFZVVcxVFJ2XzdSMXJkQzVQREllMmhVTk52ZHB1MmFWNXR5VTlBSTQuIiwic2NwIjoiVW5pZmllZFBvbGljeS5Vc2VyLlJlYWQiLCJzdWIiOiJVaUZPdEhzY2hyN0ExT0FQdUFKSlZVcUxGOVFwSUJvdFNQdU5ReWprOWZ3IiwidGlkIjoiMjk4YzQ4ZDAtZjU1Ny00NTYxLWI1NGQtMWJmZmVkMWQ2Yjc0IiwidW5pcXVlX25hbWUiOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJuSHVFb2FYUHZFNjZoNkFYYUoxTEFBIiwidmVyIjoiMS4wIn0.IdxGQUN1cT3A8Jvok-_zCKyFwwss7AU03j17zznhll9I1kKHhNCNtVYUZnqH6XukiIQOjTUlKuvij7zW4BoIaPLgMfzyliEoqOa2HYt9eWt3x88tnmHILhyv0LZe_cznT8N2A8bwRdYQ7YAOohMHXCYZGDAHXWYc6xtw07k3xqHLfRK-aoJUUCR8kw4QcZFf60QJ4KjzizJQYFgr2wB3G0wRSOJAtryBjvJtrokAXIGcxDkP3rbSn3xr0P3B3Cm3VowOEVpe-QZ91wCppb4IrYXB-0T2caLEbwj6dT_osltU_FWvmTZyw0mbm18ejGwSGwl3FzjsUwx3PaVgFGqSCQ";
        }
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
