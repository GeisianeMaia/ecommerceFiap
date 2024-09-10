package com.fiap.ecommerce.login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class TokenServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider; // Supondo que você use uma classe para fornecer tokens JWT

    @InjectMocks
    private TokenService tokenService; // O serviço que você está testando

    private static final String TEST_TOKEN = "testToken";
    private static final String TEST_USER_ID = "testUserId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        when(jwtTokenProvider.createToken(TEST_USER_ID)).thenReturn(TEST_TOKEN);

        String token = tokenService.generateToken(TEST_USER_ID);

        assertNotNull(token);
        assertEquals(TEST_TOKEN, token);
        verify(jwtTokenProvider, times(1)).createToken(TEST_USER_ID);
    }

    @Test
    void testValidateToken() {
        when(jwtTokenProvider.validateToken(TEST_TOKEN)).thenReturn(true);

        boolean isValid = tokenService.validateToken(TEST_TOKEN);

        assertTrue(isValid);
        verify(jwtTokenProvider, times(1)).validateToken(TEST_TOKEN);
    }

    @Test
    void testGetUserIdFromToken() {
        when(jwtTokenProvider.getUserIdFromToken(TEST_TOKEN)).thenReturn(Optional.of(TEST_USER_ID));

        Optional<String> userId = tokenService.getUserIdFromToken(TEST_TOKEN);

        assertTrue(userId.isPresent());
        assertEquals(TEST_USER_ID, userId.get());
        verify(jwtTokenProvider, times(1)).getUserIdFromToken(TEST_TOKEN);
    }
}
