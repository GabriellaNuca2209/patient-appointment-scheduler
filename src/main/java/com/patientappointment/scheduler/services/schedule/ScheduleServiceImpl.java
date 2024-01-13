package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.repositories.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO) {
        DoctorSchedule savedSchedule = scheduleRepository.save(modelMapper.map(doctorScheduleDTO, DoctorSchedule.class));

        return modelMapper.map(savedSchedule, DoctorScheduleDTO.class);
    }
}