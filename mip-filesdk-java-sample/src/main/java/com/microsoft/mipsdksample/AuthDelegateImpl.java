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

        String token = "";
        if (resource.startsWith("https://aadrm.com")) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL2FhZHJtLmNvbSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0LzI5OGM0OGQwLWY1NTctNDU2MS1iNTRkLTFiZmZlZDFkNmI3NC8iLCJpYXQiOjE3MDM1NTE2NDQsIm5iZiI6MTcwMzU1MTY0NCwiZXhwIjoxNzAzNjM0NzQ0LCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFZBQUFBWStSRkdHUTAxbkwxT05WNlk0Rm8yVGVNdU1aSCtKNFZyQ3VqaDZpSlNKREl4RHJHOG1CcUQ4VnJoZENRUUtkUSIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiJjMzA3YTA4OS1kNzdiLTRhMTAtOWI3Ni1iZTcxMjFjNmU0MDAiLCJhcHBpZGFjciI6IjAiLCJpcGFkZHIiOiIyMjAuMjUwLjQwLjMwIiwibmFtZSI6IlRvbSIsIm9pZCI6ImI5ZDFhN2UxLTkxYzMtNDJmMS04MjAwLWUxNDJmMjRlYTc3YyIsInB1aWQiOiIxMDAzM0ZGRjk3NjdFMTg2IiwicmgiOiIwLkFUMEEwRWlNS1ZmMVlVVzFUUnZfN1IxcmRCSUFBQUFBQUFBQXdBQUFBQUFBQUFBOUFJNC4iLCJzY3AiOiJ1c2VyX2ltcGVyc29uYXRpb24iLCJzdWIiOiJjTExIdENzVS1NalQ3MjRXZlhvbkpkMS1VZkV4MzZHWUxRN1czZHpSR21jIiwidGlkIjoiMjk4YzQ4ZDAtZjU1Ny00NTYxLWI1NGQtMWJmZmVkMWQ2Yjc0IiwidW5pcXVlX25hbWUiOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJ0b21AZm94aXRzb2Z0d2FyZWluYy5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJ6TU1wbTR0UlgweWRFMnV2ZGsxa0FRIiwidmVyIjoiMS4wIn0.wLkq9F3Sms4hFpS2NLqKDdFOtERxGbNWXUpaiOvqRnFQW-jNiWf9XTK-EC4bsYmed9TrXfhY7WeHJ9YzlWYbppsy6P0je_H5ZKx1jf5CrkUgUbIK2o4LBAeXXuFliVbjmquGzDWf7TPwkONjWe6rFuOA4KISuCQExTadaiRZPquSBEv1skmCxX5YhFb0m7SXTxulTTGhQgv4osgrnlD6U0pGqi9aZ6WJj57KGGhZr9tmVzIWzdPLRKKV_Eb7s6qZrdJWv7xvy2goA-mucu10KY45YilTWy8qTda8tUgXKGPhYWRoiq1BuxaQqnsenDgA1EShXu0g-_U3Z_8pguWOmQ";
        } else if (resource.startsWith("https://syncservice.o365syncservice.com")) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL3N5bmNzZXJ2aWNlLm8zNjVzeW5jc2VydmljZS5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yOThjNDhkMC1mNTU3LTQ1NjEtYjU0ZC0xYmZmZWQxZDZiNzQvIiwiaWF0IjoxNzAzNTU4MTg2LCJuYmYiOjE3MDM1NTgxODYsImV4cCI6MTcwMzY0MTI4NiwiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhWQUFBQUtud1VLcEFpMHJhbnBKQ3kzMFEwYVVraWNZOGtnYWdKcnprek0yZ0cySFdYckR0ZURxLzdVYXAzRUUzZ3J3bTkiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiYzMwN2EwODktZDc3Yi00YTEwLTliNzYtYmU3MTIxYzZlNDAwIiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiMTI5LjIyNy41Ny4yMjIiLCJuYW1lIjoiVG9tIiwib2lkIjoiYjlkMWE3ZTEtOTFjMy00MmYxLTgyMDAtZTE0MmYyNGVhNzdjIiwicHVpZCI6IjEwMDMzRkZGOTc2N0UxODYiLCJyaCI6IjAuQVQwQTBFaU1LVmYxWVVXMVRSdl83UjFyZEM1UERJZTJoVU5OdmRwdTJhVjV0eVU5QUk0LiIsInNjcCI6IlVuaWZpZWRQb2xpY3kuVXNlci5SZWFkIiwic3ViIjoiVWlGT3RIc2NocjdBMU9BUHVBSkpWVXFMRjlRcElCb3RTUHVOUXlqazlmdyIsInRpZCI6IjI5OGM0OGQwLWY1NTctNDU2MS1iNTRkLTFiZmZlZDFkNmI3NCIsInVuaXF1ZV9uYW1lIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXBuIjoidG9tQGZveGl0c29mdHdhcmVpbmMub25taWNyb3NvZnQuY29tIiwidXRpIjoiNlk1dnllUnpwRUNvZVc1djJKQXlBUSIsInZlciI6IjEuMCJ9.lrvgz87o_Qo8Y7uzZYizk2xIS8ZLc0cq3zzX_qq0Ob0NOMD-pOIapIZbGgXyg-ZMi4_mMzL1xxPKURpWBmxEelJCx6yDbQtDOYzCYfpBW2en8XUwzGjxJc2cB5umuluSqsbs5iYtsAdCjCoOE6FR1LQJUUXc4k7QQASD0vBzlRCRrTvjfK5CAx2Xsrpe4lZWYskj6K8sC3S1cZLcyuWVhinKMB_Pak8tZWw6_hZc6Y6b0LhVMbqqv9HmqK6bOv9H-5XWr1hNq0kTr8LGvNgx136NynvDAasGOFvWmz9TTR_X5CjQwpn8vkl45UHjPpHFeNdk22ZgMc0e67vjonI1Gg\n";
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
