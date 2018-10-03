package com.patientdata.service;

import com.patientdata.service.model.Patient;
import com.patientdata.service.services.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    private static final Logger LOGGER = LogManager.getLogger(PatientController.class.getName());
    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "api/patient", method = RequestMethod.GET)
    public List<Patient> getPatient(Pageable pageable) {
        try {
            List<Patient> pat = patientService.findByPage(pageable);
            LOGGER.info(pat.size());
            return pat;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "api/patientall", method = RequestMethod.GET)
    public List<Patient> getPatientAll() {
        try {
            List<Patient> pat = patientService.getPatient();
            LOGGER.info(pat.size());
            return pat;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "api/patient", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> savePatient(@RequestBody Patient patient) {
        try {
            patientService.savePatient(patient);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "api/patient", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> updatePatient(@RequestBody Patient patient) {
        try {
            patientService.updatePatient(patient);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "api/patient/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> DeletePatient(@PathVariable("id") long id) {
        try {
            patientService.hardDeletePatient(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "api/softdeletepatient/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> softDeletePatient(@PathVariable("id") long id) {
        try {
            if (patientService.softDeletePatient(id).isActive() == false) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
