package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.CustomerResponse;


public class CustomerMapper {


     public static CustomerResponse toDTO(Customer customer){
          if (customer == null) {
               return null;
          }

          return new CustomerResponse(
                    customer.getName(),
                    customer.getCpf()
          );
     }

     public static Customer toEntity(CustomerRequest customerDTO){
          if (customerDTO == null) {
               return null;
          }

            return new Customer(null,
                        customerDTO.name(),
                        customerDTO.cpf(),
                        null
            );
     }
}
