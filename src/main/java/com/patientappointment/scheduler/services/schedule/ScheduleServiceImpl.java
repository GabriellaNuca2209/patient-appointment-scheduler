package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.repositories.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        doctorScheduleDTO.setAvailableSlots(calculateAvailableSlots(doctorScheduleDTO.getStartShift(), doctorScheduleDTO.getEndShift()));
        DoctorSchedule savedSchedule = scheduleRepository.save(modelMapper.map(doctorScheduleDTO, DoctorSchedule.class));

        return modelMapper.map(savedSchedule, DoctorScheduleDTO.class);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedule(Long doctorId) {
        List<DoctorSchedule> doctorSchedules = scheduleRepository.findByDoctorId(doctorId);
        return doctorSchedules.stream().map(schedule -> modelMapper.map(schedule, DoctorScheduleDTO.class)).toList();
    }

    @Override
    public DoctorSchedule getSchedule(LocalDate date, Long id) {
        return scheduleRepository.findByWorkingDateAndDoctorId(date, id);
    }

    @Override
    public void removeSlot(LocalDate date, LocalTime time, Long id) {
        DoctorSchedule schedule = getSchedule(date, id);
        schedule.getAvailableSlots().remove(time);

        scheduleRepository.save(schedule);
    }

    private List<LocalTime> calculateAvailableSlots(LocalTime startShift, LocalTime endShift) {
        List<LocalTime> slots = new ArrayList<>();
        slots.add(startShift);

        while (!slots.contains(endShift)) {
            slots.add(slots.get(slots.size() - 1).plusMinutes(20)); // use constant for minutes
        }
        // exclude end shift
        return slots;
    }
}
