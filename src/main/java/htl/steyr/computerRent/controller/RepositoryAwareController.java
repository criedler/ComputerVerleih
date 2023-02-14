package htl.steyr.computerRent.controller;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface RepositoryAwareController {
    <T> void   setRepository(List<T> repository);
}
