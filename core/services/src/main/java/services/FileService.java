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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public List<PersonDTO> parseFileToDB(MultipartFile file) {

        List<PersonDTO> persons = new ArrayList<>();
        PersonDTO personDTO;
        NameDTO nameDTO;
        AddressDTO addressDTO;

        try {

            String line;
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = br.readLine()) != null) {
                  ArrayList<String> columns = new  ArrayList<String>(Arrays.asList(line.split("\t")));

                  personDTO = new PersonDTO();
                  personDTO.setId(Long.valueOf(columns.get(0)));
                  nameDTO = new NameDTO();
                  nameDTO.setTitle(columns.get(1));
                  nameDTO.setFirstName(columns.get(2));
                  nameDTO.setMiddleName(columns.get(3));
                  nameDTO.setLastName(columns.get(4));
                  nameDTO.setSuffix(columns.get(5));
                  addressDTO = new AddressDTO();
                  addressDTO.setStreetNo(columns.get(6));
                  addressDTO.setBarangay(columns.get(7));
                  addressDTO.setMunicipality(columns.get(8));
                  addressDTO.setZipCode(columns.get(9));
                  //personDTO.setbDay(new Date(columns.get(10)));
                  personDTO.setGWA(Double.valueOf(columns.get(11)));
                  //personDTO.setDateHired(new Date(columns.get(12)));
                  personDTO.setCurrEmployed(Boolean.valueOf(columns.get(13)));
                  personDTO.setName(nameDTO);
                  personDTO.setAddress(addressDTO);
                  persons.add(personDTO);
                  //nameDTO.setTitle();
            }

          } catch (IOException e) {
            System.err.println(e.getMessage());
          }

        return persons;
    }

    /*
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    */


}
