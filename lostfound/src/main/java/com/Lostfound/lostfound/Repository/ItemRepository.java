package com.Lostfound.lostfound.Repository;

import com.Lostfound.lostfound.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
