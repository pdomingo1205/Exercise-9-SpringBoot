package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import models.dto.PersonDTO;
import models.dto.ContactInfoDTO;
import models.dto.AddressDTO;
import models.dto.NameDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private PersonService personService;

    private PersonDTO personDTO;
    private NameDTO nameDTO;
    private AddressDTO addressDTO;

    public List<PersonDTO> parseFileToDB(MultipartFile file) {

        List<PersonDTO> persons = new ArrayList<>();

        try {

            String line;
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            int ctr = 0;

            while ((line = br.readLine()) != null) {
                ArrayList<String> columns = new  ArrayList<String>(Arrays.asList(line.split("\t")));
                ctr++;
                try {
                    personDTO = new PersonDTO();
                    personDTO.setId(Long.valueOf(columns.get(0)));
                    nameDTO = new NameDTO();
                    nameDTO.setTitle(columns.get(1));
                    nameDTO.setFirstName(columns.get(2));
                    nameDTO.setMiddleName(columns.get(3));
                    nameDTO.setLastName(columns.get(4));
                    nameDTO.setSuffix(columns.get(5));
                    personDTO.setName(nameDTO);
                    addressDTO = new AddressDTO();
                    addressDTO.setStreetNo(columns.get(6));
                    addressDTO.setBarangay(columns.get(7));
                    addressDTO.setMunicipality(columns.get(8));
                    addressDTO.setZipCode(columns.get(9));
                    personDTO.setAddress(addressDTO);
                    personDTO.setGWA(Double.valueOf(columns.get(11)));

                    personDTO.setbDay(DateUtils.parseDateStrictly(columns.get(10), "yyyy/MM/dd"));
                    personDTO.setDateHired(DateUtils.parseDateStrictly(columns.get(10), "yyyy/MM/dd"));

                    personDTO.setCurrEmployed(Boolean.valueOf(columns.get(13)));
                    persons.add(personDTO);
                    personService.createPerson(personDTO);
                } catch(ParseException pe){
                    logger.warn(pe.getMessage());
                    logger.warn("Error processing Person at line "+ctr);
                    logger.info("Person:" + personDTO);
                } catch(NumberFormatException nfe){
                    logger.warn("Invalid Id at line "+ctr);
                    logger.info("Person:" + personDTO);
                }

            }

          } catch (IOException e) {
            System.err.println(e.getMessage());
          }

        return persons;
    }



}
