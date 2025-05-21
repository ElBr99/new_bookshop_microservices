package ru.ifellow.jschool.ebredichina.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import ru.ifellow.jschool.client.FavouriteBookClient;
import ru.ifellow.jschool.dto.FavouriteBookDto;
import ru.ifellow.jschool.dto.GetUserDto;
import ru.ifellow.jschool.ebredichina.service.AuthenticationService;
import ru.ifellow.jschool.ebredichina.service.FavouriteBookClientService;
import ru.ifellow.jschool.ebredichina.service.UserClientService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteBookClientServiceImpl implements FavouriteBookClientService {

    private final AuthenticationService authenticationService;
    private final UserClientService userClientService;
    private final FavouriteBookClient favouriteBookClient;

    @Override
    public void addBooksToFavorites(UUID bookInfoId) {

        String username = authenticationService.getAuthenticatedUsername();
        GetUserDto user = authenticationService.getAuthenticatedUser(username);

        GetUserDto customer = userClientService.getUserById(user.getId());

        favouriteBookClient.addToMyFavourites(bookInfoId, customer.getId());

    }

    @Override
    public List<FavouriteBookDto> getFavouriteBooks() {
        return List.of();
    }

}
