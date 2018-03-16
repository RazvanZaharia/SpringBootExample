package hello.config.security.token.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.User;
import hello.model.UserToken;
import hello.repository.UserRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class ManageToken {
    private static final long MINUTES_5 = 1000 * 60 * 5;

    public static String generateTokenFromUser(User user) {
        try {
            if (user != null) {
                ObjectMapper mapper = new ObjectMapper();
                UserToken userToken = new UserToken(user);

                String userTokenJson = mapper.writeValueAsString(userToken);
                return Base64.getEncoder().withoutPadding().encodeToString(userTokenJson.getBytes());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User checkTokenAndReturnUser(String token, UserRepository userRepository) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] barr = Base64.getDecoder().decode(token);
        String userTokenJson = new String(barr);

        try {
            UserToken userToken = mapper.readValue(userTokenJson, UserToken.class);
            if (userToken != null && userToken.getId() != null) {
                final Long userId = userToken.getId();
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()
                        && !isTimePassed(userToken.getCreatedAt())) {
                    return optionalUser.get();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isTimePassed(long tokenTimestamp) {
        Long currentTime = System.currentTimeMillis();
        Long dif = currentTime - tokenTimestamp;
        return dif > MINUTES_5;
    }

}
