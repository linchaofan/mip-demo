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
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL2FhZHJtLmNvbSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0LzI5OGM0OGQwLWY1NTctNDU2MS1iNTRkLTFiZmZlZDFkNmI3NC8iLCJpYXQiOjE3MDM1NTY2MjAsIm5iZiI6MTcwMzU1NjYyMCwiZXhwIjoxNzAzNjM5NzIwLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFZBQUFBM3dLRDIvbWRzeW1Cc1kwazgrbWExSHZWV3pvWlNsaDNEUlhkbmVySDJxT3B0Vld3dUk4OUExdXpwb3JJbzE1WCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiJjMzA3YTA4OS1kNzdiLTRhMTAtOWI3Ni1iZTcxMjFjNmU0MDAiLCJhcHBpZGFjciI6IjAiLCJpcGFkZHIiOiIxMjkuMjI3LjU3LjIyMiIsIm5hbWUiOiJUb20iLCJvaWQiOiJiOWQxYTdlMS05MWMzLTQyZjEtODIwMC1lMTQyZjI0ZWE3N2MiLCJwdWlkIjoiMTAwMzNGRkY5NzY3RTE4NiIsInJoIjoiMC5BVDBBMEVpTUtWZjFZVVcxVFJ2XzdSMXJkQklBQUFBQUFBQUF3QUFBQUFBQUFBQTlBSTQuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoiY0xMSHRDc1UtTWpUNzI0V2ZYb25KZDEtVWZFeDM2R1lMUTdXM2R6UkdtYyIsInRpZCI6IjI5OGM0OGQwLWY1NTctNDU2MS1iNTRkLTFiZmZlZDFkNmI3NCIsInVuaXF1ZV9uYW1lIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXBuIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXRpIjoiU2JkQzhmdkJhRUNwQkNmT2lsaVJBZyIsInZlciI6IjEuMCJ9.tujAEPsT2mTFFDYqHRPrHsEZWskXctWqXvpC_Y-1KyzVB090f_UxQG8GMuIaO8v2L1BR9gNs9_paGHzxE__pOTxhsFjgCu6JpmgPoqDHZlKT97oM0XYj57v2Yw-vYnwczjAsoWzqchsKYv71xLXBPQrIP13yZO2D5JSvmCS7VnTWxi9q4nHU8X5qNNySOO4urFkeVoyr0mYpZDysw_JiJM9OsamR9a-CEdYeSE7w_e6ICYzEVTObCmHP5mImrA4-EGSKPBOEs9M8IWM5gihGbiFzHW-l6DLpjh0OowQVQwSQVAsGxe3nYonv3kr2dzWfUC9o4kOw8tOuJkdbwkTv2Q";
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
