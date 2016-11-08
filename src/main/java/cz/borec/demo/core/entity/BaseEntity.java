package cz.borec.demo.core.entity;
import java.io.Serializable;


public abstract class BaseEntity<ID extends Serializable> implements  Serializable {

    /** Creates a new instance. */
    public BaseEntity() {
        super();
    }

    /**
     * Returns the primary key of this entity.
     * @return unique identifier.
     */
    public abstract ID getId();

    /**
     * Returns the hashCode. This implementation is based only on object's ID.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 13 + 7 * (getId() != null ? getId().hashCode() : 0);
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) { // the same Object instance
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // "invalid" class
        }

        BaseEntity<ID> other = (BaseEntity<ID>) obj;
        if (getId() != other.getId() && (getId() == null || !getId().equals(other.getId()))) {
            return false; // not the same Object && not equals
        }
        return true; // has same ID
    }


    @Override
    public String toString() {
        return String.format("%s[PK=%s]", getClass().getSimpleName(), getId());
    }
}
