package com.monarca.backendmonarca.domain.pqr;

import java.time.LocalDate;

public record DataRegisterPqr(String name, String descripcion, LocalDate date_register, LocalDate date_solution, LocalDate deadline, Status status, Priority priority) {
}
