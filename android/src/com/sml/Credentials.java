package com.sml;

import com.badlogic.gdx.utils.Base64Coder;
import com.sml.utils.SettingsService;

import java.util.ArrayList;

/**
 * Created on 30.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class Credentials {

    public static String createCredetials(String username, String password) {
        String cred = Base64Coder.encodeString(String.format("%s:%s", username, password)).replaceAll("\n", "");
        return String.format("Basic %s", cred);
    }

    public static String getCredentials() {
        return SettingsService.getInstance().getString("credentials");
    }

    public static class AuthScope {
        private static AuthScope authScope;
        public static AuthScope getAuthScope() {
            if (authScope == null)
                authScope = new AuthScope();
            return authScope;
        }

        private ArrayList<String> scopes;
        private String note = "proger_game";

        private AuthScope() {
            scopes = new ArrayList<>();
            scopes.add("repo");
            scopes.add("user");
        }
    }

    public static class Token {
        public String token;
    }
}
