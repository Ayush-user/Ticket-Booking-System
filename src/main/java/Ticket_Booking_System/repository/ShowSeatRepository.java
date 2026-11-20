package Ticket_Booking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Ticket_Booking_System.entity.ShowSeat;
import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

	List<ShowSeat> findByShowId(Long showId);
	

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			SELECT ss
			FROM ShowSeat ss
			WHERE ss.id IN :ids
			ORDER BY ss.id
			""")
	List<ShowSeat> findAllByIdForUpdate(
        @Param("ids") List<Long> ids
);

}
