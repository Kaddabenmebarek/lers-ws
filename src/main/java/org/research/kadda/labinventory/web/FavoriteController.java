package org.research.kadda.labinventory.web;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.research.kadda.labinventory.data.FavoriteDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.Favorite;
import org.research.kadda.labinventory.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/favorite")
public class FavoriteController {
    private static Logger logger = LogManager.getLogger(FavoriteController.class);
    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    protected FavoriteController() {}

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAllFavorites() {
        logger.info("Getting all favorites");

        boolean firstFavorite = true;
        Iterable<Favorite> favorites = favoriteService.findAll();
        StringBuffer bodyResponse = new StringBuffer("{");
        bodyResponse.append("\"favorites\":[");
        try {
            for (Favorite favorite : favorites) {
                if (firstFavorite) {
                	firstFavorite = false;
                } else {
                    bodyResponse.append(",");
                }

                bodyResponse.append(JsonUtils.mapToJson(favorite));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        bodyResponse.append("]}");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }

    @GetMapping("/user/{user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getUserFavorites(@PathVariable(value = "user") String user) {
        logger.info("Getting favorites for : " + user);

        List<Favorite> userFavorites = favoriteService.findByUser(user);
        if (userFavorites == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Cannot find user favorites instruments.\"}");

        }
        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(userFavorites));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteDto favoriteDto) {
        logger.info("Adding favorite");
        StringBuffer bodyResponse = new StringBuffer("{");
        Favorite favorite = new DozerBeanMapper().map(favoriteDto, Favorite.class);        
        favoriteService.save(favorite);
        bodyResponse.append("}");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
    @DeleteMapping("/delete/{favoriteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteFavorite(@PathVariable(value = "favoriteId") String favoriteId) {
        logger.info("Deleting favorite : " + favoriteId);
        Optional<Favorite> favoriteToRemove = favoriteService.findById(favoriteId);
        if (favoriteToRemove == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Favorite cannot be removed.\"}");

        }
        Favorite fav = favoriteToRemove.get();
		StringBuffer bodyResponse = new StringBuffer("{}");
		favoriteService.delete(fav);
		bodyResponse.append("}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
    }
    
    @GetMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getFavorite(@PathVariable(value = "favoriteId") String favoriteId) {
        logger.info("Getting favorite : " + favoriteId);

        Optional<Favorite> favorite = favoriteService.findById(favoriteId);
        if (favorite == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Favorite id must be an integer.\"}");

        }
        Favorite fav = favorite.get();
        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(fav));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
	@DeleteMapping("/delete/inst/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteFavoriteByInstrument(@PathVariable(value = "instrId") String instId) {
		logger.info("Delete favorite for instrument");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");
		try {
			Iterable<Favorite> favs = favoriteService.findByInstrId(instId);
	        for(Favorite fav : favs) {
	        	favoriteService.delete(fav);
	        }
		} catch (NumberFormatException nfe) {
			status = HttpStatus.BAD_REQUEST;
			bodyResponse.append("\"message\":\"NumberFormatException. The instrument id '"+ instId +"' is not well formatted.\"");
		}
		bodyResponse.append("}");

		bodyResponse.append("}");
		return ResponseEntity.status(status).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
}
