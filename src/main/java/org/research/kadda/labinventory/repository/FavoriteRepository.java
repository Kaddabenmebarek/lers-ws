package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.Favorite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {
    
    @Query("select f from Favorite f where f.userName = ?1")
    List<Favorite> findFavoriteByUser(String user);
    
    @Query(value = "delete from CHEMINFRA.FAVORITE_INSTRUMENT where id = ?1", nativeQuery = true)
    List<Favorite> deleteFavouriteById(int favouriteId);

    @Query("select f from Favorite f where f.instrid = ?1")
	Iterable<Favorite> findByInstrId(int id);
       
}
