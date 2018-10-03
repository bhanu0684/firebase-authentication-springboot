package com.patientdata.service.services;

import com.patientdata.service.model.Address;
import com.patientdata.service.model.Patient;
import com.patientdata.service.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PatientService {
    private static final Logger LOGGER = LogManager.getLogger(PatientService.class.getName());

    @Autowired
    private PatientRepository patientRepository ;



    public void savePatient(Patient patient)
    {
        patientRepository.save(patient);

    }
    public Patient updatePatient(Patient patient)
    {
        Patient previousPatient=patientRepository.findById(patient.getId()).get();
        if(previousPatient !=null) {
            previousPatient.setFirstName(patient.getFirstName());
            previousPatient.setLastName(patient.getLastName());
            previousPatient.setContactNumber(patient.getContactNumber());
            previousPatient.getAddresses().clear();
            previousPatient.getAddresses().addAll(patient.getAddresses());
            patientRepository.save(previousPatient);
            return patient;
        }
        else
            return null ;

    }
    public Patient softDeletePatient(long id)
    {
        Patient previousPatient=patientRepository.findById(id).get();
        if(previousPatient !=null) {
            previousPatient.setActive(false);
            patientRepository.save(previousPatient);
            return previousPatient;
        }
        else
            return null ;
    }
    public void hardDeletePatient(long id)
    {
        patientRepository.deleteById(id);
    }
    public List<Patient> getPatient()
    {
       return StreamSupport.stream(patientRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public boolean isPatientExists(String contactNumber)
    {
        Patient patient=patientRepository.findPatientByContactNumber(contactNumber);
        if(patient==null)
            return false    ;
        else
            return true;
    }

    public List<Patient> findByPage(Pageable pageable )
    {
       return patientRepository.findAll(pageable).getContent();
    }

}
