package hello.config.security.token.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.User;
import hello.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.io.IOException;
import java.util.Date;

import static hello.config.security.token.jwt.JWTConstants.*;

public class ManageAuthToken {
//    private static final long MINUTES_5 = 1000 * 60 * 5;

    public static String generateTokenFromUser(User user) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String userJson = mapper.writeValueAsString(user);

            byte[] keyBytes = SECRET.getBytes();
            String base64Encoded = TextCodec.BASE64.encode(keyBytes);


            String token = Jwts.builder()
                    .setSubject(userJson)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, base64Encoded)
                    .compact();

            return TOKEN_PREFIX + token;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User checkTokenAndReturnUser(String token, UserRepository userRepository) throws IOException {
        if (token != null) {
            // parse the token.
            String userJson = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (userJson != null) {
                ObjectMapper mapper = new ObjectMapper();
                User tokenUser = mapper.readValue(userJson, User.class);
                User databaseUser = userRepository.findByUsername(tokenUser.getUsername());
                if (tokenUser.equals(databaseUser)) {
                    return databaseUser;
                }
            }
            return null;
        }
        return null;
    }

    /*public static String generateTokenFromUser(User user) {
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
    }*/

    /*public static User checkTokenAndReturnUser(String token, UserRepository userRepository) {
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
    }*/

}
