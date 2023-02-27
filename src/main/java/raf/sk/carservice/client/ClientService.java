package raf.sk.carservice.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import raf.sk.carservice.dto.interservice.UserInfoDto;

@AllArgsConstructor
@Service
public class ClientService {

    private WebClient webClient;

    public UserInfoDto getUserInfo(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/m1/inter-service/find/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
    }

    public void updateUserReservationDays(Long id, int numOfDays){
        webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/m1/inter-service/updateReservationDays/{id}")
                        .queryParam("numOfDays", numOfDays)
                        .build(id))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
