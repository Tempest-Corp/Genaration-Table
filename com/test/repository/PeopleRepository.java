package com.test.repository; 

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

public interface PeopleRepository extends JpaRepository<PeopleRepository,Long> {

} 
