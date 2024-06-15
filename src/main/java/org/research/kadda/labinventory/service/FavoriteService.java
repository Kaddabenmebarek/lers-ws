package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.Favorite;
import org.research.kadda.labinventory.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Iterable<Favorite> findAll() { return favoriteRepository.findAll(); }

    public List<Favorite> findByUser(String user) {
        return this.favoriteRepository.findFavoriteByUser(user);
    }

    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    public void delete(Favorite favorite) {
    	//favoriteRepository.delete(favorite);
    	favoriteRepository.deleteFavouriteById(favorite.getId());
    }
    
    public Optional<Favorite> findById(String favoriteId) {
        int id = toInt(favoriteId);
        return this.favoriteRepository.findById(id);
    }

	public Iterable<Favorite> findByInstrId(String instId) {
		int id = toInt(instId);
		return this.favoriteRepository.findByInstrId(id);
	}

	
	private Integer toInt(String favoriteId) {
		int id;
        try {
            id = Integer.valueOf(favoriteId);
        } catch (NumberFormatException nfe) {
            return null;
        }
		return id;
	}
}
