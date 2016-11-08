
package cz.borec.demo.repository;

import java.io.Serializable;

import cz.borec.demo.core.entity.BaseEntity;


/**
 * Custom methods for all repositories.
*/
@SuppressWarnings("UnusedDeclaration")
public interface CustomJpaRepository<T extends BaseEntity<ID>, ID extends Serializable> {

    T findById(ID id);
    T findByIdNullable(ID id);

}
