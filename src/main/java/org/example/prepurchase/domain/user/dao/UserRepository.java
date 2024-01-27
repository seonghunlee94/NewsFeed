package org.example.prepurchase.domain.user.dao;

import org.example.prepurchase.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // 추가적인 쿼리 메서드가 필요하다면 여기에 선언할 수 있습니다.

    // 이메일을 사용하여 사용자 조회
    Users findByEmail(String email);

    // 추가적인 쿼리 메서드 예시:
    // Users findByUsername(String username);
    // List<Users> findByAgeGreaterThan(int age);
    // 등등...
}