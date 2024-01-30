package com.Ega.EgaBankingBackend;

import com.Ega.EgaBankingBackend.dto.ClientDTO;
import com.Ega.EgaBankingBackend.dto.CompteCourantDTO;
import com.Ega.EgaBankingBackend.dto.CompteDTO;
import com.Ega.EgaBankingBackend.dto.CompteEpargneDTO;
import com.Ega.EgaBankingBackend.exceptions.ClientNonTrouvrerExceptions;
import com.Ega.EgaBankingBackend.service.CompteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.stream.Stream;


@SpringBootApplication
@ComponentScan(basePackages = "com.Ega.EgaBankingbackend")
public class EgaBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgaBankingBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CompteService compteService){
		return args -> {
			Stream.of("Ulrich","Obed","Joel").forEach(nom->{
				ClientDTO client= new ClientDTO();
				client.setNom(nom);
				client.setEmail(nom+"@gmail.com");
				compteService.saveClient(client);
			});
			compteService.listClients().forEach(customer->{
				try {
					compteService.saveCompteCourant(Math.random()*90000,9000,customer.getId());
					compteService.saveCompteEpargne(Math.random()*120000,5.5,customer.getId());

				} catch (ClientNonTrouvrerExceptions e) {
					e.printStackTrace();
				}
			});
			List<CompteDTO> comptes = compteService.listComptes();
			for (CompteDTO compte:comptes){
				for (int i = 0; i <10 ; i++) {
					String compteId;
					if(compte instanceof CompteEpargneDTO){
						compteId=((CompteEpargneDTO) compte).getId();
					} else{
						compteId=((CompteCourantDTO) compte).getId();
					}
					compteService.credit(compteId,10000+Math.random()*120000,"Credit");
					compteService.debit(compteId,1000+Math.random()*9000,"Debit");
				}
			}

		};
	}

}



