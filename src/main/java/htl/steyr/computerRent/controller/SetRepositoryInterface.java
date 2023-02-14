package htl.steyr.computerRent.controller;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface SetRepositoryInterface {
    <T> void   setRepository(List<T> repository);
}
