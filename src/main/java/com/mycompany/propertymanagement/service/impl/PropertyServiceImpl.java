package com.mycompany.propertymanagement.service.impl;

import com.mycompany.propertymanagement.converter.PropertyConverter;
import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.entity.PropertyEntity;
import com.mycompany.propertymanagement.entity.UserEntity;
import com.mycompany.propertymanagement.exception.BusinessException;
import com.mycompany.propertymanagement.exception.ErrorModel;
import com.mycompany.propertymanagement.repository.PropertyRepository;
import com.mycompany.propertymanagement.repository.UserRepository;
import com.mycompany.propertymanagement.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyConverter propertyConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(propertyDTO.getUserId());
        if (optionalUserEntity.isPresent()) {
            PropertyEntity propertyEntity = propertyConverter.convertDTOToEntity(propertyDTO);
            propertyEntity.setUserEntity(optionalUserEntity.get());
            propertyRepository.save(propertyEntity);

            propertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
        }else {
            List<ErrorModel> errorModels = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ID_NOT_EXIST");
            errorModel.setMessage("User ID does not exist");
            errorModels.add(errorModel);

            throw new BusinessException(errorModels);
        }

        return propertyDTO;
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyEntity> propertyEntities = (List<PropertyEntity>) propertyRepository.findAll();
        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (PropertyEntity propertyEntity: propertyEntities) {
            PropertyDTO propertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
            propertyDTOs.add(propertyDTO);
        }
        return propertyDTOs;
    }

    @Override
    public List<PropertyDTO> getAllPropertiesForUser(Long userId) {
        List<PropertyEntity> propertyEntities = (List<PropertyEntity>) propertyRepository.findAllByUserEntityId(userId);
        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (PropertyEntity propertyEntity: propertyEntities) {
            PropertyDTO propertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
            propertyDTOs.add(propertyDTO);
        }
        return propertyDTOs;
    }

    @Override
    public  PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optionalPropertyEntity = propertyRepository.findById(propertyId);
        PropertyDTO updatedPropertyDTO = null;

        if (optionalPropertyEntity.isPresent()) {
            PropertyEntity propertyEntity = optionalPropertyEntity.get();
            propertyEntity.setTitle(propertyDTO.getTitle());
            propertyEntity.setAddress(propertyDTO.getAddress());
            propertyEntity.setDescription(propertyDTO.getDescription());
            propertyEntity.setPrice(propertyDTO.getPrice());

            updatedPropertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
            propertyRepository.save(propertyEntity);
        }

        return updatedPropertyDTO;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optionalPropertyEntity = propertyRepository.findById(propertyId);
        PropertyDTO updatedPropertyDTO = null;

        if (optionalPropertyEntity.isPresent()) {
            PropertyEntity propertyEntity = optionalPropertyEntity.get();
            propertyEntity.setDescription(propertyDTO.getDescription());

            updatedPropertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
            propertyRepository.save(propertyEntity);
        }

        return updatedPropertyDTO;
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optionalPropertyEntity = propertyRepository.findById(propertyId);
        PropertyDTO updatedPropertyDTO = null;

        if (optionalPropertyEntity.isPresent()) {
            PropertyEntity propertyEntity = optionalPropertyEntity.get();
            propertyEntity.setPrice(propertyDTO.getPrice());

            updatedPropertyDTO = propertyConverter.convertEntityToDTO(propertyEntity);
            propertyRepository.save(propertyEntity);
        }

        return updatedPropertyDTO;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
