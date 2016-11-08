
package cz.borec.demo.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang.Validate;
/*import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
*/import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import cz.borec.demo.core.entity.BaseEntity;


/**
 * Custom methods for all repositories implementation.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class CustomJpaRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
                    extends SimpleJpaRepository<T, ID>
                    implements CustomJpaRepository<T, ID> {


    //protected final Logger log = Logger.getLogger(this.getClass());
    private final EntityManager em;


    public CustomJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    public T findById(ID id)  {
        //LogMF.trace(log, "Searching {0} with Id={1} (findById)", new Object[]{getDomainClass().getCanonicalName(), id});
        Validate.notNull(id);

        T entity = findOne(id);
        if (entity == null) {
            throw new RuntimeException("Entity not found.");
        }
        return entity;
    }

    @Override
    public T findByIdNullable(ID id) {
        //LogMF.trace(log, "Searching {0} with Id={1} (findByIdNullable)", new Object[]{getDomainClass().getCanonicalName(), id});
        Validate.notNull(id);

        T entity = findOne(id);
        if (entity == null) {
            return null;
        }
        return entity;
    }
}
