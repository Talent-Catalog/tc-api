package org.tctalent.anonymization.domain.document;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractDomainDocument<IdType extends Serializable>  implements Serializable {

  @Id
  private IdType id;

  /*
    For good discussion on hashCode and equals for entities see
    https://web.archive.org/web/20170710132916/http://www.onjava.com/pub/a/onjava/2006/09/13/dont-let-hibernate-steal-your-identity.html

    The key problem is that entity objects only get an id once they are
    persisted. If you are using those objects before persisting them
    the absence of an id can lead to peculiar results - for example all
    object instances looking like they are equal.
   */

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    } else {
      return super.hashCode();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    AbstractDomainDocument<?> other = (AbstractDomainDocument<?>) obj;

    //If id is missing assume that it is not equal to other instance.
    //(Previous version of this code treated all instances with null
    //ids as equal).
    if (id == null) return false;

    //Equivalence by id
    return id.equals(other.id);
  }

  @Override
  public String toString() {
    return this.getClass().getName() + "[id=" + id + "]";
  }
}
