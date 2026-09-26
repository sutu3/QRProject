package org.example.qrproject.Repo;

import org.example.qrproject.Module.InvalidateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepo extends JpaRepository<InvalidateTokenEntity,String> {
}
