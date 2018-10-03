package com.patientdata.service.repository;

import com.patientdata.service.model.Patient;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
//List<Patient> findPatientListByContactNumber(String zipCode);

    Patient findPatientByContactNumber(String contactNumber);

    public Page<Patient> findAll(Pageable pageable);


}
