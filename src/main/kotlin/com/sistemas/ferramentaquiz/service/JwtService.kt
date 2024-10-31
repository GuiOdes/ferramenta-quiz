package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.response.AuthenticationResponse
import com.sistemas.ferramentaquiz.dto.UserDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

@Service
class JwtService(
    /**
     * hardcoded 256-bit hex formatted secret key (generated on https://allkeysgenerator.com/random/security-encryption-key-generator.aspx)
     */
    @Value("\${security-configurations.jwt.secret}")
    private val secretKey: String,
    @Value("\${security-configurations.jwt.expiration}")
    private val expirationTime: Long
) {

    fun extractUsername(token: String): String {
        return extractClaim(token.replace("Bearer ", ""), Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDto: UserDto): AuthenticationResponse {
        val authToken = generateJwt(extraClaims, userDto, expirationTime)

        val refreshToken = generateJwt(extraClaims, userDto, expirationTime * 2)

        return AuthenticationResponse(authToken, refreshToken)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token) // json web signature
            .body
    }

    fun generateToken(user: UserDto): AuthenticationResponse {
        return generateToken(HashMap(), user)
    }

    fun isTokenValid(token: String, user: UserDetails): Boolean {
        val username = extractUsername(token)

        return username == user.username
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun generateJwt(
        extraClaims: Map<String, Any>,
        userDto: UserDto,
        expiration: Long
    ): String = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDto.email)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact()
}
