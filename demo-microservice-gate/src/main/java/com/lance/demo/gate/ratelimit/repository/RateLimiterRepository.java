package com.lance.demo.gate.ratelimit.repository;

import com.lance.demo.gate.ratelimit.Rate;
import org.springframework.data.repository.CrudRepository;


public interface RateLimiterRepository extends CrudRepository<Rate, String> {

}
