package com.mjc.school.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = ConfigureTestDatabase.class)
public class BaseRepositoryTest {

    @Autowired
    private List<BaseRepository<?, Long>> repositories;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void givenRepositories_whenCheckLength_then4Returned(){
        System.out.println(repositories);
        assertEquals(4, repositories.size());
    }

    @TestFactory
    Stream<DynamicTest> givenAllRepositories_whenTheirMethodsCalled_thenShouldReturnValidResponses(){
        return repositories.stream()
                .flatMap(repository -> Stream.of(

                        dynamicTest("readAll should not return null for " + repository.getClass().getName(), () -> {
                            var entities = repository.readAll(0, 5, "id");
                            assertThat(entities).isNotNull();
                        }),

                        dynamicTest("readById should not return null for " + repository.getClass().getName(), () -> {
                            var entity = repository.readById(1L);
                            assertThat(entity).isNotNull();
                        })
                        )
                );

    }


}
