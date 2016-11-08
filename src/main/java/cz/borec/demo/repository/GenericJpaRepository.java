package cz.borec.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import cz.borec.demo.core.entity.BaseEntity;


/**
 * Base extendable interface for all repositories.
 */
@NoRepositoryBean
public interface GenericJpaRepository<T extends BaseEntity<ID>, ID extends Serializable> 
	extends CustomJpaRepository<T, ID>, JpaRepository<T, ID> {

}
