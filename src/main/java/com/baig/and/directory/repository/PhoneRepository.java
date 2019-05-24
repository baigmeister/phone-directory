package com.baig.and.directory.repository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository {
    Optional<List<Phone>> findAll();
    boolean save(Optional<Phone> phone) throws Exception;
}
