package com.mailorderpharma.subscription.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mailorderpharma.subscription.entity.PrescriptionDetails;

/**JPA Repository which interacts with database*/
public interface PrescriptionRepository extends JpaRepository<PrescriptionDetails,Long> {

}
