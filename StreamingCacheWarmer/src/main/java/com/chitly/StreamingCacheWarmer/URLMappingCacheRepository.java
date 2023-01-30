package com.chitly.StreamingCacheWarmer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLMappingCacheRepository extends CrudRepository<URLMapping, String> {
}
