package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.exceptions.schedule.ScheduleNotFoundException;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.repositories.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.patientappointment.scheduler.utils.constants.ScheduleConstants.CONSULTATION_TIME;

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
        DoctorSchedule savedSchedule = scheduleRepository.save(modelMapper.map(doctorScheduleDTO, DoctorSchedule.class));

        return modelMapper.map(savedSchedule, DoctorScheduleDTO.class);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        DoctorSchedule schedule =  scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with id " + scheduleId + " not found"));
        scheduleRepository.delete(schedule);
    }

    @Override
    public DoctorScheduleDTO getSchedule(Long scheduleId) {
        DoctorSchedule doctorSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule with id " + scheduleId + " not found"));

        return modelMapper.map(doctorSchedule, DoctorScheduleDTO.class);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId) {
        List<DoctorSchedule> doctorSchedules = scheduleRepository.findByDoctorId(doctorId);
        return doctorSchedules.stream().map(schedule -> modelMapper.map(schedule, DoctorScheduleDTO.class)).toList();
    }

    @Override
    public List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId, List<Appointment> appointments) {
        DoctorSchedule schedule = scheduleRepository.findByWorkingDateAndDoctorId(date, doctorId);
        List<LocalTime> totalSlots = calculateTotalSlots(schedule.getStartShift(), schedule.getEndShift());

        return calculateAvailableSlots(totalSlots, appointments);
    }

    private List<LocalTime> calculateAvailableSlots(List<LocalTime> slots, List<Appointment> appointments) {
        List<LocalTime> occupiedSlots = new ArrayList<>();

        for (Appointment appointment : appointments) {
            occupiedSlots.add(appointment.getAppointmentTime());
        }
        for (int i = 0; i < occupiedSlots.size(); i++) {
            slots.remove(occupiedSlots.get(i));
        }

        return slots;
    }

    private List<LocalTime> calculateTotalSlots(LocalTime startShift, LocalTime endShift) {
        List<LocalTime> slots = new ArrayList<>();
        slots.add(startShift);

        while (!slots.contains(endShift)) {
            slots.add(slots.get(slots.size() - 1).plusMinutes(CONSULTATION_TIME));
        }
        slots.remove(slots.get(slots.size() - 1));

        return slots;
    }
}
