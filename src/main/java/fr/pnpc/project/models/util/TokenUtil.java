package fr.pnpc.project.models.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.pnpc.project.models.model.User;

import java.io.UnsupportedEncodingException;

/**
 * Created by stephen on 14/10/17.
 */
public class TokenUtil {

    /**
     * Generates a unique token based on the user passed as a parameter.
     * It uses the unique identifier of the user as well as his email address.
     *
     * @param user User object.
     * @return String token.
     * @throws UnsupportedEncodingException
     */
    public static String generate(User user) throws UnsupportedEncodingException {
        return JWT.create()
                .withIssuer(user.getId() + user.getEmail())
                .sign(Algorithm.HMAC256(user.getMdp() + user.getId()));
    }
}
