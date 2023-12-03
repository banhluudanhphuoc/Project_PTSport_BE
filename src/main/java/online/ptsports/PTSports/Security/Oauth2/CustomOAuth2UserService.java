//package online.ptsports.PTSports.Security.Oauth2;
//
//
//import lombok.RequiredArgsConstructor;
//import online.ptsports.PTSports.Entity.SocialLogin;
//import online.ptsports.PTSports.Entity.User;
//import online.ptsports.PTSports.Exeption.OAuth2AuthenticationProcessingException;
//import online.ptsports.PTSports.Repository.UserRepo;
//import online.ptsports.PTSports.Security.User.OAuth2UserInfo;
//import online.ptsports.PTSports.Security.User.OAuth2UserInfoFactory;
//import online.ptsports.PTSports.Security.UserPrincipal;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(rollbackFor = {Exception.class})
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    private final UserRepo userRepo;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//
//        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//        try {
//            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (AuthenticationException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//        }
//    }
//
//    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//        if (oAuth2UserInfo.getEmail().isEmpty()) {
//            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
//        }
//
//        Optional<User> userOptional = Optional.ofNullable(userRepo.findByEmail(oAuth2UserInfo.getEmail()));
//        User user;
//        if (userOptional.isPresent()) {
//
//            user = userOptional.get();
//
//            user = updateExistingUser(user, oAuth2UserInfo);
//        } else {
//            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
//        }
//
//        return UserPrincipal.create(user, oAuth2User.getAttributes());
//    }
//
//    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
//        User user = new User();
//
////        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
////        user.setProviderId(oAuth2UserInfo.getId());
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setName(oAuth2UserInfo.getName());
//        user.setEmail(oAuth2UserInfo.getEmail());
//
//        SocialLogin socialLogin = new SocialLogin();
//        socialLogin.setId(2);
//
//        user.setSocialLogin(socialLogin);
////        user.setImgUrl(oAuth2UserInfo.getImageUrl());
//        return userRepo.save(user);
//    }
//
//    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
//        existingUser.setName(oAuth2UserInfo.getName());
////        existingUser.setImgUrl(oAuth2UserInfo.getImageUrl());
//        return userRepo.save(existingUser);
//    }
//}
