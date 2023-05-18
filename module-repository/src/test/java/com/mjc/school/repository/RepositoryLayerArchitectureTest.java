package com.mjc.school.repository;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTag("architecture")
@AnalyzeClasses(packagesOf = RepositoryLayerArchitectureTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryLayerArchitectureTest {

    @ArchTest
    void Repositories_should_only_be_accessed_by_Services(final JavaClasses repositoryLayerClasses){
        ArchRule myRule = classes()
            .that().resideInAPackage("com.mjc.school.repository")
            .should().onlyBeAccessed().byAnyPackage("..service..", "..repository..");

        myRule.check(repositoryLayerClasses);
    }

    @ArchTest
    void givenClasses_whenAnnotatedWithEntity_thenShouldImplementBaseEntityInterface(final JavaClasses repositoryLayerClasses){
        classes()
            .that().areAnnotatedWith("javax.persistence.Entity")
            .or().areAnnotatedWith("jakarta.persistence.Entity")
            .should().implement("com.mjc.school.repository.model.BaseEntity")
            .check(repositoryLayerClasses);
    }
    @ArchTest
    void givenClasses_whenAnnotatedWithRepository_thenShouldImplementBaseRepositoryInterface(final JavaClasses repositoryLayerClasses){
        classes()
            .that().areAnnotatedWith("org.springframework.stereotype.Repository")
            .should().implement("com.mjc.school.repository.BaseRepository")
            .check(repositoryLayerClasses);
    }

}
