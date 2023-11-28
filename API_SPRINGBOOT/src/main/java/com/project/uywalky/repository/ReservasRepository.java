package com.project.uywalky.repository;

import com.project.uywalky.Entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Integer> {
}
