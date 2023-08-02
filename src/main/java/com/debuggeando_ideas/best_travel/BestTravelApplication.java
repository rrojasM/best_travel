package com.debuggeando_ideas.best_travel;

import com.debuggeando_ideas.best_travel.domain.repositories.mongo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@Slf4j
public class BestTravelApplication implements CommandLineRunner { //implements CommandLineRunner {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AppUserRepository appUserRepository;

	//Autowired
	//private AppUserRepository appUserRepository;
	/*

	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final TourRepository tourRepository;
	private final CustomerRepository customerRepository;

	//Implementando Repositories mediante constructor en lugar de utilizar Autiwired o inyeccion de dependencias
	public BestTravelApplication(
			HotelRepository hotelRepository,
			FlyRepository flyRepository,
			TicketRepository ticketRepository,
			ReservationRepository reservationRepository,
			TourRepository tourRepository,
			CustomerRepository customerRepository) {
		this.hotelRepository = hotelRepository;
		this.flyRepository = flyRepository;
		this.ticketRepository = ticketRepository;
		this.reservationRepository = reservationRepository;
		this.tourRepository = tourRepository;
		this.customerRepository = customerRepository;
	}
	 */

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.appUserRepository.findAll().forEach(user -> System.out.println( user.getUsername() + " - " + this.bCryptPasswordEncoder.encode(user.getPassword())));
	}

	/*
	@Override
	public void run(String... args) throws Exception{
		this.appUserRepository.findAll().forEach(System.out::println);
		System.out.print(this.appUserRepository.findByUsername("ragnar777").orElseThrow());
	}
	*/

	/*
	@Override
	public void run(String... args) throws Exception {
		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
		var ticket = ticketRepository.findById(UUID.fromString("32345678-1234-5678-4234-567812345678")).get();
		var reservation = reservationRepository.findById(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
		var customer = customerRepository.findById("VIKI771012HMCRG093").get();

		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
		log.info(String.valueOf(ticket));
		log.info(String.valueOf(reservation));
		log.info(String.valueOf(customer));


		this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(f -> System.out.println(f));
		this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(f->System.out.println(f));
		this.flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(System.out::println);

		var fly = flyRepository.findById(1L).get();
		System.out.println(fly);

		fly.getTickets().forEach(t -> System.out.println(t));


		//Buscar por ticket id
		var fly = flyRepository.findByTicketId(UUID.fromString("22345678-1234-5678-3235-567812345678"));
		System.out.println(fly);


		hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);
		hotelRepository.findByPriceIsBetween(BigDecimal.valueOf(50), BigDecimal.valueOf(150)).forEach(System.out::println);
		hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);

		var hotel = hotelRepository.findByReservationId(UUID.fromString("12345678-1234-5678-1234-567812345678"));
		System.out.println(hotel);

		var customer = customerRepository.findById("VIKI771012HMCRG093").orElseThrow();

		log.info("Client name: " + customer.getFullName());

		var fly = flyRepository.findById(11L).orElseThrow();

		log.info("Fly: " + fly.getOriginName() + "-" + fly.getDestinyName());
		var hotel = hotelRepository.findById(3L).orElseThrow();
		log.info("Hotel: " + hotel.getName());

		var tour = TourEntity.builder().customer(customer).build();

		var ticket = TicketEntity.builder()
				.id(UUID.randomUUID())
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.arrivalDate(LocalDateTime.now())
				.departureDate(LocalDateTime.now())
				.purchaseDate(LocalDate.now())
				.customer(customer)
				.tour(tour)
				.fly(fly)
				.build();

		var reservation = ReservationEntity.builder()
				.id(UUID.randomUUID())
				.dateTimeReservation(LocalDateTime.now())
				.dateEnd(LocalDate.now().plusDays(2))
				.dateStart(LocalDate.now().plusDays(1))
				.hotel(hotel)
				.customer(customer)
				.tour(tour)
				.totalDays(1)
				.price(hotel.getPrice().multiply(BigDecimal.TEN))
				.build();

		System.out.println("--- SAVING ---");

		tour.addReservation(reservation);
		tour.updateReservation();
		tour.addTicket(ticket);
		tour.updateTicket();

		var tourSaved = this.tourRepository.save(tour);
		Thread.sleep(8000);
		this.tourRepository.deleteById(tourSaved.getId());

	}
	**/
}
