package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.ResetPasswordCode;
import xyz.stodo.entity.VerificationToken;

import java.util.Optional;

public interface ResetPasswordCodeRepository extends JpaRepository<ResetPasswordCode, Long> {
    Optional<ResetPasswordCode> findByCode(String code);
}
