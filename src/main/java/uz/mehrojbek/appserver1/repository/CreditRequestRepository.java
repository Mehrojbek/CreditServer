package uz.mehrojbek.appserver1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mehrojbek.appserver1.entity.CreditRequest;

public interface CreditRequestRepository extends JpaRepository<CreditRequest,Integer> {
}
