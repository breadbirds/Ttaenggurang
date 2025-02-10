package com.ladysparks.ttaenggrang.domain.user.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    // 블랙리스트로 사용할 Set (메모리 기반)
    private final Set<String> blacklist = new HashSet<>();

    // 토큰을 블랙리스트에 추가하는 메서드
    public void blicklistToken(String token) {
        blacklist.add(token);
    }

    // 블랙리스트에 토큰이 존재하는지 확인하는 메서드
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

