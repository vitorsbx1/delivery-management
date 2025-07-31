package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.CustomerResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {


     CustomerResponse toDTO(Customer customer);
     Customer toEntity(CustomerRequest customerDTO);
}
