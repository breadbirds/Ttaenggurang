package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.SavingsGoal;
import com.ladysparks.ttaenggrang.dto.SavingsGoalDTO;
import com.ladysparks.ttaenggrang.mapper.SavingsGoalMapper;
import com.ladysparks.ttaenggrang.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final SavingsGoalMapper savingsGoalMapper;

    @Autowired
    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository, SavingsGoalMapper savingsGoalMapper) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.savingsGoalMapper = savingsGoalMapper;
    }

    public SavingsGoalDTO addSavingsGoal(SavingsGoalDTO savingsGoalDTO) {
        SavingsGoal savingsGoal = savingsGoalMapper.toEntity(savingsGoalDTO);
        SavingsGoal savedSavingsGoal = savingsGoalRepository.save(savingsGoal);
        return savingsGoalMapper.toDto(savedSavingsGoal);
    }

    public List<SavingsGoalDTO> findSavingsGoals(Long studentId) {
        List<SavingsGoal> savingsGoals = savingsGoalRepository.findByStudentId(studentId);
        return savingsGoals
                .stream()
                .map(savingsGoalMapper::toDto)
                .collect(Collectors.toList());
    }

}
