package com.javabean.agilemind.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GitHubOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    @Override
    public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .username(retrieveAttr("login", oAuth2User))
                .name(retrieveAttr("name", oAuth2User))
                .email(retrieveAttr("email", oAuth2User))
                .avatarUrl(retrieveAttr("avatar_url", oAuth2User))
                .attributes(oAuth2User.getAttributes())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER"))).build();
        return customUserDetails;
    }

    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return "GITHUB".equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    private String retrieveAttr(String attr, OAuth2User oAuth2User) {
        Object attribute = oAuth2User.getAttributes().get(attr);
        return attribute == null ? "" : attribute.toString();
    }
}
